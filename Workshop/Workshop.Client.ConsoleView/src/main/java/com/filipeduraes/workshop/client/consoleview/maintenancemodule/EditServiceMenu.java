package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class EditServiceMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Editar servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return null;
    }
}
