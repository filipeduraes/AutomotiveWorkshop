// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.LicensePlateValidator;
import com.filipeduraes.workshop.client.consoleview.input.YearInputValidator;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;

/**
 * Menu responsável pelo registro de novos veículos no sistema.
 * Gerencia a interface de usuário para coletar e validar dados do veículo.
 *
 * @author Filipe Durães
 */
public class RegisterVehicleMenu implements IWorkshopMenu
{

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return O nome do menu que será exibido para o usuário
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Registrar Veiculo";
    }

    /**
     * Exibe o menu de registro de veículo e processa as entradas do usuário.
     * Verifica se há um cliente selecionado antes de prosseguir com o registro.
     *
     * @param menuManager O gerenciador de menus que controla a navegação
     * @return O resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();

        if (!viewModelRegistry.getClientViewModel().hasLoadedDTO())
        {
            System.out.println("Nenhum cliente selecionado para o qual o veiculo sera registrado.");
            System.out.println("Por favor selecione um cliente antes de prosseguir.");
            return MenuResult.pop();
        }

        VehicleDTO vehicleDTO = requestVehicleData();
        VehicleViewModel vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        vehicleViewModel.setSelectedDTO(vehicleDTO);

        vehicleViewModel.OnVehicleRegistrationRequest.broadcast();

        if (vehicleViewModel.getRequestWasSuccessful())
        {
            return MenuResult.pop();
        }
        else
        {
            boolean tryRegisteringAgain = ConsoleInput.readConfirmation("Nao foi possivel registrar o veiculo, deseja tentar novamente?");

            if (!tryRegisteringAgain)
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
        String vehicleLicensePlate = ConsoleInput.readValidatedLine(new LicensePlateValidator());

        System.out.println("Insira o ano do veiculo:");
        int vehicleYear = Integer.parseInt(ConsoleInput.readValidatedLine(new YearInputValidator()));

        VehicleDTO vehicleDTO = new VehicleDTO(vehicleModel, vehicleColor, vehicleVinNumber, vehicleLicensePlate, vehicleYear);
        return vehicleDTO;
    }
}
