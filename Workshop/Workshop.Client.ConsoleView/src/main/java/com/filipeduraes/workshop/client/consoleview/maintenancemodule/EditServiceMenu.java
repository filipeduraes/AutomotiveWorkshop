// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.auth.EmployeeSearchMenu;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientSelectionMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.MaxCharactersValidator;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.RegisterVehicleMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleSelectionFromClientMenu;
import com.filipeduraes.workshop.client.dtos.*;
import com.filipeduraes.workshop.client.viewmodel.*;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

import java.util.Arrays;
import java.util.Deque;
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
    private FieldType lastSelectedStepEditField = FieldType.NONE;
    private int lastSelectedStepIndex = -1;

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

        selectedOption = menuManager.showMenuOptions("O que deseja editar?", options, true);

        return selectedOption.execute(menuManager);
    }

    private MenuResult editClient(MenuManager menuManager)
    {
        lastSelectedStepEditField = FieldType.NONE;

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
            serviceViewModel.setFieldType(FieldType.CLIENT);
            serviceViewModel.OnEditServiceRequest.broadcast();

            resultMessage = serviceViewModel.getRequestWasSuccessful()
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
        lastSelectedStepEditField = FieldType.NONE;

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
            serviceViewModel.setFieldType(FieldType.VEHICLE);
            serviceViewModel.OnEditServiceRequest.broadcast();

            resultMessage = serviceViewModel.getRequestWasSuccessful()
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
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();
        List<ServiceStepDTO> steps = serviceViewModel.getSelectedDTO().getSteps();

        EmployeeViewModel employeeViewModel = viewModelRegistry.getEmployeeViewModel();

        if(lastSelectedStepEditField != FieldType.EMPLOYEE)
        {
            ServiceStepTypeDTO[] possibleTypes = Arrays.copyOfRange(ServiceStepTypeDTO.values(), 1, steps.size() + 1);

            lastSelectedStepIndex = ConsoleInput.readOptionFromList("Escolha a etapa a ser alterada", possibleTypes, true);

            if(lastSelectedStepIndex >= steps.size() + 1)
            {
                System.out.println("Edicao cancelada. Voltando...");
                return MenuResult.pop();
            }

            FieldType[] possibleFields = { FieldType.SHORT_DESCRIPTION, FieldType.DETAILED_DESCRIPTION, FieldType.EMPLOYEE };

            int selectedEditOptionIndex = ConsoleInput.readOptionFromList("Qual campo deseja alterar?", possibleFields, true);
            lastSelectedStepEditField = possibleFields[selectedEditOptionIndex];

            if(lastSelectedStepEditField == FieldType.EMPLOYEE)
            {
                employeeViewModel.resetSelectedDTO();
            }
        }
        else if(!employeeViewModel.hasLoadedDTO())
        {
            lastSelectedStepEditField = FieldType.NONE;
            System.out.println("Nenhum colaborador selecionado. Voltando...");
            return MenuResult.pop();
        }

        if(lastSelectedStepEditField == FieldType.EMPLOYEE && !employeeViewModel.hasLoadedDTO())
        {
            System.out.println("Selecione um novo colaborador. Redirecionando...");
            return MenuResult.push(new EmployeeSearchMenu());
        }

        readNewDescriptions(serviceViewModel);

        serviceViewModel.setFieldType(lastSelectedStepEditField);
        serviceViewModel.setSelectedStepIndex(lastSelectedStepIndex);
        serviceViewModel.OnEditServiceStepRequest.broadcast();

        if(serviceViewModel.getRequestWasSuccessful())
        {
            System.out.println("Etapa alterada com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel alterar a etapa, tente novamente.");
        }

        return MenuResult.pop();
    }

    private void readNewDescriptions(ServiceViewModel serviceViewModel)
    {
        switch (lastSelectedStepEditField)
        {
            case SHORT_DESCRIPTION ->
            {
                String newShortDescription = ConsoleInput.readValidatedLine("Digite uma nova descricao curta do problema", new MaxCharactersValidator(30));
                serviceViewModel.setCurrentStepShortDescription(newShortDescription);
            }
            case DETAILED_DESCRIPTION ->
            {
                String newDetailedDescription = ConsoleInput.readLine("Digite uma nova descricao detalhada do problema");
                serviceViewModel.setCurrentStepDetailedDescription(newDetailedDescription);
            }
        }
    }
}