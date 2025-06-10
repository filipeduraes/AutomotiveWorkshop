// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.model.mappers.EmployeeRoleMapper;
import com.filipeduraes.workshop.client.viewmodel.LoginRequest;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.auth.EmployeeRole;
import com.filipeduraes.workshop.core.auth.LocalEmployee;

import java.util.Map;

/**
 * Controlador responsável por gerenciar a autenticação e registro de usuários no sistema.
 *
 * @author Filipe Durães
 */
public class AuthController
{
    private final Map<LoginRequest, Runnable> handlers = Map.of
    (
        LoginRequest.LOGIN_REQUESTED, this::processLoginRequest,
        LoginRequest.SIGNIN_REQUESTED, this::processSignInRequest
    );

    private final UserInfoViewModel viewModel;
    private final AuthModule authModule;

    /**
     * Inicializa o controlador de autenticação.
     *
     * @param viewModel  ViewModel com dados do usuário
     * @param authModule Módulo de autenticação
     */
    public AuthController(UserInfoViewModel viewModel, AuthModule authModule)
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
        LoginRequest loginRequest = viewModel.getLoginState();

        if(handlers.containsKey(loginRequest))
        {
            Runnable handler = handlers.get(loginRequest);
            handler.run();
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
            viewModel.setSelectedRole(EmployeeRoleMapper.toDTO(role));
            viewModel.setLoginState(LoginRequest.LOGIN_SUCCESS);
        }
        else
        {
            viewModel.setLoginState(LoginRequest.LOGIN_FAILED);
        }
    }

    private void processSignInRequest()
    {
        EmployeeRoleDTO selectedRole = viewModel.getSelectedRole();
        EmployeeRole role = EmployeeRoleMapper.fromDTO(selectedRole);

        LocalEmployee newUser = new LocalEmployee(viewModel.getName(), viewModel.getEmail(), role, viewModel.getPasswordHash());

        authModule.registerUser(newUser);
    }
}