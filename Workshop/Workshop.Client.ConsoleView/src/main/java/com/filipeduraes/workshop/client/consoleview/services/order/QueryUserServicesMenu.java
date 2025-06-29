// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;

/**
 * Menu para consulta de serviços associados ao usuário atual.
 * Permite visualizar todos os serviços vinculados ao usuário logado no sistema.
 *
 * @author Filipe Durães
 */

public class QueryUserServicesMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Meus Servicos";
    }

    /**
     * Exibe o menu de serviços do usuário e configura o tipo de consulta.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceViewModel();
        serviceOrderViewModel.setQueryType(ServiceQueryType.USER);

        System.out.println("Servicos do usuario selecionados");

        return MenuResult.replace(new QueryServicesMenu());
    }
}
