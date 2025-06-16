// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.MainMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.AuthViewModel;

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
        System.out.println(" - Insira o email:");
        String email = ConsoleInput.readLine();
        
        System.out.println(" - Insira a senha:");
        String password = ConsoleInput.readLine();
       
        AuthViewModel viewModel = menuManager.getViewModelRegistry().getUserInfoViewModel();
        viewModel.setEmail(email);
        viewModel.setPasswordHash(password.hashCode());
        viewModel.OnLoginRequested.broadcast();
        
        if(!viewModel.getRequestWasSuccessful())
        {
            System.out.println("Usuario não encontrado ou senha invalida, confira os dados e tente novamente.");
            int userInput = ConsoleInput.readOptionFromList("Escolha como prosseguir: ", loginFailOptions);

            if(userInput == 0)
            {
                return MenuResult.none();
            }
        }

        System.out.println("\nUsuario logado com sucesso!");
        System.out.printf(" - Nome: %s%n - Cargo: %s%n - Email: %s%n", viewModel.getName(), viewModel.getSelectedRole(), viewModel.getEmail());

        return MenuResult.replace(new MainMenu());
    }
}
