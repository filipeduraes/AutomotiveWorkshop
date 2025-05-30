// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.auth.EmployeeRole;
import com.filipeduraes.workshop.core.auth.LocalEmployee;
import java.util.ArrayList;

/**
 * Controlador responsável por gerenciar a autenticação e registro de usuários no sistema.
 *
 * @author Filipe Durães
 */
public class LoginController
{
    private final UserInfoViewModel viewModel;
    private final AuthModule authModule;

    /**
     * Inicializa o controlador de autenticação.
     *
     * @param viewModel  ViewModel com dados do usuário
     * @param authModule Módulo de autenticação
     */
    public LoginController(UserInfoViewModel viewModel, AuthModule authModule)
    {
        this.viewModel = viewModel;

        this.viewModel.OnLoginStateChanged.addListener(this::updateLoginState);
        this.authModule = authModule;

        EmployeeRole[] employeeRoles = EmployeeRole.values();
        ArrayList<String> possibleRoles = new ArrayList<>();

        for (EmployeeRole role : employeeRoles)
        {
            possibleRoles.add(role.getDisplayName());
        }

        this.viewModel.setPossibleRoles(possibleRoles);
    }

    /**
     * Remove os listeners e libera recursos.
     */
    public void dispose()
    {
        viewModel.OnLoginStateChanged.removeListener(this::updateLoginState);
    }

    private void updateLoginState()
    {
        LoginState loginState = viewModel.getLoginState();

        switch (loginState)
        {
            case LOGIN_REQUESTED:
            {
                processLoginRequest();
                break;
            }
            case SIGNIN_REQUESTED:
            {
                processSignInRequest();
                break;
            }
        }
    }

    private void processLoginRequest()
    {
        boolean loginWasSuccessful = authModule.tryLogIn(viewModel.getEmail(), viewModel.getPasswordHash());

        if (loginWasSuccessful)
        {
            viewModel.setName(authModule.getLoggedUser().getName());
            viewModel.setLoginState(LoginState.LOGIN_SUCCESS);
        }
        else
        {
            viewModel.setLoginState(LoginState.LOGIN_FAILED);
        }
    }

    private void processSignInRequest()
    {
        int selectedRole = viewModel.getSelectedRole();
        EmployeeRole role = EmployeeRole.values()[selectedRole];
        LocalEmployee newUser = new LocalEmployee(viewModel.getName(), viewModel.getEmail(), role, viewModel.getPasswordHash());

        authModule.registerUser(newUser);
    }
}