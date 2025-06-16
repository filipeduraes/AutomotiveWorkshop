// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.login;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.AuthViewModel;

/**
 * Menu responsável pelo cadastro de novos usuários no sistema.
 * Coleta informações como nome completo, cargo, email e senha.
 * Gerencia a interface de registro e comunica com o ViewModel para processar o cadastro.
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
    public MenuResult showMenu(MenuManager menuManager)
    {
        AuthViewModel viewModel = menuManager.getViewModelRegistry().getUserInfoViewModel();
        
        System.out.println(" - Insira o nome completo: ");
        String userName = ConsoleInput.readLine();
        
        System.out.println();

        EmployeeRoleDTO[] employeeRoleDTOS = EmployeeRoleDTO.values();
        String[] options = new String[employeeRoleDTOS.length];

        for(int i = 0; i < employeeRoleDTOS.length; i++)
        {
            String roleDisplayName = employeeRoleDTOS[i].toString();
            String option = String.format(" [%d]: %s", i, roleDisplayName);
            System.out.println(option);

            options[i] = roleDisplayName;
        }

        int selectedRoleIndex = ConsoleInput.readOptionFromList(" - Insira o cargo do colaborador:", options);

        System.out.println(" - Insira o email: ");
        String email = ConsoleInput.readLine();
        
        System.out.println(" - Insira a senha: ");
        String password = ConsoleInput.readLine();
        
        viewModel.setName(userName);
        viewModel.setEmail(email);
        viewModel.setSelectedRole(employeeRoleDTOS[selectedRoleIndex]);
        viewModel.setPasswordHash(password.hashCode());
        viewModel.OnSignInRequested.broadcast();

        return MenuResult.pop();
    }    
}