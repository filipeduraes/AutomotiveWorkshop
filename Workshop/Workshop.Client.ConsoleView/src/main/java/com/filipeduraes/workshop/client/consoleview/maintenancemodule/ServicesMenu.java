// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

/**
 *
 * @author Filipe Durães
 */
public class ServicesMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] submenus =
    {
        new CreateServiceOrderMenu(),
        new QueryOpenedServicesMenu(),
        new QueryUserServicesMenu(),
        new QueryGeneralServicesMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu selectedSubmenu = menuManager.showSubmenuOptions("O que deseja fazer?", submenus);

        if (selectedSubmenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(selectedSubmenu);
    }
}