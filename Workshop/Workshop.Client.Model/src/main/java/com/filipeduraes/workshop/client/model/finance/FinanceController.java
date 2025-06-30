// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.finance;

import com.filipeduraes.workshop.client.dtos.ExpenseDTO;
import com.filipeduraes.workshop.client.viewmodel.ExpenseViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.financial.Expense;
import com.filipeduraes.workshop.core.financial.FinancialModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FinanceController
{
    private final InventoryService inventoryService;
    private final ServiceItemService serviceItemService;
    private final ExpenseViewModel expenseViewModel;
    private final FinancialModule financialModule;

    public FinanceController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        inventoryService = new InventoryService(viewModelRegistry, workshop);
        serviceItemService = new ServiceItemService(viewModelRegistry, workshop);

        financialModule = workshop.getFinancialModule();
        expenseViewModel = viewModelRegistry.getExpenseViewModel();

        expenseViewModel.OnExpenseRegisterRequested.addListener(this::registerExpense);
        expenseViewModel.OnExpenseReportRequested.addListener(this::generateExpensesMonthReport);
    }

    public void dispose()
    {
        expenseViewModel.OnExpenseRegisterRequested.removeListener(this::registerExpense);
        expenseViewModel.OnExpenseReportRequested.removeListener(this::generateExpensesMonthReport);

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
        List<Expense> monthExpenses;

        if(expenseViewModel.getSelectedMonth() <= 0)
        {
            monthExpenses = financialModule.getMonthExpenses(LocalDateTime.now());
        }
        else if(expenseViewModel.getSelectedYear() <= 0)
        {
            LocalDateTime date = LocalDateTime.of(LocalDate.now().getYear(), expenseViewModel.getSelectedMonth(), 1, 0, 0);
            monthExpenses = financialModule.getMonthExpenses(date);
        }
        else
        {
            LocalDateTime date = LocalDateTime.of(expenseViewModel.getSelectedYear(), expenseViewModel.getSelectedMonth(), 1, 0, 0);
            monthExpenses = financialModule.getMonthExpenses(date);
        }

        String report = monthExpenses.stream()
                                     .map(Expense::toString)
                                     .collect(Collectors.joining("\n"));

        expenseViewModel.setReport(report);
        expenseViewModel.setRequestWasSuccessful(true);
    }
}