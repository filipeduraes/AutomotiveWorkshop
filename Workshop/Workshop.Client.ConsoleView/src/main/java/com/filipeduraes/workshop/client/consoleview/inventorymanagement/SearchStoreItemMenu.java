// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchStoreItemMenu extends EntitySearchMenu<EntityViewModel<StoreItemDTO>, StoreItemDTO>
{
    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar Item no Inventario";
    }

    @Override
    protected Map<FieldType, SearchInputStrategy> getSearchInputStrategies()
    {
        Map<FieldType, SearchInputStrategy> strategies = new LinkedHashMap<>();

        strategies.put(FieldType.NAME, new SearchInputStrategy("Digite o nome do item"));
        strategies.put(FieldType.DESCRIPTION, new SearchInputStrategy("Digite a descricao do item"));

        return strategies;
    }

    @Override
    protected EntityViewModel<StoreItemDTO> getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getInventoryViewModel();
    }
}
