// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Menu de pesquisa de itens de serviço.
 * Permite ao usuário buscar itens de serviço cadastrados no sistema por nome ou descrição,
 * facilitando a localização e seleção de serviços para operações futuras.
 *
 * Este menu é utilizado para navegação e seleção de itens do catálogo de serviços.
 *
 * @author Filipe Durães
 */
public class SearchServiceItemMenu extends EntitySearchMenu<EntityViewModel<PricedItemDTO>, PricedItemDTO>
{
    @Override
    public String getMenuDisplayName()
    {
        return "Pesquisar Item de Servico";
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
    protected EntityViewModel<PricedItemDTO> getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getServiceItemsViewModel();
    }
}
