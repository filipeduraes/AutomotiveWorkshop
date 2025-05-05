package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

public class SelectRegisteredClientVehicleMenu implements IWorkshopMenu
{

    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar veiculo já cadastrado do cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return MenuResult.none();
    }
}