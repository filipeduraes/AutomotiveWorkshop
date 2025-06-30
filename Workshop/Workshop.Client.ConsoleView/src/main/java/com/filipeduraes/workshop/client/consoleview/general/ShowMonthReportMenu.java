// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;

public abstract class ShowMonthReportMenu implements IWorkshopMenu
{
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        boolean useCurrentMonth = ConsoleInput.readConfirmation("Deseja mostrar o relatorio do mes atual?");
        int month = -1;
        int year = -1;

        if(!useCurrentMonth)
        {
            month = ConsoleInput.readLineInteger("Insira o mes desejado (1-12)", 1, 12);

            boolean useCurrentYear = ConsoleInput.readConfirmation("Deseja mostrar o relatorio do ano atual?");

            if(!useCurrentYear)
            {
                year = ConsoleInput.readLineInteger("Insira o ano desejado", 2000, 2100);
            }
        }

        IMonthReportViewModel employeeViewModel = getViewModel(menuManager);
        employeeViewModel.setSelectedMonth(month);
        employeeViewModel.setSelectedYear(year);

        employeeViewModel.broadcastReportRequest();

        String report = employeeViewModel.getReport();

        if(employeeViewModel.getRequestWasSuccessful() && report != null && !report.isBlank())
        {
            System.out.println(report);
        }
        else
        {
            System.out.println("Nao foi possivel obter o relatorio do mes selecionado.");
        }

        if(canShowConfirmation())
        {
            System.out.println();
            ConsoleInput.readOptionFromList("O que deseja fazer?", new String[]{"X Voltar"});
        }

        return MenuResult.pop();
    }

    protected abstract IMonthReportViewModel getViewModel(MenuManager menuManager);

    protected boolean canShowConfirmation()
    {
        return true;
    }
}
