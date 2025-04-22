// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;

/**
 *
 * @author Filipe Durães
 */
public class LoginMenu implements IWorkshopMenu 
{
    @Override
    public String getMenuDisplayName() 
    {
        return "Entrar";
    }
    
    @Override
    public boolean showMenu(MenuManager menuManager)
    {
        System.out.println(" - Insira o email: ");
        String email = ConsoleInput.readLine();
        
        System.out.println("\n - Insira a senha: ");
        String password = ConsoleInput.readLine();
       
        UserInfoViewModel viewModel = menuManager.getUserInfoViewModel();
        viewModel.setEmail(email);
        viewModel.setPasswordHash(password.hashCode());
        viewModel.setLoginState(LoginState.LOGIN_REQUESTED);
        
        if(viewModel.getLoginState() == LoginState.LOGIN_FAILED)
        {
            System.out.println("Login falhou, tente novamente.");
            return false;
        }
        
        return true;
    }
}
