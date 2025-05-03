// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.console;

import com.filipeduraes.workshop.client.model.LoginController;
import com.filipeduraes.workshop.client.model.ClientController;

import com.filipeduraes.workshop.client.consoleview.login.EnterUserMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
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
        String title = " ___________ _____ _____ _____ _   _   ___  \n"
                     + "|  _  |  ___|_   _/  __ \\_   _| \\ | | / _ \\ \n"
                     + "| | | | |_    | | | /  \\/ | | |  \\| |/ /_\\ \\\n"
                     + "| | | |  _|   | | | |     | | | . ` ||  _  |\n"
                     + "\\ \\_/ / |    _| |_| \\__/\\_| |_| |\\  || | | |\n"
                     + " \\___/\\_|    \\___/ \\____/\\___/\\_| \\_/\\_| |_/";

        System.out.println(title);
        Workshop workshop = new Workshop();
        
        UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
        ClientViewModel clientViewModel = new ClientViewModel();
        
        LoginController loginController = new LoginController(userInfoViewModel, workshop.getAuthModule());
        ClientController clientController = new ClientController(clientViewModel, workshop.getClientModule());
        
        menuManager = new MenuManager(userInfoViewModel, clientViewModel);
        menuManager.pushMenu(new EnterUserMenu());
        menuManager.run();
        
        loginController.dispose();
        clientController.dispose();
    }
}