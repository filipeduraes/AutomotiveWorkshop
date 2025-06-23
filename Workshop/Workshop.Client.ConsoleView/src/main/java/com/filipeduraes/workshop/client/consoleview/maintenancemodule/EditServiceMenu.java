// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientSelectionMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.RegisterVehicleMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleSelectionFromClientMenu;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

import java.util.List;

/**
 * Menu para edição de serviços.
 * Permite modificar o cliente, veículo e etapa de um serviço existente.
 *
 * @author Filipe Durães
 */
public class EditServiceMenu implements IWorkshopMenu
{
    private MenuOption selectedOption;

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return o nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Editar servico";
    }

    /**
     * Exibe o menu de edição de serviço e processa a opção selecionada.
     *
     * @param menuManager o gerenciador de menus
     * @return o resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if (selectedOption != null)
        {
            return selectedOption.execute(menuManager);
        }

        List<MenuOption> options = List.of
        (
            new MenuOption("Cliente", this::editClient),
            new MenuOption("Veiculo", this::editVehicle),
            new MenuOption("Etapa", this::editStep)
        );

        selectedOption = menuManager.showMenuOptions("O que deseja editar?", options);
        return selectedOption.execute(menuManager);
    }

    private MenuResult editClient(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ClientViewModel clientViewModel = viewModelRegistry.getClientViewModel();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();
        VehicleViewModel vehicleViewModel = viewModelRegistry.getVehicleViewModel();

        if (!clientViewModel.hasLoadedDTO())
        {
            if (ConsoleInput.readConfirmation("Deseja selecionar um novo cliente?"))
            {
                return MenuResult.push(new ClientSelectionMenu());
            }

            return MenuResult.pop();
        }

        if (!vehicleViewModel.hasLoadedDTO())
        {
            if (ConsoleInput.readConfirmation("Deseja selecionar um novo veiculo?"))
            {
                return MenuResult.push(new RedirectMenu
                (
                    "Selecionar veiculo", new IWorkshopMenu[]
                    {
                        new RegisterVehicleMenu(),
                        new VehicleSelectionFromClientMenu()
                    }
                ));
            }

            return MenuResult.pop();
        }

        ServiceOrderDTO serviceOrderDTO = serviceViewModel.getSelectedDTO();
        ClientDTO clientDTO = clientViewModel.getSelectedDTO();
        String resultMessage;

        if (!serviceOrderDTO.getClientID().equals(clientDTO.getID()))
        {
            serviceViewModel.setEditFieldType(FieldType.CLIENT);
            serviceViewModel.OnEditServiceRequest.broadcast();

            resultMessage = serviceViewModel.getWasRequestSuccessful()
                            ? "Cliente alterado com sucesso!"
                            : "Nao foi possivel alterar o cliente. Tente novamente.";
        }
        else
        {
            resultMessage = "Cliente selecionado e o mesmo do servico. Nenhuma alteracao foi feita.";
        }

        System.out.println(resultMessage);
        return MenuResult.pop();
    }

    private MenuResult editVehicle(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        VehicleViewModel vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();

        if (!vehicleViewModel.hasLoadedDTO())
        {
            return MenuResult.push(new VehicleSelectionFromClientMenu());
        }

        ServiceOrderDTO serviceOrderDTO = serviceViewModel.getSelectedDTO();
        VehicleDTO vehicleDTO = vehicleViewModel.getSelectedDTO();
        String resultMessage;

        if (!serviceOrderDTO.getVehicleID().equals(vehicleDTO.getID()))
        {
            serviceViewModel.setEditFieldType(FieldType.VEHICLE);
            serviceViewModel.OnEditServiceRequest.broadcast();

            resultMessage = serviceViewModel.getWasRequestSuccessful()
                            ? "Veiculo alterado com sucesso!"
                            : "Nao foi possivel alterar o veiculo. Tente novamente.";
        }
        else
        {
            resultMessage = "Veiculo selecionado e o mesmo do servico. Nenhuma alteracao foi feita.";
        }

        System.out.println(resultMessage);
        return MenuResult.pop();
    }

    private MenuResult editStep(MenuManager menuManager)
    {
        return null;
    }
}