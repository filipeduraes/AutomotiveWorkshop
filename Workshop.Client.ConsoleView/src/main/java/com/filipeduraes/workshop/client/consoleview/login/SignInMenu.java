// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class SignInMenu implements IWorkshopMenu 
{
    @Override
    public String getMenuDisplayName() 
    {
        return "Cadastrar";
    }
    
    @Override
    public boolean showMenu(MenuManager menuManager)
    {
        UserInfoViewModel viewModel = menuManager.getUserInfoViewModel();
        
        System.out.println(" - Insira o nome completo: ");
        String userName = ConsoleInput.readLine();
        
        System.out.println("");
        ArrayList<String> possibleRoles = viewModel.getPossibleRoles();
        
        for(int i = 0; i < possibleRoles.size(); i++)
        {
            String option = String.format(" [%d]: %s", i, possibleRoles.get(i));
            System.out.println(option);
        }
        
        System.out.println(" - Insira o seu cargo: ");
        int selectedRole = Integer.parseInt(ConsoleInput.readLine());
        
        System.out.println("\n - Insira o email: ");
        String email = ConsoleInput.readLine();
        
        System.out.println("\n - Insira a senha: ");
        String password = ConsoleInput.readLine();
        
        viewModel.setName(userName);
        viewModel.setEmail(email);
        viewModel.setSelectedRole(selectedRole);
        viewModel.setPasswordHash(password.hashCode());
        viewModel.setLoginState(LoginState.SIGNIN_REQUESTED);
        
        return true;
    }    
}
