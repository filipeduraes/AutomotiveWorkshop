// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.employee;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.ShowMonthReportMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;

public class ShowMonthClockInMenu extends ShowMonthReportMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Mostrar pontos do mes";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        super.showMenu(menuManager);
        ConsoleInput.readOptionFromList("O que deseja fazer?", new String[]{"X Voltar"});

        return MenuResult.pop();
    }

    @Override
    protected IMonthReportViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getEmployeeViewModel();
    }
}