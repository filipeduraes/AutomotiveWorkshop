// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
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
        EmployeeRoleDTO selectedRole = viewModel.getSelectedRole();
        EmployeeRole role = EmployeeRole.COSTUMER_SERVICE;

        switch (selectedRole)
        {
            case MECHANIC -> role = EmployeeRole.MECHANIC;
            case SPECIALIST_MECHANIC -> role = EmployeeRole.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> role = EmployeeRole.ADMINISTRATOR;
        }

        LocalEmployee newUser = new LocalEmployee(viewModel.getName(), viewModel.getEmail(), role, viewModel.getPasswordHash());

        authModule.registerUser(newUser);
    }
}