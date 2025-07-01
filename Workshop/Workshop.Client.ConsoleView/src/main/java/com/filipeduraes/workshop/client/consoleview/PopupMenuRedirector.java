// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

/**
 * Controlador que gerencia o redirecionamento para menus popup.
 * Permite navegar para um menu específico e retornar ao menu anterior,
 * controlando o estado de redirecionamento para evitar loops infinitos.
 *
 * @author Filipe Durães
 */
public class PopupMenuRedirector
{
    private final IWorkshopMenu menuToRedirect;

    private boolean hasRedirected = false;

    /**
     * Cria um novo redirecionador para o menu especificado.
     *
     * @param menuToRedirect menu para o qual será redirecionado
     */
    public PopupMenuRedirector(IWorkshopMenu menuToRedirect)
    {
        this.menuToRedirect = menuToRedirect;
    }

    /**
     * Executa o redirecionamento para o menu configurado.
     * Na primeira chamada, redireciona para o menu especificado.
     * Na segunda chamada, retorna ao menu anterior.
     *
     * @return resultado da operação de redirecionamento
     */
    public MenuResult redirect()
    {
        if(!hasRedirected)
        {
            System.out.printf("Redirecionando para o menu: %s%n", menuToRedirect.getMenuDisplayName());
            hasRedirected = true;
            return MenuResult.push(menuToRedirect);
        }
        else
        {
            System.out.println("Nenhum dado selecionado. Voltando...");
            hasRedirected = false;
            return MenuResult.pop();
        }
    }

    /**
     * Reseta o estado de redirecionamento, permitindo um novo ciclo
     * de redirecionamento para o menu especificado.
     */
    public void reset()
    {
        hasRedirected = false;
    }
}
