// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

/**
 * Fornece um menu de interação com serviços da oficina.
 * Este menu permite criar ordens de serviço e realizar diferentes tipos de consultas
 * sobre os serviços existentes no sistema.
 *
 * @author Filipe Durães
 */
public class ServicesMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] submenus =
    {
        new CreateServiceOrderMenu(),
        new QueryOpenedServicesMenu(),
        new QueryUserServicesMenu(),
        new QueryGeneralServicesMenu()
    };

    /**
     * Obtém o nome de exibição do menu de serviços.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Servico";
    }

    /**
     * Exibe as opções do menu de serviços e processa a seleção do usuário.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu selectedSubmenu = menuManager.showSubmenuOptions("O que deseja fazer?", submenus);

        if (selectedSubmenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(selectedSubmenu);
    }
}