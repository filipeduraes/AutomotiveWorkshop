// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.client;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

/**
 * Menu de seleção de clientes.
 * Gerencia as operações de escolha de cliente, fornecendo acesso aos submenus de registro
 * e pesquisa. Se um cliente já estiver selecionado, exibe suas informações e retorna ao menu anterior.
 *
 * Este menu facilita o processo de identificação e seleção de clientes para operações futuras.
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