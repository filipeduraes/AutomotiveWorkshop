// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

public class VehicleDetailsMenu extends EntityDetailsMenu<VehicleViewModel, VehicleDTO>
{
    @Override
    public String toString()
    {
        return "Buscar veiculo";
    }

    @Override
    protected VehicleViewModel findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getVehicleViewModel();
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(!menuManager.getViewModelRegistry().getVehicleViewModel().hasLoadedDTO())
        {
            boolean shouldSearchVehicle = ConsoleInput.readConfirmation("Deseja selecionar um veiculo para buscar detalhes?");

            if(shouldSearchVehicle)
            {
                return MenuResult.push(new VehicleSelectionFromClientMenu());
            }

            return MenuResult.pop();
        }

        return super.showMenu(menuManager);
    }
}
