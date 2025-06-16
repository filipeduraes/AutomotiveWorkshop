// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.model.mappers.EmployeeRoleMapper;
import com.filipeduraes.workshop.client.viewmodel.AuthViewModel;
import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.auth.EmployeeRole;
import com.filipeduraes.workshop.core.auth.LocalEmployee;

/**
 * Controlador responsável por gerenciar a autenticação e registro de usuários no sistema.
 *
 * @author Filipe Durães
 */
public class AuthController
{
    private final AuthViewModel viewModel;
    private final AuthModule authModule;

    /**
     * Inicializa o controlador de autenticação.
     *
     * @param viewModel  ViewModel com dados do usuário
     * @param authModule Módulo de autenticação
     */
    public AuthController(AuthViewModel viewModel, AuthModule authModule)
    {
        this.viewModel = viewModel;

        this.viewModel.OnLoginRequested.addListener(this::processLoginRequest);
        this.viewModel.OnSignInRequested.addListener(this::processSignInRequest);
        this.authModule = authModule;
    }

    /**
     * Remove os listeners e libera recursos.
     */
    public void dispose()
    {
        this.viewModel.OnLoginRequested.removeListener(this::processLoginRequest);
        this.viewModel.OnSignInRequested.removeListener(this::processSignInRequest);
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
        }

        viewModel.setRequestWasSuccessful(loginWasSuccessful);
    }

    private void processSignInRequest()
    {
        EmployeeRoleDTO selectedRole = viewModel.getSelectedRole();
        EmployeeRole role = EmployeeRoleMapper.fromDTO(selectedRole);

        LocalEmployee newUser = new LocalEmployee(viewModel.getName(), viewModel.getEmail(), role, viewModel.getPasswordHash());

        authModule.registerUser(newUser);
    }
}