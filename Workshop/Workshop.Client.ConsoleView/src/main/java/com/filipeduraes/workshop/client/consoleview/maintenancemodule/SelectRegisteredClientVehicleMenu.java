package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

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
        return "Selecionar veiculo já cadastrado do cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        VehicleViewModel vehicleViewModel = menuManager.getVehicleViewModel();
        vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_CLIENT_VEHICLES);

        VehicleRequest currentVehicleRequest = vehicleViewModel.getCurrentVehicleRequest();

        if(currentVehicleRequest == VehicleRequest.REQUEST_SUCCESS)
        {
            List<String> vehicleNamesList = vehicleViewModel.getSelectedClientVehicles();
            String[] vehicleNames = new String[vehicleNamesList.size()];
            vehicleNamesList.toArray(vehicleNames);

            ConsoleInput.readOptionFromList("Selecione um dos veiculos do cliente", vehicleNames);
        }
        else
        {
            System.out.println("Nenhum veiculo cadastrado para o cliente selecionado.");
            return MenuResult.pop();
        }

        return MenuResult.none();
    }
}