// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MainMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;

/**
 *
 * @author Filipe Durães
 */
public class EnterUserMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] menus = 
    {
        new LoginMenu(),
        new SignInMenu()
    };
    
    @Override
    public String getMenuDisplayName() 
    {
        return "Opcoes de Login";
    }
    
    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        UserInfoViewModel viewModel = menuManager.getUserInfoViewModel();
        
        if(viewModel.getLoginState() == LoginState.LOGIN_SUCCESS)
        {
            System.out.println(String.format("Login realizado com sucesso como usuario: %s\nCargo: %s", viewModel.getName(), viewModel.getSelectedRoleName()));
            menuManager.replaceCurrentMenu(new MainMenu());
            return false;
        }
        
        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual a opcao de login?", menus);

        if(selectedOption != null)
        {
            menuManager.pushMenu(selectedOption);
        }
        
        return false;
    }
}