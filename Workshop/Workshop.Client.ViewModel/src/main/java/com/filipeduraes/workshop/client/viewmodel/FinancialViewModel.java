// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;

public class FinancialViewModel implements IMonthReportViewModel
{
    public final Observer OnBalanceReportRequested = new Observer();

    private String report = "";
    private int selectedMonth = -1;
    private int selectedYear = -1;
    private boolean requestWasSuccessful = false;

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
        OnBalanceReportRequested.broadcast();
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

    public void setReport(String report)
    {
        this.report = report;
    }

    public void setRequestWasSuccessful(boolean requestWasSuccessful)
    {
        this.requestWasSuccessful = requestWasSuccessful;
    }
}
