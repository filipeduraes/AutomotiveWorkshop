// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

/**
 * Base para todos os menus do cliente.
 * @author Filipe Durães
 */
public interface IWorkshopMenu 
{
    /**
     * Retorna o nome de exibição do menu.
     *
     * @return nome do menu
     */
    String getMenuDisplayName();

    /**
     * Exibe o menu e retorna a operação de manipulação do menu.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    MenuResult showMenu(MenuManager menuManager);
}