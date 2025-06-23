package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class EmployeeMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] menus =
    {
        new RegisterEmployeeMenu(),
    };

    @Override
    public String getMenuDisplayName()
    {
        return "Colaborador";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu selectedMenu = menuManager.showSubmenuOptions("Qual menu deseja acessar?", menus, true);

        if(selectedMenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(selectedMenu);
    }
}
