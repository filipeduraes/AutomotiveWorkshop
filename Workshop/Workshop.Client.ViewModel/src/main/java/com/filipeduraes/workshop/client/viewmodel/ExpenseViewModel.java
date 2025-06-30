// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ExpenseDTO;
import com.filipeduraes.workshop.utils.Observer;

public class ExpenseViewModel implements IMonthReportViewModel
{
    public final Observer OnExpenseReportRequested = new Observer();
    public final Observer OnExpenseRegisterRequested = new Observer();

    private ExpenseDTO selectedExpense;
    private String report = "";
    private boolean requestWasSuccessful = false;
    private int selectedMonth = -1;
    private int selectedYear = -1;

    @Override
    public void setSelectedMonth(int month)
    {
        selectedMonth = month;
    }

    @Override
    public void setSelectedYear(int year)
    {
        selectedYear = year;
    }

    @Override
    public void broadcastReportRequest()
    {
        OnExpenseReportRequested.broadcast();
    }

    @Override
    public String getReport()
    {
        return report;
    }

    @Override
    public boolean getRequestWasSuccessful()
    {
        return requestWasSuccessful;
    }

    public int getSelectedMonth()
    {
        return selectedMonth;
    }

    public int getSelectedYear()
    {
        return selectedYear;
    }

    public void setRequestWasSuccessful(boolean requestWasSuccessful)
    {
        this.requestWasSuccessful = requestWasSuccessful;
    }

    public void setReport(String report)
    {
        this.report = report;
    }

    public ExpenseDTO getSelectedExpense()
    {
        return selectedExpense;
    }

    public void setSelectedExpense(ExpenseDTO expense)
    {
        this.selectedExpense = expense;
    }
}
