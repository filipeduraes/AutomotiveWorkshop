// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;

/**
 * Menu para seleção de itens de serviço no sistema.
 * Permite buscar, criar temporariamente ou registrar novos itens de serviço.
 * Se um item já estiver carregado, retorna automaticamente ao menu anterior.
 *
 * @author Filipe Durães
 */
public class SelectServiceItem extends RedirectMenu
{
    /**
     * Constrói um novo menu de seleção de item de serviço.
     * Inclui opções para buscar, criar temporariamente ou registrar novos itens.
     */
    public SelectServiceItem()
    {
        super("Selecionar item de sevico", new IWorkshopMenu[]
        {
            new SearchServiceItemMenu(),
            new CreateTemporaryServiceItemMenu(),
            new RegisterServiceItemMenu()
        });
    }

    /**
     * Exibe o menu de seleção de item de serviço.
     * Se um item já estiver carregado no ViewModel, retorna automaticamente
     * ao menu anterior sem exibir as opções.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(menuManager.getViewModelRegistry().getServiceItemsViewModel().hasLoadedDTO())
        {
            return MenuResult.pop();
        }

        return super.showMenu(menuManager);
    }
}
