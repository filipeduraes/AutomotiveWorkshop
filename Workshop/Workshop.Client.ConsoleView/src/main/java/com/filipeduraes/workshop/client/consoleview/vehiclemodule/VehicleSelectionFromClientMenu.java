// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientSearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

import java.util.List;
import java.util.Map;

/**
 * Menu para seleção de veículo já cadastrado para um cliente.
 * Permite visualizar e selecionar veículos registrados no sistema.
 *
 * @author Filipe Durães
 */
public class VehicleSelectionFromClientMenu extends EntitySearchMenu<VehicleViewModel, VehicleDTO>
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
    protected VehicleViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getVehicleViewModel();
    }

    private static ClientViewModel getClientViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getClientViewModel();
    }
}