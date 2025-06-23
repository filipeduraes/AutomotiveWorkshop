// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

/**
 * Menu de redirecionamento que agrupa e gerencia submenus do sistema.
 * Permite a navegação entre diferentes opções de menu apresentadas ao usuário.
 *
 * @author Filipe Durães
 */
public class RedirectMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] submenus;
    private final String displayMessage;

    /**
     * Constrói um novo menu de redirecionamento com mensagem e submenus específicos.
     *
     * @param displayMessage mensagem exibida para este menu
     * @param submenus array de submenus disponíveis para seleção
     */
    public RedirectMenu(String displayMessage, IWorkshopMenu[] submenus)
    {
        this.displayMessage = displayMessage;
        this.submenus = submenus;
    }

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return displayMessage;
    }

    /**
     * Exibe as opções de submenu disponíveis e processa a seleção do usuário.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu indicando próxima ação
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual menu deseja acessar?", submenus);

        if (selectedOption != null)
        {
            return MenuResult.push(selectedOption);
        }

        return MenuResult.pop();
    }
}
