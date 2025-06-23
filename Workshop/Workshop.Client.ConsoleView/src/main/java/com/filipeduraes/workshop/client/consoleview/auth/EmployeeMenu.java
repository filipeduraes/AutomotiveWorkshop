// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

/**
 * Menu para gerenciamento de operações relacionadas a colaboradores.
 * Fornece acesso aos submenus de registro e detalhes dos colaboradores.
 *
 * @author Filipe Durães
 */
public class EmployeeMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menus =
            {
                    new RegisterEmployeeMenu(),
                    new EmployeeDetailsMenu()
            };

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Colaborador";
    }

    /**
     * Exibe as opções de submenu para operações com colaboradores.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        menuManager.getViewModelRegistry().getEmployeeViewModel().resetSelectedDTO();

        IWorkshopMenu selectedMenu = menuManager.showSubmenuOptions("Qual menu deseja acessar?", menus, true);

        if (selectedMenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(selectedMenu);
    }
}
