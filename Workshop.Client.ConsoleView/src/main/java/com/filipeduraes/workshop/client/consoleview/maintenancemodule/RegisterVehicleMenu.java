package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

public class RegisterVehicleMenu implements IWorkshopMenu
{

    @Override
    public String getMenuDisplayName()
    {
        return "Registrar Veículo";
    }

    @Override
    public boolean showMenu(MenuManager menuManager)
    {

        return false;
    }
}
