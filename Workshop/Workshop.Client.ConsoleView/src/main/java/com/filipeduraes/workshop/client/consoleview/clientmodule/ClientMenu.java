// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

/**
 * Menu para gerenciamento de operações relacionadas a clientes.
 * Fornece opções para registro e visualização de detalhes dos clientes no sistema.
 *
 * @author Filipe Durães
 */
public class ClientMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] submenus =
            {
                    new ClientRegistrationMenu(),
                    new ClientDetailsMenu(),
            };

    /**
     * Obtém o nome de exibição deste menu.
     *
     * @return o nome de exibição do menu
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Cliente";
    }

    /**
     * Exibe o menu de clientes e processa a interação do usuário.
     *
     * @param menuManager o gerenciador de menus da aplicação
     * @return o resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ClientViewModel clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();
        clientViewModel.resetSelectedDTO();

        IWorkshopMenu selectedMenu = menuManager.showSubmenuOptions("O que deseja fazer?", submenus);

        if (selectedMenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(selectedMenu);
    }
}
