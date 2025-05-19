// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.SelectVehicleMenu;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientModuleMenu;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

/**
 *
 * @author Filipe Durães
 */
public class CreateServiceOrderMenu implements IWorkshopMenu 
{
    private String selectClientMessage = "Selecionar Cliente";
    private String selectVehicleMessage = "Selecionar Veiculo";

    @Override
    public String getMenuDisplayName() 
    {
        return "Criar ordem de serviço";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        final ClientViewModel clientViewModel = menuManager.getClientViewModel();
        final VehicleViewModel vehicleViewModel = menuManager.getVehicleViewModel();

        MenuResult clientSelectionMenuResult = selectClient(clientViewModel);

        if (clientSelectionMenuResult != null)
        {
            return clientSelectionMenuResult;
        }

        MenuResult vehicleSelectionMenuResult = selectVehicle(vehicleViewModel);

        if (vehicleSelectionMenuResult != null)
        {
            return vehicleSelectionMenuResult;
        }

        return MenuResult.none();
    }

    private MenuResult selectClient(ClientViewModel clientViewModel)
    {
        if(!clientViewModel.hasSelectedClient())
        {
            System.out.println("- CLIENTE");
            int selectedOption = ConsoleInput.readOptionFromList("Selecione o cliente", new String[] { selectClientMessage }, true);

            if(selectedOption == 0)
            {
                return MenuResult.push(new ClientModuleMenu());
            }

            return MenuResult.pop();
        }

        return null;
    }

    private MenuResult selectVehicle(VehicleViewModel vehicleViewModel)
    {
        if(!vehicleViewModel.hasSelectedVehicle())
        {
            System.out.println("- VEICULO");

            int selectedOption = ConsoleInput.readOptionFromList("Selecione o veiculo", new String[] { selectVehicleMessage }, true);

            if(selectedOption == 0)
            {
                return MenuResult.push(new SelectVehicleMenu());
            }

            return MenuResult.pop();
        }

        return null;
    }
}