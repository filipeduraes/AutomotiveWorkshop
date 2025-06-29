// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.employee;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;

/**
 * Menu responsável pelo cadastro de novos colaboradores no sistema.
 * Coleta e valida informações pessoais e profissionais para registro.
 *
 * @author Filipe Durães
 */
public class RegisterEmployeeMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição deste menu.
     *
     * @return o nome de exibição do menu
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Cadastrar";
    }

    /**
     * Exibe o formulário de cadastro de colaborador e processa o registro.
     *
     * @param menuManager o gerenciador de menus da aplicação
     * @return o resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        EmployeeViewModel viewModel = menuManager.getViewModelRegistry().getEmployeeViewModel();

        System.out.println(" - Insira o nome completo: ");
        String userName = ConsoleInput.readLine();

        System.out.println();

        EmployeeRoleDTO[] employeeRoleDTOS = EmployeeRoleDTO.values();

        int selectedRoleIndex = ConsoleInput.readOptionFromList(" - Insira o cargo do colaborador:", employeeRoleDTOS);

        System.out.println(" - Insira o email: ");
        String email = ConsoleInput.readLine();

        System.out.println(" - Insira a senha: ");
        String password = ConsoleInput.readLine();

        EmployeeDTO newEmployee = new EmployeeDTO(userName, email, employeeRoleDTOS[selectedRoleIndex], password.hashCode());
        viewModel.setSelectedDTO(newEmployee);
        viewModel.OnRegisterRequest.broadcast();

        if (!viewModel.getRequestWasSuccessful())
        {
            System.out.println("Nao foi possivel cadastrar o colaborador. Tente novamente!");
            return MenuResult.none();
        }

        System.out.println("Colaborador cadastrado com sucesso!");
        return MenuResult.pop();
    }
}