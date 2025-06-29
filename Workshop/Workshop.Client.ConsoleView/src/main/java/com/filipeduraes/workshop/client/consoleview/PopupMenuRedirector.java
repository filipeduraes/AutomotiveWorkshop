// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

public class PopupMenuRedirector
{
    private final IWorkshopMenu menuToRedirect;

    private boolean hasRedirected = false;

    public PopupMenuRedirector(IWorkshopMenu menuToRedirect)
    {
        this.menuToRedirect = menuToRedirect;
    }

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

    public void reset()
    {
        hasRedirected = false;
    }
}
