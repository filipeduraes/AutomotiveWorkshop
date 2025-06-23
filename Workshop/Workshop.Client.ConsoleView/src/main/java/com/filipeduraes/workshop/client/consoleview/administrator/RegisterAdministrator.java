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

public class RegisterAdministrator implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Cadastrar Administrador";
    }

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
        employeeViewModel.OnRegisterUserRequested.broadcast();

        if(employeeViewModel.getRequestWasSuccessful())
        {
            System.out.println("Cadastro criado com sucesso!");

            employeeViewModel.OnLoginRequested.broadcast();

            return MenuResult.replace(new MainMenu());
        }

        System.out.println("Nao foi possivel cadastrar o administrador, tente novamente!");
        return MenuResult.none();
    }
}
