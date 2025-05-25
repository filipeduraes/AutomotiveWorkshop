// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MainMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;

/**
 * Menu de entrada do usuário que gerencia as opções de login do sistema.
 * Fornece opções para login de usuários existentes e registro de novos usuários.
 * Coordena a navegação entre os submenus de login e registro, e redireciona para
 * o menu principal após autenticação bem-sucedida.
 *
 * @author Filipe Durães
 */
public class EnterUserMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] menus = 
    {
        new LogInMenu(),
        new SignInMenu()
    };
    
    @Override
    public String getMenuDisplayName() 
    {
        return "Opcoes de Login";
    }
    
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        UserInfoViewModel viewModel = menuManager.getUserInfoViewModel();
        
        if(viewModel.getLoginState() == LoginState.LOGIN_SUCCESS)
        {
            System.out.println(String.format("Login realizado com sucesso como usuario: %s\nCargo: %s", viewModel.getName(), viewModel.getSelectedRoleName()));
            return MenuResult.replace(new MainMenu());
        }
        
        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual a opcao de login?", menus);

        if(selectedOption != null)
        {
            return MenuResult.push(selectedOption);
        }
        
        return MenuResult.exit();
    }
}