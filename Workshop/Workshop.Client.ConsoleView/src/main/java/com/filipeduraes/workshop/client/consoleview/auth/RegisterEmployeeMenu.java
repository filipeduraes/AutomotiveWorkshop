// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.utils.TextUtils;

import java.util.Arrays;

/**
 * Menu responsável pelo cadastro de novos usuários no sistema.
 * Coleta informações como nome completo, cargo, email e senha.
 * Gerencia a interface de registro e comunica com o ViewModel para processar o cadastro.
 *
 * @author Filipe Durães
 */
public class RegisterEmployeeMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName() 
    {
        return "Cadastrar";
    }
    
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
        viewModel.OnRegisterUserRequested.broadcast();

        if(!viewModel.getRequestWasSuccessful())
        {
            System.out.println("Nao foi possivel cadastrar o colaborador. Tente novamente!");
            return MenuResult.none();
        }

        System.out.println("Colaborador cadastrado com sucesso!");
        return MenuResult.pop();
    }    
}