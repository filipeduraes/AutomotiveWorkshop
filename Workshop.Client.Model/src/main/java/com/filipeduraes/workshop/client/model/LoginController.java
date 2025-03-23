// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.viewmodel.LoginState;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import com.filipeduraes.workshop.core.AuthModule;
import com.filipeduraes.workshop.core.Employee;
import com.filipeduraes.workshop.core.EmployeeRole;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class LoginController
{
    private final Runnable updateLoginStateLambda;
    private final UserInfoViewModel viewModel;
    private final AuthModule authModule;
    
    public LoginController(UserInfoViewModel viewModel, AuthModule authModule)
    {
        updateLoginStateLambda = () -> UpdateLoginState();
        this.viewModel = viewModel;

        this.viewModel.OnLoginStateChanged.add(updateLoginStateLambda);
        this.authModule = authModule;
        
        EmployeeRole[] employeeRoles = EmployeeRole.values();
        ArrayList<String> possibleRoles = new ArrayList<>();
        
        for(EmployeeRole role : employeeRoles)
        {
            possibleRoles.add(role.getDisplayName());
        }
        
        this.viewModel.setPossibleRoles(possibleRoles);
    }
    
    public void Dispose()
    {
        viewModel.OnLoginStateChanged.remove(updateLoginStateLambda);
    }
    
    private void UpdateLoginState()
    {
        LoginState loginState = viewModel.getLoginState();
        
        switch(loginState)
        {
            case LOGIN_REQUESTED :
                boolean loginWasSuccessful = authModule.tryLogIn(viewModel.getEmail(), viewModel.getPasswordHash());
                
                if(loginWasSuccessful)
                {
                    viewModel.setName(authModule.getLoggedUser().getName());
                    viewModel.setLoginState(LoginState.LOGIN_SUCCESS);
                }
                else
                {
                    viewModel.setLoginState(LoginState.LOGIN_FAILED);
                }
                
                break;
            case SIGNIN_REQUESTED:
                int selectedRole = viewModel.getSelectedRole();
                EmployeeRole role = EmployeeRole.values()[selectedRole];
                Employee newUser = new Employee(viewModel.getName(), viewModel.getEmail(), role, viewModel.getPasswordHash());
                
                authModule.registerUser(newUser);
                break;
        }
    }
}