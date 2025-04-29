// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

/**
 *
 * @author Filipe Durães
 */
public class SelectVehicleMenu implements IWorkshopMenu
{

    @Override
    public String getMenuDisplayName() 
    {
        return "Selecionar Veículo";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        
        
        return true;
    }
}
