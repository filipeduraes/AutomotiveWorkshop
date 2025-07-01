// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.finance;

import com.filipeduraes.workshop.client.dtos.ExpenseDTO;
import com.filipeduraes.workshop.client.viewmodel.ExpenseViewModel;
import com.filipeduraes.workshop.client.viewmodel.FinancialViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.core.catalog.ServiceItem;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.financial.Expense;
import com.filipeduraes.workshop.core.financial.FinancialModule;
import com.filipeduraes.workshop.core.financial.Sale;
import com.filipeduraes.workshop.core.financial.Store;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceOrderModule;
import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controlador responsável por gerenciar todas as operações financeiras da oficina.
 * Esta classe coordena o registro de despesas, geração de relatórios financeiros
 * e balanços mensais, atuando como intermediária entre a interface do usuário
 * e os módulos financeiros da oficina.
 *
 * @author Filipe Durães
 */
public class FinanceController
{
    private final InventoryService inventoryService;
    private final ServiceItemService serviceItemService;

    private final ExpenseViewModel expenseViewModel;
    private final FinancialViewModel financialViewModel;

    private final FinancialModule financialModule;
    private final Store store;
    private final Workshop workshop;

    /**
     * Constrói um novo controlador financeiro.
     * Inicializa os serviços de inventário e itens de serviço, e registra os listeners
     * para responder aos eventos da interface do usuário.
     *
     * @param viewModelRegistry registro contendo as referências para os ViewModels
     * @param workshop instância principal da oficina contendo os módulos do sistema
     */
    public FinanceController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        this.workshop = workshop;
        inventoryService = new InventoryService(viewModelRegistry, workshop);
        serviceItemService = new ServiceItemService(viewModelRegistry, workshop);

        expenseViewModel = viewModelRegistry.getExpenseViewModel();
        financialViewModel = viewModelRegistry.getFinancialViewModel();

        financialModule = workshop.getFinancialModule();
        store = workshop.getStore();

        expenseViewModel.OnExpenseRegisterRequested.addListener(this::registerExpense);
        expenseViewModel.OnExpenseReportRequested.addListener(this::generateExpensesMonthReport);
        financialViewModel.OnBalanceReportRequested.addListener(this::generateMonthBalanceReport);
    }

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o controlador não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        expenseViewModel.OnExpenseRegisterRequested.removeListener(this::registerExpense);
        expenseViewModel.OnExpenseReportRequested.removeListener(this::generateExpensesMonthReport);
        financialViewModel.OnBalanceReportRequested.removeListener(this::generateMonthBalanceReport);

        inventoryService.dispose();
        serviceItemService.dispose();
    }

    /**
     * Registra uma nova despesa no sistema financeiro.
     * Cria e adiciona uma nova despesa com base nos dados fornecidos.
     */
    private void registerExpense()
    {
        ExpenseDTO expenseDTO = expenseViewModel.getSelectedExpense();
        Expense expense = new Expense(expenseDTO.getDescription(), expenseDTO.getAmount());
        financialModule.registerExpense(expense);
        expenseViewModel.setRequestWasSuccessful(true);
    }

    /**
     * Gera o relatório de despesas do mês selecionado.
     * Recupera todas as despesas do período e formata em um relatório legível.
     */
    private void generateExpensesMonthReport()
    {
        LocalDateTime date = getTimeFromMonthAndYear(expenseViewModel.getSelectedMonth(), expenseViewModel.getSelectedYear());
        List<Expense> monthExpenses = financialModule.getMonthExpenses(date);

        String report = monthExpenses.stream()
                                     .map(Expense::toString)
                                     .collect(Collectors.joining("\n"));

        expenseViewModel.setReport(report);
        expenseViewModel.setRequestWasSuccessful(true);
    }

    /**
     * Gera o relatório de balanço mensal completo.
     * Calcula receitas (vendas diretas e ordens de serviço) e despesas,
     * apresentando um balanço detalhado do período.
     */
    private void generateMonthBalanceReport()
    {
        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();
        LocalDateTime date = getTimeFromMonthAndYear(financialViewModel.getSelectedMonth(), financialViewModel.getSelectedYear());

        Set<UUID> closedServicesIDs = serviceOrderModule.loadClosedServicesInMonth(date);
        List<Expense> monthExpenses = financialModule.getMonthExpenses(date);
        List<Sale> monthSales = store.getMonthSales(date);

        List<ServiceOrder> closedServiceOrders = closedServicesIDs.stream()
                                                                  .map(id -> serviceOrderModule.getServiceOrderRepository().getEntityWithID(id))
                                                                  .toList();

        Set<UUID> serviceOrdersSales = closedServiceOrders.stream()
                                                           .map(ServiceOrder::getSales)
                                                           .flatMap(List::stream)
                                                           .map(WorkshopEntity::getID)
                                                           .collect(Collectors.toSet());

        List<Sale> detachedSales = monthSales.stream()
                                             .filter(sale -> !serviceOrdersSales.contains(sale.getID()))
                                             .toList();

        StringBuilder builder = new StringBuilder();
        TextUtils.appendSectionTitle(builder, String.format("BALANCO MENSAL - %s", TextUtils.formatMonthDate(date)));
        TextUtils.appendSectionTitle(builder, "+ RECEITAS");

        BigDecimal totalSalesPrice = getTotalSalesPrice(detachedSales);
        BigDecimal totalServiceOrdersPrice = getTotalServiceOrdersPrice(closedServiceOrders);

        appendDetachedSales(builder, detachedSales, TextUtils.formatPrice(totalSalesPrice));
        appendServiceOrders(builder, closedServiceOrders, TextUtils.formatPrice(totalServiceOrdersPrice));

        BigDecimal totalIncome = totalSalesPrice.add(totalServiceOrdersPrice);
        TextUtils.appendSeparator(builder);
        builder.append(String.format("TOTAL RECEITAS: %s", TextUtils.formatPrice(totalIncome)));

        BigDecimal totalExpenses = getTotalExpenses(monthExpenses);
        appendExpenses(builder, monthExpenses, TextUtils.formatPrice(totalExpenses));

        BigDecimal balance = totalIncome.subtract(totalExpenses);
        String balanceType = balance.compareTo(BigDecimal.ZERO) < 0 ? "PREJUIZO" : "LUCRO";

        builder.append("\n");
        TextUtils.appendSectionTitle(builder, String.format("BALANCO LIQUIDO (%s): %s", balanceType, TextUtils.formatPrice(balance)));

        financialViewModel.setReport(builder.toString());
        financialViewModel.setRequestWasSuccessful(true);
    }

    /**
     * Adiciona as vendas diretas da loja ao relatório.
     * Formata e inclui as vendas que não estão associadas a ordens de serviço.
     *
     * @param builder StringBuilder para construir o relatório
     * @param detachedSales lista de vendas diretas da loja
     * @param totalPrice preço total das vendas formatado
     */
    private static void appendDetachedSales(StringBuilder builder, List<Sale> detachedSales, String totalPrice)
    {
        TextUtils.appendSubtitle(builder, "Vendas Diretas na Loja");

        for(Sale sale : detachedSales)
        {
            StoreItem storeItemSnapshot = sale.getStoreItemSnapshot();
            LocalDateTime saleDate = sale.getDate();
            String itemDescription = String.format("%s (%dx)", storeItemSnapshot.getName(), sale.getQuantity());
            String saleDescription = String.format
            (
                "%-35s %02d/%02d %02d:%02d | %s",
                itemDescription,
                saleDate.getMonthValue(),
                saleDate.getYear(),
                saleDate.getHour(),
                saleDate.getMinute(),
                TextUtils.formatPrice(sale.getTotalPrice())
            );

            TextUtils.appendFirstList(builder, saleDescription);
        }

        builder.append("\n");
        TextUtils.appendFirstList(builder, String.format("TOTAL VENDAS LOJA: %s%n", totalPrice), '+');
    }

    /**
     * Adiciona as receitas das ordens de serviço concluídas ao relatório.
     * Formata e inclui os detalhes de cada ordem de serviço finalizada.
     *
     * @param builder StringBuilder para construir o relatório
     * @param closedServiceOrders lista de ordens de serviço concluídas
     * @param totalPrice preço total das ordens formatado
     */
    private void appendServiceOrders(StringBuilder builder, List<ServiceOrder> closedServiceOrders, String totalPrice)
    {
        TextUtils.appendSubtitle(builder, "Receitas de Ordens de Servico Concluidas");

        for(ServiceOrder closedServiceOrder : closedServiceOrders)
        {
            UUID clientID = closedServiceOrder.getClientID();
            Client client = workshop.getClientRepository().getEntityWithID(clientID);

            String nameDetails = String.format("%-22s | Total: %s", client.getName(), TextUtils.formatPrice(closedServiceOrder.getTotalPrice()));
            String servicesTag = String.format("[Servicos: %s", TextUtils.formatPrice(closedServiceOrder.getServicesPrice()));
            String pricesDetails = String.format("%-22s | Produtos: %s]", servicesTag, TextUtils.formatPrice(closedServiceOrder.getSalesPrice()));

            TextUtils.appendFirstList(builder, nameDetails);
            TextUtils.appendFirstList(builder, pricesDetails, ' ');
            TextUtils.appendFirstList(builder, "Detalhamento:", ' ');

            for(ServiceItem serviceItem : closedServiceOrder.getServices())
            {
                TextUtils.appendSecondList(builder, String.format("%-30s%s", serviceItem.getName(), TextUtils.formatPrice(serviceItem.getPrice())));
            }

            for(Sale sale : closedServiceOrder.getSales())
            {
                String saleName = sale.getStoreItemSnapshot().getName();
                String priceText = TextUtils.formatPrice(sale.getTotalPrice());
                TextUtils.appendSecondList(builder, String.format("%-30s%s (%dx)", saleName, priceText, sale.getQuantity()));
            }

            builder.append('\n');
        }

        TextUtils.appendFirstList(builder, String.format("TOTAL OS: %s", totalPrice), '+');
    }

    /**
     * Adiciona as despesas do mês ao relatório.
     * Formata e inclui todas as despesas registradas no período.
     *
     * @param builder StringBuilder para construir o relatório
     * @param monthExpenses lista de despesas do mês
     * @param totalPrice preço total das despesas formatado
     */
    private void appendExpenses(StringBuilder builder, List<Expense> monthExpenses, String totalPrice)
    {
        TextUtils.appendSectionTitle(builder, "- DESPESAS");

        for(Expense expense : monthExpenses)
        {
            LocalDateTime registerTime = expense.getRegisterTime();

            String expenseDescription = String.format
            (
                "%-34s %02d/%02d %02d:%02d | %s",
                expense.getDescription(),
                registerTime.getMonthValue(),
                registerTime.getYear(),
                registerTime.getHour(),
                registerTime.getMinute(),
                TextUtils.formatPrice(expense.getAmount())
            );

            TextUtils.appendFirstList(builder, expenseDescription);
        }

        TextUtils.appendSeparator(builder);
        builder.append(String.format("TOTAL DESPESAS: %s", totalPrice));
    }

    /**
     * Calcula o preço total de uma lista de vendas.
     *
     * @param sales lista de vendas para calcular o total
     * @return valor total das vendas
     */
    private BigDecimal getTotalSalesPrice(List<Sale> sales)
    {
        return sales.stream()
                    .map(Sale::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula o preço total de uma lista de ordens de serviço.
     *
     * @param serviceOrders lista de ordens de serviço para calcular o total
     * @return valor total das ordens de serviço
     */
    private BigDecimal getTotalServiceOrdersPrice(List<ServiceOrder> serviceOrders)
    {
        return serviceOrders.stream()
                            .map(ServiceOrder::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula o valor total de uma lista de despesas.
     *
     * @param expenses lista de despesas para calcular o total
     * @return valor total das despesas
     */
    private BigDecimal getTotalExpenses(List<Expense> expenses)
    {
        return expenses.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Converte mês e ano em uma data LocalDateTime.
     * Se o mês for inválido, retorna a data atual.
     * Se o ano for inválido, usa o ano atual.
     *
     * @param month mês (1-12)
     * @param year ano
     * @return LocalDateTime representando o primeiro dia do mês/ano especificado
     */
    private LocalDateTime getTimeFromMonthAndYear(int month, int year)
    {
        if(month <= 0)
        {
            return LocalDateTime.now();
        }
        else if(year <= 0)
        {
            return LocalDateTime.of(LocalDate.now().getYear(), month, 1, 0, 0);
        }

        return LocalDateTime.of(year, month, 1, 0, 0);
    }
}