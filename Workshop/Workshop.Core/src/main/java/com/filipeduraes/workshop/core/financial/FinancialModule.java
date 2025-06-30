// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.financial;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FinancialModule
{
    private final List<Expense> currentMonthExpenses;
    private final String expensesCurrentMonthPath;
    private final ParameterizedType expensesArrayType;

    public FinancialModule()
    {
        expensesCurrentMonthPath = WorkshopPaths.getExpensesCurrentMonthPath();
        expensesArrayType = Persistence.createParameterizedType(List.class, Expense.class);
        currentMonthExpenses = Persistence.loadFile(expensesCurrentMonthPath, expensesArrayType, new ArrayList<>());
    }

    public void registerExpense(Expense expense)
    {
        currentMonthExpenses.add(expense);
        Persistence.saveFile(currentMonthExpenses, expensesCurrentMonthPath);
    }

    public List<Expense> getMonthExpenses(LocalDateTime date)
    {
        String expensesMonthPath = WorkshopPaths.getExpensesMonthPath(date);
        return Persistence.loadFile(expensesMonthPath, expensesArrayType, new ArrayList<>());
    }
}
