package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

import java.util.List;

public class VehicleSelectionMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar veiculo ja cadastrado do cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        VehicleViewModel vehicleViewModel = menuManager.getViewModelRegistry().getVehicleViewModel();
        vehicleViewModel.OnClientVehiclesRequest.broadcast();

        if(vehicleViewModel.getWasRequestSuccessful() && vehicleViewModel.clientHasVehicles())
        {
            List<String> vehicleNamesList = vehicleViewModel.getSelectedClientVehicles();
            String[] vehicleNames = new String[vehicleNamesList.size()];
            vehicleNamesList.toArray(vehicleNames);

            int selectedVehicleIndex = ConsoleInput.readOptionFromList("Selecione um dos veiculos do cliente", vehicleNames);

            if (selectedVehicleIndex < vehicleNames.length)
            {
                vehicleViewModel.setSelectedVehicleIndex(selectedVehicleIndex);
                vehicleViewModel.OnVehicleDetailsRequest.broadcast();

                if(vehicleViewModel.getWasRequestSuccessful())
                {
                    VehicleDTO selectedVehicle = vehicleViewModel.getSelectedVehicle();
                    String selectedMessage = String.format("Veiculo selecionado:%n%s", selectedVehicle);
                    System.out.println(selectedMessage);

                    return MenuResult.pop();
                }

                System.out.println("Nao foi possivel obter os detalhes do veiculo selecionado.");
            }
        }

        if(!vehicleViewModel.clientHasVehicles())
        {
            System.out.println("Nenhum veiculo cadastrado para o cliente selecionado.");
        }

        vehicleViewModel.cleanCurrentSelectedVehicle();
        return MenuResult.pop();
    }
}