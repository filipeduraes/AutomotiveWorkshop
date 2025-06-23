// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar e armazenar informações do usuário logado,
 * incluindo estado de login, dados pessoais e papéis disponíveis no sistema.
 *
 * @author Filipe Durães
 */
public class EmployeeViewModel extends EntityViewModel<EmployeeDTO>
{
    public final Observer OnLoginRequested = new Observer();
    public final Observer OnRegisterUserRequested = new Observer();

    private EmployeeDTO loggedUser;
    private boolean requestWasSuccessful = false;

    public boolean getRequestWasSuccessful()
    {
        return requestWasSuccessful;
    }

    public void setRequestWasSuccessful(boolean requestWasSuccessful)
    {
        this.requestWasSuccessful = requestWasSuccessful;
    }

    public EmployeeDTO getLoggedUser()
    {
        return loggedUser;
    }

    public void setLoggedUser(EmployeeDTO loggedUser)
    {
        this.loggedUser = loggedUser;
    }
}