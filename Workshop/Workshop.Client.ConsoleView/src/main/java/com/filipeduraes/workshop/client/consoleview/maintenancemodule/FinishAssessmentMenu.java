package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class FinishAssessmentMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Finalizar inspecao";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return null;
    }
}
