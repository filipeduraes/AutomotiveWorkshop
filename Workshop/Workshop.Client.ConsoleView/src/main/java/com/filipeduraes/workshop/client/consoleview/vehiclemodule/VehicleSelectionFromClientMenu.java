// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.client.ClientSearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.Map;

/**
 * Menu de seleção de veículos para um cliente.
 * Permite ao usuário visualizar e selecionar veículos já cadastrados no sistema
 * para um cliente específico, facilitando a identificação e escolha de veículos
 * para operações futuras.
 *
 * @author Filipe Durães
 */
public class VehicleSelectionFromClientMenu extends EntitySearchMenu<EntityViewModel<VehicleDTO>, VehicleDTO>
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return Nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar veiculo";
    }

    @Override
    protected Map<FieldType, SearchInputStrategy> getSearchInputStrategies()
    {
        return Map.of
        (
            FieldType.CLIENT, new SearchInputStrategy
            (
                new ClientSearchMenu(),
                (menuManager) -> getClientViewModel(menuManager).getSelectedDTO().getID().toString(),
                (menuManager) -> getClientViewModel(menuManager).hasValidSelectedIndex(),
                (menuManager) -> getClientViewModel(menuManager).resetSelectedDTO()
            )
        );
    }

    @Override
    protected EntityViewModel<VehicleDTO> getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getVehicleViewModel();
    }

    private static EntityViewModel<ClientDTO> getClientViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getClientViewModel();
    }
}