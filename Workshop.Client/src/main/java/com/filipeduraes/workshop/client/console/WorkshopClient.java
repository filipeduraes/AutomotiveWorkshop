// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.console;

import com.filipeduraes.workshop.client.model.LoginController;
import com.filipeduraes.workshop.client.consoleview.login.EnterUserMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import com.filipeduraes.workshop.core.Workshop;

/**
 * 
 * @author Filipe Durães
 */
public class WorkshopClient 
{
    private static MenuManager menuManager;
    
    public static void main(String[] args)
    {
        Workshop workshop = new Workshop();
        
        UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
        LoginController controller = new LoginController(userInfoViewModel, workshop.getAuthModule());
        menuManager = new MenuManager(userInfoViewModel);
        
        menuManager.pushMenu(new EnterUserMenu());
        menuManager.run();
    }
}