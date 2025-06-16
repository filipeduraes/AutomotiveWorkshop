package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class EditServiceStepMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Editar etapa do servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return null;
    }
}
