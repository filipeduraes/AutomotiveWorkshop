// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.services.item.SelectServiceItem;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

/**
 * Menu para adicionar itens de serviço a uma ordem de serviço.
 * Permite selecionar um item de serviço e associá-lo à ordem de serviço atual,
 * redirecionando para o menu de seleção se necessário.
 *
 * @author Filipe Durães
 */
public class AddServiceItemToServiceOrder implements IWorkshopMenu
{
    PopupMenuRedirector selectServiceItemRedirector = new PopupMenuRedirector(new SelectServiceItem());

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Adicionar item de servico a ordem de servico";
    }

    /**
     * Exibe o menu de adição de item de serviço à ordem de serviço.
     * Se não houver um item de serviço selecionado, redireciona para o menu de seleção.
     * Caso contrário, adiciona o item à ordem de serviço atual.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();

        if(!viewModelRegistry.getServiceItemsViewModel().hasLoadedDTO())
        {
            return selectServiceItemRedirector.redirect();
        }

        selectServiceItemRedirector.reset();

        ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        serviceOrderViewModel.OnAddServiceItemRequested.broadcast();

        if(serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.println("Item de servico adicionado com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel adicionar o item de servico, tente novamente.");
        }

        return MenuResult.pop();
    }
}
