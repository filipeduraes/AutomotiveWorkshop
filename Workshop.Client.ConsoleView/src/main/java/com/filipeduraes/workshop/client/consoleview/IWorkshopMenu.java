// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

/**
 * Base for all menus in the client.
 * @author Filipe Durães
 */
public interface IWorkshopMenu 
{
    public String getMenuDisplayName();
    public boolean showMenu(MenuManager menuManager);
}
