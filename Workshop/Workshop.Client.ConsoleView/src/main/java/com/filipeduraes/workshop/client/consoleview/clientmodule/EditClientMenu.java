package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class EditClientMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Editar cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return null;
    }
}
