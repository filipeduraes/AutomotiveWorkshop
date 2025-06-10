// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

/**
 *
 * @author Filipe Durães
 */
public class VehicleMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menuOptions =
    {
        new RegisterVehicleMenu(),
        new VehicleSelectionMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Veiculo";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        VehicleViewModel vehicleViewModel = menuManager.getViewModelRegistry().getVehicleViewModel();

        if(!vehicleViewModel.hasSelectedVehicle())
        {
            IWorkshopMenu submenu = menuManager.showSubmenuOptions("Selecione um veiculo. O que deseja fazer?", menuOptions);

            if(submenu != null)
            {
                return MenuResult.push(submenu);
            }
        }

        return MenuResult.pop();
    }
}
