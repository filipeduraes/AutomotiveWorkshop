// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

/**
 * Define as ações possíveis que podem ser executadas durante a navegação entre menus.
 * Essas ações determinam como o fluxo de navegação deve ser controlado.
 *
 * @author Filipe Durães
 */
public enum MenuAction
{
    /**
     * Adiciona um novo menu no topo da pilha de menus, sem remover o menu atual.
     */
    PUSH_MENU,

    /**
     * Substitui o menu atual por um novo menu.
     */
    REPLACE_MENU,

    /**
     * Remove o menu atual do topo da pilha de menus e volta para o menu anterior.
     */
    POP_MENU,

    /**
     * Não executa nenhuma ação de navegação, o menu atual continua sendo exibido.
     */
    NO_ACTION,

    /**
     * Encerra a execução do programa
     */
    EXIT
}
