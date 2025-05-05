// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;

/**
 * Implementa o menu de login do sistema da oficina.
 * Gerencia a interface de entrada de credenciais do usuário,
 * coletando email e senha para autenticação.
 *
 * @author Filipe Durães
 */
public class LogInMenu implements IWorkshopMenu
{
    private String[] loginFailOptions = new String[]
    {
        "Tentar Novamente",
        "X Voltar"
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Entrar";
    }
    
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        System.out.println(" - Insira o email: ");
        String email = ConsoleInput.readLine();
        
        System.out.println(" - Insira a senha: ");
        String password = ConsoleInput.readLine();
       
        UserInfoViewModel viewModel = menuManager.getUserInfoViewModel();
        viewModel.setEmail(email);
        viewModel.setPasswordHash(password.hashCode());
        viewModel.setLoginState(LoginState.LOGIN_REQUESTED);
        
        if(viewModel.getLoginState() == LoginState.LOGIN_FAILED)
        {
            System.out.println("Usuário não encontrado ou senha invalida, confira os dados e tente novamente.");
            int userInput = ConsoleInput.readOptionFromList("Escolha como prosseguir: ", loginFailOptions);

            if(userInput == 0)
            {
                return MenuResult.none();
            }
        }

        return MenuResult.pop();
    }
}
