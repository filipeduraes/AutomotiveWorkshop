// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.client;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

/**
 * Menu de seleção de clientes que gerencia as operações de escolha de cliente.
 * Fornece acesso aos submenus de registro e pesquisa para seleção de clientes.
 *
 * @author Filipe Durães
 */
public class ClientSelectionMenu extends RedirectMenu
{
    public ClientSelectionMenu()
    {
        super("Selecionar Cliente", new IWorkshopMenu[]
        {
            new ClientRegistrationMenu(),
            new ClientSearchMenu()
        });
    }

    /**
     * Apresenta o menu de seleção de cliente e processa a escolha realizada.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação realizada no menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        EntityViewModel<ClientDTO> clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();

        if (clientViewModel.hasLoadedDTO())
        {
            System.out.printf("Cliente selecionado:%n%s%n%n", clientViewModel.getSelectedDTO());
            return MenuResult.pop();
        }

        return super.showMenu(menuManager);
    }
}