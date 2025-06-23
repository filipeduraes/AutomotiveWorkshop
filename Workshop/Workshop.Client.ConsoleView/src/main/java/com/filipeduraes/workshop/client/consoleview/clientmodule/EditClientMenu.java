// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

/**
 * Menu para edição de dados do cliente.
 * Permite modificar as informações cadastrais de um cliente existente.
 *
 * @author Filipe Durães
 */
public class EditClientMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return o nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Editar cliente";
    }

    /**
     * Exibe o menu de edição de cliente e processa a interação do usuário.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        return null;
    }
}
