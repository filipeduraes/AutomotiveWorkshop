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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class FinanceController
{
    private final InventoryService inventoryService;
    private final ServiceItemService serviceItemService;

    private final ExpenseViewModel expenseViewModel;
    private final FinancialViewModel financialViewModel;

    private final FinancialModule financialModule;
    private final Store store;
    private final Workshop workshop;

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

    public void dispose()
    {
        expenseViewModel.OnExpenseRegisterRequested.removeListener(this::registerExpense);
        expenseViewModel.OnExpenseReportRequested.removeListener(this::generateExpensesMonthReport);
        financialViewModel.OnBalanceReportRequested.removeListener(this::generateMonthBalanceReport);

        inventoryService.dispose();
        serviceItemService.dispose();
    }

    private void registerExpense()
    {
        ExpenseDTO expenseDTO = expenseViewModel.getSelectedExpense();
        Expense expense = new Expense(expenseDTO.getDescription(), expenseDTO.getAmount());
        financialModule.registerExpense(expense);
        expenseViewModel.setRequestWasSuccessful(true);
    }

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

    private BigDecimal getTotalSalesPrice(List<Sale> sales)
    {
        return sales.stream()
                    .map(Sale::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalServiceOrdersPrice(List<ServiceOrder> serviceOrders)
    {
        return serviceOrders.stream()
                            .map(ServiceOrder::getTotalPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalExpenses(List<Expense> expenses)
    {
        return expenses.stream()
            .map(Expense::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

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