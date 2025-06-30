// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

public interface IMonthReportViewModel
{
    void setSelectedMonth(int month);
    void setSelectedYear(int year);
    void broadcastReportRequest();
    String getReport();
    boolean getRequestWasSuccessful();
}
