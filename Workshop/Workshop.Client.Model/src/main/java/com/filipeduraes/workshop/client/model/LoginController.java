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
            EmployeeRole role = authModule.getLoggedUser().getRole();
            String userName = authModule.getLoggedUser().getName();

            viewModel.setName(userName);
            viewModel.setSelectedRole(convertRoleToDTO(role));
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
        EmployeeRole role = convertDTOToRole(selectedRole);

        LocalEmployee newUser = new LocalEmployee(viewModel.getName(), viewModel.getEmail(), role, viewModel.getPasswordHash());

        authModule.registerUser(newUser);
    }

    private static EmployeeRole convertDTOToRole(EmployeeRoleDTO selectedRole)
    {
        return switch (selectedRole)
        {
            case COSTUMER_SERVICE -> EmployeeRole.COSTUMER_SERVICE;
            case MECHANIC -> EmployeeRole.MECHANIC;
            case SPECIALIST_MECHANIC -> EmployeeRole.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> EmployeeRole.ADMINISTRATOR;
        };
    }

    private static EmployeeRoleDTO convertRoleToDTO(EmployeeRole employeeRole)
    {
        return switch (employeeRole)
        {
            case COSTUMER_SERVICE -> EmployeeRoleDTO.COSTUMER_SERVICE;
            case MECHANIC -> EmployeeRoleDTO.MECHANIC;
            case SPECIALIST_MECHANIC -> EmployeeRoleDTO.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> EmployeeRoleDTO.ADMINISTRATOR;
        };
    }
}