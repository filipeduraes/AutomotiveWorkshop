package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.VehicleRequest;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;

public class RegisterVehicleMenu implements IWorkshopMenu
{

    @Override
    public String getMenuDisplayName()
    {
        return "Registrar Veiculo";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();

        if(!viewModelRegistry.getClientViewModel().hasSelectedClient())
        {
            System.out.println("Nenhum cliente selecionado para o qual o veiculo sera registrado.");
            System.out.println("Por favor selecione um cliente antes de prosseguir.");
            return MenuResult.pop();
        }

        System.out.println("Insira o modelo do veiculo:");
        String vehicleModel = ConsoleInput.readLine();

        System.out.println("Insira a cor do veiculo:");
        String vehicleColor = ConsoleInput.readLine();

        System.out.println("Insira o numero de chassi do veiculo:");
        String vehicleVinNumber = ConsoleInput.readLine();

        System.out.println("Insira a placa do veiculo:");
        String vehicleLicensePlate = ConsoleInput.readLine();

        System.out.println("Insira o ano do veiculo:");
        int vehicleYear = ConsoleInput.readLineInteger();

        VehicleViewModel vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        VehicleDTO vehicleDTO = new VehicleDTO(vehicleModel, vehicleColor, vehicleVinNumber, vehicleLicensePlate, vehicleYear);
        vehicleViewModel.setSelectedVehicle(vehicleDTO);

        vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_VEHICLE_REGISTRATION);
        VehicleRequest currentVehicleRequest = vehicleViewModel.getCurrentVehicleRequest();

        if(currentVehicleRequest == VehicleRequest.REQUEST_SUCCESS)
        {
            return MenuResult.pop();
        }
        else if(currentVehicleRequest == VehicleRequest.REQUEST_FAILED)
        {
            boolean tryRegisteringAgain = ConsoleInput.readConfirmation("Nao foi possivel registrar o veiculo, deseja tentar novamente?");

            if(!tryRegisteringAgain)
            {
                return MenuResult.pop();
            }
        }

        vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.WAITING_REQUEST);
        return MenuResult.none();
    }
}
