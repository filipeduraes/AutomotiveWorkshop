package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class EditEmployeeMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Editar colaborador";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return null;
    }
}
