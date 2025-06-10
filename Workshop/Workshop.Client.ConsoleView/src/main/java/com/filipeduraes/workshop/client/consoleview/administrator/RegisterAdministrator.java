package com.filipeduraes.workshop.client.consoleview.administrator;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MainMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
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
        UserInfoViewModel userInfoViewModel = viewModelRegistry.getUserInfoViewModel();

        userInfoViewModel.setName(name);
        userInfoViewModel.setEmail(email);
        userInfoViewModel.setPasswordHash(password.hashCode());
        userInfoViewModel.setSelectedRole(EmployeeRoleDTO.ADMINISTRATOR);
        userInfoViewModel.setLoginState(LoginState.SIGNIN_REQUESTED);

        System.out.println("Cadastro criado com sucesso!");

        userInfoViewModel.setLoginState(LoginState.LOGIN_REQUESTED);

        return MenuResult.replace(new MainMenu());
    }
}
