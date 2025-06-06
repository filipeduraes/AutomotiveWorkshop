// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleModuleMenu;
import com.filipeduraes.workshop.client.viewmodel.*;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientModuleMenu;

/**
 *
 * @author Filipe Durães
 */
public class CreateServiceOrderMenu implements IWorkshopMenu 
{
    private final String selectClientMessage = "Selecionar Cliente";
    private final String selectVehicleMessage = "Selecionar Veiculo";

    @Override
    public String getMenuDisplayName() 
    {
        return "Criar ordem de serviço";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        final MaintenanceViewModel maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();

        MenuResult clientSelectionMenuResult = selectClient(viewModelRegistry.getClientViewModel());

        if (clientSelectionMenuResult != null)
        {
            return clientSelectionMenuResult;
        }

        MenuResult vehicleSelectionMenuResult = selectVehicle(viewModelRegistry.getVehicleViewModel());

        if (vehicleSelectionMenuResult != null)
        {
            return vehicleSelectionMenuResult;
        }

        String problemDescription = ConsoleInput.readLine("Digite a descrição do problema: ");
        maintenanceViewModel.setCurrentStepDescription(problemDescription);
        maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_REGISTER_APPOINTMENT);

        if(maintenanceViewModel.getMaintenanceRequest() == MaintenanceRequest.REQUEST_SUCCESS)
        {
            System.out.printf("Agendamento registrado com sucesso! ID: %s%n", maintenanceViewModel.getCurrentMaintenanceID());
            return MenuResult.pop();
        }

        System.out.println("O registro do agendamento falhou, tente novamente.");
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
                return MenuResult.push(new VehicleModuleMenu());
            }

            return MenuResult.pop();
        }

        return null;
    }
}