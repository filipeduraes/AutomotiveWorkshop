// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

/**
 *
 * @author Filipe Durães
 */
public class SelectVehicleMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] menuOptions =
    {
        new RegisterVehicleMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Selecionar Veículo";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        
        
        return MenuResult.none();
    }
}
