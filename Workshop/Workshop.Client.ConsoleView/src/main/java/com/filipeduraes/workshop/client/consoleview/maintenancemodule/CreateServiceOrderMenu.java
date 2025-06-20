// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleMenu;
import com.filipeduraes.workshop.client.viewmodel.*;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientSelectionMenu;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

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
        return "Criar ordem de servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        final ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();

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

        String shortDescription = ConsoleInput.readLine("Digite uma descricao curta do problema:");
        String detailedDescription = ConsoleInput.readLine("Digite uma descricao detalhada do problema:");

        serviceViewModel.setCurrentStepShortDescription(shortDescription);
        serviceViewModel.setCurrentStepDetailedDescription(detailedDescription);

        serviceViewModel.OnRegisterAppointmentRequest.broadcast();

        if(serviceViewModel.getWasRequestSuccessful())
        {
            System.out.printf("Agendamento registrado com sucesso! ID: %s%n", serviceViewModel.getSelectedDTO().getID());
            viewModelRegistry.getClientViewModel().resetSelectedDTO();
            return MenuResult.pop();
        }

        System.out.println("O registro do agendamento falhou, tente novamente.");
        return MenuResult.none();
    }

    private MenuResult selectClient(ClientViewModel clientViewModel)
    {
        if(!clientViewModel.hasLoadedDTO())
        {
            System.out.println("- CLIENTE");
            int selectedOption = ConsoleInput.readOptionFromList("Selecione o cliente", new String[] { selectClientMessage }, true);

            if(selectedOption == 0)
            {
                return MenuResult.push(new ClientSelectionMenu());
            }

            return MenuResult.pop();
        }

        return null;
    }

    private MenuResult selectVehicle(VehicleViewModel vehicleViewModel)
    {
        if(!vehicleViewModel.hasLoadedDTO())
        {
            int selectedOption = ConsoleInput.readOptionFromList("Selecione o veiculo", new String[] { selectVehicleMessage }, true);

            if(selectedOption == 0)
            {
                return MenuResult.push(new VehicleMenu());
            }

            return MenuResult.pop();
        }

        return null;
    }
}