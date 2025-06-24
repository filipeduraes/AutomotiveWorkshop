// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

public class VehicleDetailsMenu extends EntityDetailsMenu<VehicleViewModel, VehicleDTO>
{
    private boolean alreadyRedirected = false;

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
        boolean noVehicleWasSelected = !menuManager.getViewModelRegistry().getVehicleViewModel().hasLoadedDTO();

        if(noVehicleWasSelected)
        {
            if(!alreadyRedirected)
            {
                alreadyRedirected = true;
                System.out.println("Redirecionando para a pesquisa do veiculo...");
                return MenuResult.push(new VehicleSelectionFromClientMenu());
            }
            else
            {
                alreadyRedirected = false;
                System.out.println("Nenhum veiculo selecionado. Voltando...");
                return MenuResult.pop();
            }
        }

        return super.showMenu(menuManager);
    }
}
