// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.administrator;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MainMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;

/**
 * Menu de cadastro de administrador.
 * Permite ao usuário cadastrar um novo administrador no sistema, coletando nome, email e senha.
 * Após o cadastro bem-sucedido, o usuário é automaticamente logado e redirecionado para o menu principal.
 *
 * Este menu é utilizado para configuração inicial do sistema e criação de contas administrativas.
 *
 * @author Filipe Durães
 */
public class RegisterAdministrator implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Cadastrar Administrador";
    }

    /**
     * Exibe o menu de cadastro de administrador e processa as entradas do usuário.
     * Solicita nome, email e senha, e tenta registrar um novo administrador no sistema.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu, indicando sucesso ou falha no cadastro
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        String name = ConsoleInput.readLine("Insira o seu nome completo:");
        String email = ConsoleInput.readValidatedLine("Insira o seu email:", new EmailInputValidator());
        String password = ConsoleInput.readLine("Crie uma senha:");

        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        EmployeeViewModel employeeViewModel = viewModelRegistry.getEmployeeViewModel();

        EmployeeDTO administrator = new EmployeeDTO(name, email, EmployeeRoleDTO.ADMINISTRATOR, password.hashCode());
        employeeViewModel.setSelectedDTO(administrator);
        employeeViewModel.OnRegisterRequest.broadcast();

        if (employeeViewModel.getRequestWasSuccessful())
        {
            System.out.println("Cadastro criado com sucesso!");

            employeeViewModel.OnLoginRequested.broadcast();

            return MenuResult.replace(new MainMenu(menuManager));
        }

        System.out.println("Nao foi possivel cadastrar o administrador, tente novamente!");
        return MenuResult.none();
    }
}
