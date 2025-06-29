// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.MaxCharactersValidator;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.RegisterVehicleMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleSelectionFromClientMenu;
import com.filipeduraes.workshop.client.viewmodel.*;
import com.filipeduraes.workshop.client.consoleview.client.ClientSelectionMenu;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

/**
 * Menu para criação de ordens de serviço.
 * Permite selecionar cliente, veículo e registrar descrições do problema
 * para criar uma nova ordem de serviço.
 *
 * @author Filipe Durães
 */
public class CreateServiceOrderMenu implements IWorkshopMenu
{
    private final PopupMenuRedirector clientRedirector = new PopupMenuRedirector(new ClientSelectionMenu());
    private final PopupMenuRedirector vehicleRedirector = new PopupMenuRedirector
    (
        new RedirectMenu
        (
            "Selecionar veiculo", new IWorkshopMenu[]
            {
                new RegisterVehicleMenu(),
                new VehicleSelectionFromClientMenu()
            }
        )
    );

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Criar ordem de servico";
    }

    /**
     * Exibe o menu de criação de ordem de serviço e processa as entradas do usuário.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        final ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();

        if (!viewModelRegistry.getClientViewModel().hasLoadedDTO())
        {
            return clientRedirector.redirect();
        }

        clientRedirector.reset();

        if (!viewModelRegistry.getVehicleViewModel().hasLoadedDTO())
        {
            return vehicleRedirector.redirect();
        }

        vehicleRedirector.reset();

        String shortDescription = ConsoleInput.readValidatedLine("Digite uma descricao curta do problema:", new MaxCharactersValidator(30));
        String detailedDescription = ConsoleInput.readLine("Digite uma descricao detalhada do problema:");

        serviceOrderViewModel.setCurrentStepShortDescription(shortDescription);
        serviceOrderViewModel.setCurrentStepDetailedDescription(detailedDescription);

        serviceOrderViewModel.OnRegisterAppointmentRequest.broadcast();

        if (serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Agendamento registrado com sucesso! ID: %s%n", serviceOrderViewModel.getSelectedDTO().getID());
            viewModelRegistry.getClientViewModel().resetSelectedDTO();
            return MenuResult.pop();
        }

        System.out.println("O registro do agendamento falhou, tente novamente.");
        return MenuResult.none();
    }
}