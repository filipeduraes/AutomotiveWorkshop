// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

import java.util.List;

/**
 * Menu para seleção de veículo já cadastrado para um cliente.
 * Permite visualizar e selecionar veículos registrados no sistema.
 *
 * @author Filipe Durães
 */
public class VehicleSelectionMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return Nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar veiculo ja cadastrado do cliente";
    }

    /**
     * Exibe o menu de seleção de veículos e processa a escolha do usuário.
     *
     * @param menuManager Gerenciador de menus que controla a navegação
     * @return Resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        VehicleViewModel vehicleViewModel = menuManager.getViewModelRegistry().getVehicleViewModel();
        vehicleViewModel.OnSearchRequest.broadcast();

        if (vehicleViewModel.getWasRequestSuccessful() && vehicleViewModel.hasAnyFoundEntities())
        {
            List<String> vehicleNamesList = vehicleViewModel.getFoundEntitiesDescriptions();
            String[] vehicleNames = vehicleNamesList.toArray(String[]::new);

            int selectedVehicleIndex = ConsoleInput.readOptionFromList("Selecione um dos veiculos do cliente", vehicleNames);

            if (selectedVehicleIndex < vehicleNames.length)
            {
                vehicleViewModel.setSelectedIndex(selectedVehicleIndex);
                vehicleViewModel.OnLoadDataRequest.broadcast();

                if (vehicleViewModel.getWasRequestSuccessful())
                {
                    VehicleDTO selectedVehicle = vehicleViewModel.getSelectedDTO();
                    String selectedMessage = String.format("Veiculo selecionado:%n%s", selectedVehicle);
                    System.out.println(selectedMessage);

                    return MenuResult.pop();
                }

                System.out.println("Nao foi possivel obter os detalhes do veiculo selecionado.");
            }
        }

        if (!vehicleViewModel.hasAnyFoundEntities())
        {
            System.out.println("Nenhum veiculo cadastrado para o cliente selecionado.");
        }

        vehicleViewModel.resetSelectedDTO();
        return MenuResult.pop();
    }
}