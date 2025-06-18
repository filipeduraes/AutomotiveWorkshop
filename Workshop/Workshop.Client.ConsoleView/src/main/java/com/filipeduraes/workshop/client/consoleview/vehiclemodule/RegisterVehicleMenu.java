package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
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

        if(!viewModelRegistry.getClientViewModel().hasLoadedDTO())
        {
            System.out.println("Nenhum cliente selecionado para o qual o veiculo sera registrado.");
            System.out.println("Por favor selecione um cliente antes de prosseguir.");
            return MenuResult.pop();
        }

        VehicleDTO vehicleDTO = requestVehicleData();
        VehicleViewModel vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        vehicleViewModel.setSelectedDTO(vehicleDTO);

        vehicleViewModel.OnVehicleRegistrationRequest.broadcast();

        if(vehicleViewModel.getWasRequestSuccessful())
        {
            return MenuResult.pop();
        }
        else
        {
            boolean tryRegisteringAgain = ConsoleInput.readConfirmation("Nao foi possivel registrar o veiculo, deseja tentar novamente?");

            if(!tryRegisteringAgain)
            {
                return MenuResult.pop();
            }
        }

        return MenuResult.none();
    }

    private static VehicleDTO requestVehicleData()
    {
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

        VehicleDTO vehicleDTO = new VehicleDTO(vehicleModel, vehicleColor, vehicleVinNumber, vehicleLicensePlate, vehicleYear);
        return vehicleDTO;
    }
}
