// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client;

import com.filipeduraes.workshop.client.views.IWorkshopMenu;
import com.filipeduraes.workshop.client.views.EnterUserMenu;

/**
 * 
 * @author Filipe Durães
 */
public class WorkshopClient 
{
    private static IWorkshopMenu currentMenu = new EnterUserMenu();
    
    public static void main(String[] args)
    {
        currentMenu.ShowMenu();
    }
}