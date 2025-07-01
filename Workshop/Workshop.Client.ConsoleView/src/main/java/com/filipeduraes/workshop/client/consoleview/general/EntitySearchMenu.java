// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.Map;

/**
 * Menu abstrato para busca de entidades no sistema.
 * Fornece funcionalidades comuns de busca, incluindo seleção de campo de busca,
 * entrada de dados e seleção de resultados encontrados.
 *
 * @param <TViewModel> tipo do ViewModel que gerencia os dados da entidade
 * @param <TEntityDTO> tipo do DTO que representa a entidade
 * @author Filipe Durães
 */
public abstract class EntitySearchMenu<TViewModel extends EntityViewModel<TEntityDTO>, TEntityDTO> implements IWorkshopMenu
{
    private boolean alreadyRedirected = false;
    private int selectedFieldIndex = 0;

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        Map<FieldType, SearchInputStrategy> strategies = getSearchInputStrategies();
        FieldType[] searchableFields = strategies.keySet().toArray(FieldType[]::new);
        SearchInputStrategy searchInputStrategy = null;

        if(!alreadyRedirected)
        {
            if(searchableFields.length > 1)
            {
                selectedFieldIndex = ConsoleInput.readOptionFromList("Qual campo deseja pesquisar?", searchableFields, true);

                if(selectedFieldIndex >= searchableFields.length)
                {
                    System.out.println("Busca cancelada. Voltando...");
                    return MenuResult.pop();
                }
            }
            else if(searchableFields.length == 0)
            {
                System.out.println("Nao foi possivel encontrar nenhum campo de pesquisa. Voltando...");
                return MenuResult.pop();
            }
        }

        searchInputStrategy = strategies.get(searchableFields[selectedFieldIndex]);

        if(!alreadyRedirected)
        {
            if(searchInputStrategy.getStrategyType() == SearchInputStrategyType.REDIRECT_MENU && !searchInputStrategy.getValidateInput().apply(menuManager))
            {
                System.out.printf("Redirecionando para %s...%n", searchInputStrategy.getMenuToRedirect().getMenuDisplayName());
                alreadyRedirected = true;

                return MenuResult.push(searchInputStrategy.getMenuToRedirect());
            }
        }
        else if(!searchInputStrategy.getValidateInput().apply(menuManager))
        {
            System.out.println("Busca cancelada. Voltando...");
            searchInputStrategy.getCleanup().accept(menuManager);
            return MenuResult.pop();
        }

        TViewModel viewModel = getViewModel(menuManager);
        String inputValue = searchInputStrategy.getResolveInput().apply(menuManager);

        viewModel.setFieldType(searchableFields[selectedFieldIndex]);
        viewModel.setSearchPattern(inputValue);

        viewModel.OnSearchRequest.broadcast();
        searchInputStrategy.getCleanup().accept(menuManager);

        if(!viewModel.getRequestWasSuccessful())
        {
            System.out.println("Nao foi possivel encontrar nenhum registro.");
            boolean tryAgain = ConsoleInput.readConfirmation("Deseja tentar novamente?");

            if(tryAgain)
            {
                return MenuResult.none();
            }

            return MenuResult.pop();
        }

        String[] descriptions = viewModel.getFoundEntitiesDescriptions().toArray(String[]::new);
        int selectedRegister = ConsoleInput.readOptionFromList("Qual registro deseja selecionar?", descriptions, true);

        if(selectedRegister >= descriptions.length)
        {
            System.out.println("Busca cancelada. Voltando...");
        }
        else
        {
            System.out.printf("Registro selecionado: %s%n", descriptions[selectedRegister]);
            viewModel.setSelectedIndex(selectedRegister);
            viewModel.OnLoadDataRequest.broadcast();
        }

        return MenuResult.pop();
    }

    /**
     * Obtém as estratégias de entrada de busca disponíveis para cada tipo de campo.
     * Deve ser implementado pelas classes filhas para definir como cada campo
     * deve ser tratado durante a busca.
     *
     * @return mapa associando tipos de campo às suas estratégias de entrada
     */
    protected abstract Map<FieldType, SearchInputStrategy> getSearchInputStrategies();
    
    /**
     * Obtém o ViewModel responsável por gerenciar os dados da entidade.
     * Deve ser implementado pelas classes filhas para fornecer o ViewModel
     * específico da entidade sendo buscada.
     *
     * @param menuManager gerenciador de menus atual
     * @return ViewModel da entidade
     */
    protected abstract TViewModel getViewModel(MenuManager menuManager);

}