// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuAction;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Menu abstrato para exibição e manipulação de detalhes de uma entidade.
 * Permite visualizar informações detalhadas e executar operações sobre a entidade selecionada.
 *
 * @param <TViewModel> tipo do ViewModel que gerencia os dados da entidade
 * @param <TEntityDTO> tipo do DTO que representa a entidade
 * @author Filipe Durães
 */
public abstract class EntityDetailsMenu<TViewModel extends EntityViewModel<TEntityDTO>, TEntityDTO> implements IWorkshopMenu
{
    private TViewModel viewModel;

    /**
     * Retorna o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Exibir detalhes";
    }

    /**
     * Exibe o menu de detalhes da entidade e processa as opções selecionadas.
     *
     * @param menuManager gerenciador do menu atual
     * @return resultado da operação realizada no menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        viewModel = findViewModel(menuManager);

        if (!viewModel.hasValidSelectedIndex())
        {
            System.out.println("Selecao invalida, fechando menu de detalhes...");
            return MenuResult.pop();
        }

        viewModel.OnLoadDataRequest.broadcast();

        System.out.printf("%s%n", viewModel.getSelectedDTO());
        MenuOption menuOption = menuManager.showMenuOptions("O que deseja fazer?", buildOptions(), true);
        MenuResult menuResult = menuOption.execute(menuManager);

        if(menuResult.getAction() == MenuAction.POP_MENU)
        {
            viewModel.resetSelectedDTO();
        }

        return menuResult;
    }

    /**
     * Constrói a lista de opções disponíveis no menu.
     *
     * @return lista de opções do menu
     */
    protected List<MenuOption> buildOptions()
    {
        ArrayList<MenuOption> optionsList = new ArrayList<>();
        optionsList.add(new MenuOption("Excluir", this::deleteEntity));

        return optionsList;
    }

    /**
     * Obtém o ViewModel atual associado ao menu.
     *
     * @return ViewModel atual
     */
    protected TViewModel getViewModel()
    {
        return viewModel;
    }

    /**
     * Localiza o ViewModel apropriado no gerenciador de menu.
     *
     * @param menuManager gerenciador do menu atual
     * @return ViewModel localizado
     */
    protected abstract TViewModel findViewModel(MenuManager menuManager);

    private MenuResult deleteEntity(MenuManager menuManager)
    {
        boolean canDelete = ConsoleInput.readConfirmation("Tem certeza que deseja excluir?\nEssa acao nao pode ser desfeita!");

        if (canDelete)
        {
            getViewModel().OnDeleteRequest.broadcast();
            return MenuResult.pop();
        }

        return MenuResult.none();
    }
}
