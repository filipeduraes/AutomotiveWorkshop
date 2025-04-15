// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.consoleview.maintanencemodule.ServiceModuleMenu;

/**
 *
 * @author Filipe Durães
 */
public class MainMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menus = 
    {
        new ServiceModuleMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Menu Principal";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual modulo deseja abrir?", menus);
        
        if(selectedOption != null)
        {
            menuManager.pushMenu(selectedOption);
        }
        
        return false;
    }    
}
