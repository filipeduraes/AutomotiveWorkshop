// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

/**
 * Menu principal do módulo de clientes que gerencia as operações relacionadas aos clientes.
 * Fornece acesso aos submenus de registro e pesquisa de clientes.
 *
 * @author Filipe Durães
 */
public class ClientSelectionMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menus = 
    {
        new ClientRegistrationMenu(),
        new ClientSearchMenu()
    };
    
    @Override
    public String getMenuDisplayName() 
    {
        return "Selecionar Cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ClientViewModel clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();

        if(clientViewModel.hasLoadedDTO())
        {
            System.out.printf("Cliente selecionado:%n%s%n%n", clientViewModel.getSelectedDTO());
            return MenuResult.pop();
        }

        IWorkshopMenu submenu = menuManager.showSubmenuOptions("Como deseja selecionar o cliente?", menus);
        
        if(submenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(submenu);
    }
}