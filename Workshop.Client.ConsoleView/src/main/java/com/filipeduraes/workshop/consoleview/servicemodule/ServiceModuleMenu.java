// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.consoleview.servicemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

/**
 *
 * @author Filipe Durães
 */
public class ServiceModuleMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName() 
    {
        return "Modulo de Servicos";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        return true;
    }
}
