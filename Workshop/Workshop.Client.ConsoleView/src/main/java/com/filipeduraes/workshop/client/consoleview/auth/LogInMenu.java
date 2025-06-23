// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.MainMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;

/**
 * Implementa o menu de login do sistema da oficina.
 * Gerencia a interface de entrada de credenciais do usuário para autenticação.
 *
 * @author Filipe Durães
 */
public class LogInMenu implements IWorkshopMenu
{
    /**
     * Retorna o nome de exibição do menu de login.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Entrar";
    }

    /**
     * Exibe o menu de login e processa a tentativa de autenticação do usuário.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu indicando próxima ação
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        System.out.println(" - Insira o email:");
        String email = ConsoleInput.readLine();

        System.out.println(" - Insira a senha:");
        String password = ConsoleInput.readLine();

        EmployeeViewModel viewModel = menuManager.getViewModelRegistry().getEmployeeViewModel();
        EmployeeDTO employee = new EmployeeDTO(email, password.hashCode());
        viewModel.setSelectedDTO(employee);

        viewModel.OnLoginRequested.broadcast();

        if (!viewModel.getRequestWasSuccessful())
        {
            System.out.println("Usuario não encontrado ou senha invalida, confira os dados e tente novamente.");
            boolean tryAgain = ConsoleInput.readConfirmation("Deseja tentar novamente?");

            if (tryAgain)
            {
                return MenuResult.none();
            }

            return MenuResult.exit();
        }

        System.out.println("\nUsuario logado com sucesso!");
        System.out.println(viewModel.getLoggedUser());

        return MenuResult.replace(new MainMenu());
    }
}