// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientModuleMenu;
import com.filipeduraes.workshop.client.consoleview.maintenancemodule.MaintenanceModuleMenu;

/**
 *
 * @author Filipe Durães
 */
public class MainMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menus = 
    {
        new MaintenanceModuleMenu(),
        new ClientModuleMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Menu Principal";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual modulo deseja abrir?", menus);
        
        if(selectedOption != null)
        {
            return MenuResult.push(selectedOption);
        }
        
        return MenuResult.exit();
    }    
}
