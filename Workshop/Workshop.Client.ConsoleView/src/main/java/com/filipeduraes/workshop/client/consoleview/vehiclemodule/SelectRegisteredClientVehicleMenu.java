package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.VehicleRequest;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

import java.util.List;

public class SelectRegisteredClientVehicleMenu implements IWorkshopMenu
{

    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar veiculo ja cadastrado do cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        VehicleViewModel vehicleViewModel = menuManager.getVehicleViewModel();
        vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_CLIENT_VEHICLES);

        VehicleRequest currentVehicleRequest = vehicleViewModel.getCurrentVehicleRequest();

        if(currentVehicleRequest == VehicleRequest.REQUEST_SUCCESS && vehicleViewModel.clientHasVehicles())
        {
            List<String> vehicleNamesList = vehicleViewModel.getSelectedClientVehicles();
            String[] vehicleNames = new String[vehicleNamesList.size()];
            vehicleNamesList.toArray(vehicleNames);

            int selectedVehicle = ConsoleInput.readOptionFromList("Selecione um dos veiculos do cliente", vehicleNames);

            if (selectedVehicle < vehicleNames.length)
            {
                vehicleViewModel.setSelectedVehicleIndex(selectedVehicle);
                vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_SELECTED_VEHICLE_DETAILS);

                if(vehicleViewModel.getCurrentVehicleRequest() == VehicleRequest.REQUEST_SUCCESS)
                {
                    String selectedMessage = String.format("Veiculo selecionado: %s", vehicleViewModel.getSelectedClientVehicleFromIndex(selectedVehicle));
                    System.out.println(selectedMessage);

                    vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.WAITING_REQUEST);
                    return MenuResult.pop();
                }

                System.out.println("Nao foi possivel obter os detalhes do veiculo selecionado.");
            }
        }

        if(!vehicleViewModel.clientHasVehicles())
        {
            System.out.println("Nenhum veiculo cadastrado para o cliente selecionado.");
        }

        vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.WAITING_REQUEST);
        vehicleViewModel.cleanCurrentSelectedVehicle();
        return MenuResult.pop();
    }
}