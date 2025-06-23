// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;


/**
 * Menu para consulta de serviços gerais da oficina.
 * Implementa a interface de menu da oficina para lidar com a exibição
 * e seleção de serviços gerais.
 *
 * @author Filipe Durães
 */
public class QueryGeneralServicesMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return o nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Servicos Gerais";
    }

    /**
     * Exibe o menu de serviços gerais e configura o tipo de consulta.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado do menu indicando a próxima tela
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();
        serviceViewModel.setQueryType(ServiceQueryType.GENERAL);
        System.out.println("Servicos gerais selecionados");

        return MenuResult.replace(new QueryServicesMenu());
    }
}
