// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

/**
 *
 * @author Filipe Durães
 */
public class ClientModuleMenu implements IWorkshopMenu 
{
    private final IWorkshopMenu[] menus = 
    {
        new ClientRegistrationMenu(),
        new ClientSearchMenu()
    };
    
    @Override
    public String getMenuDisplayName() 
    {
        return "Modulo de Clientes";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        IWorkshopMenu submenu = menuManager.showSubmenuOptions("O que deseja fazer?", menus);
        
        if(submenu == null)
        {
            return false;
        }
        
        menuManager.pushMenu(submenu);
        return false;
    }
}
