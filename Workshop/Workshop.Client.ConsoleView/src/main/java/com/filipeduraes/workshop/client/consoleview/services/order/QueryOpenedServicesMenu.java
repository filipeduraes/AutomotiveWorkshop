// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.*;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;

/**
 * Menu responsável pela consulta de serviços em aberto na oficina.
 * Define as operações necessárias para listar e visualizar serviços
 * que ainda não foram concluídos.
 *
 * @author Filipe Durães
 */
public class QueryOpenedServicesMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição deste menu.
     *
     * @return nome do menu para exibição ao usuário
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Servicos Abertos";
    }

    /**
     * Exibe o menu de consulta de serviços em aberto.
     * Configura o tipo de consulta para serviços abertos e redireciona para o menu de consulta geral.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        serviceOrderViewModel.setQueryType(ServiceQueryType.OPENED);
        System.out.println("Servicos abertos selecionados");

        return MenuResult.replace(new QueryServicesMenu());
    }
}
