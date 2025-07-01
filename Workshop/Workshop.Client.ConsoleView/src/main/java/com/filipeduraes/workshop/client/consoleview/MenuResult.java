// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

/**
 * Representa o resultado de uma operação de menu, contendo a ação a ser executada e o menu alvo desta ação.
 *
 * @author Filipe Durães
 */
public class MenuResult
{
    private final MenuAction action;
    private final IWorkshopMenu targetMenu;

    /**
     * Cria um novo MenuResult com a ação e menu alvo especificados.
     *
     * @param action A ação a ser executada
     * @param targetMenu O menu alvo da ação
     */
    private MenuResult(MenuAction action, IWorkshopMenu targetMenu)
    {
        this.action = action;
        this.targetMenu = targetMenu;
    }

    /**
     * Obtém a ação a ser executada.
     *
     * @return A ação do menu
     */
    public MenuAction getAction()
    {
        return action;
    }

    /**
     * Obtém o menu alvo da ação.
     *
     * @return O menu alvo
     */
    public IWorkshopMenu getTargetMenu()
    {
        return targetMenu;
    }

    /**
     * Cria um resultado para empilhar um novo menu.
     *
     * @param menu O menu a ser empilhado
     * @return O resultado da operação
     */
    public static MenuResult push(IWorkshopMenu menu)
    {
        return new MenuResult(MenuAction.PUSH_MENU, menu);
    }

    /**
     * Cria um resultado para substituir o menu atual.
     *
     * @param menu O menu que substituirá o atual
     * @return O resultado da operação
     */
    public static MenuResult replace(IWorkshopMenu menu)
    {
        return new MenuResult(MenuAction.REPLACE_MENU, menu);
    }

    /**
     * Cria um resultado para remover o menu atual da pilha.
     *
     * @return O resultado da operação
     */
    public static MenuResult pop()
    {
        return new MenuResult(MenuAction.POP_MENU, null);
    }

    /**
     * Cria um resultado que não executa nenhuma ação.
     *
     * @return O resultado da operação
     */
    public static MenuResult none()
    {
        return new MenuResult(MenuAction.NO_ACTION, null);
    }

    /**
     * Cria um resultado para sair do sistema de menus.
     *
     * @return O resultado da operação
     */
    public static MenuResult exit()
    {
        return new MenuResult(MenuAction.EXIT, null);
    }
}