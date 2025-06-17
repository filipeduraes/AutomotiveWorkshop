// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar e armazenar informações do usuário logado,
 * incluindo estado de login, dados pessoais e papéis disponíveis no sistema.
 *
 * @author Filipe Durães
 */
public class AuthViewModel
{
    public final Observer OnLoginRequested = new Observer();
    public final Observer OnSignInRequested = new Observer();

    private boolean requestWasSuccessful = false;
    private String name;
    private String email;
    private EmployeeRoleDTO selectedRole;
    private int passwordHash;
    private int accessLevel;

    public boolean getRequestWasSuccessful()
    {
        return requestWasSuccessful;
    }

    public void setRequestWasSuccessful(boolean requestWasSuccessful)
    {
        this.requestWasSuccessful = requestWasSuccessful;
    }

    /**
     * Obtém o nome do usuário
     *
     * @return nome do usuário
     */
    public String getName()
    {
        return name;
    }

    /**
     * Define o nome do usuário
     *
     * @param newName novo nome
     */
    public void setName(String newName)
    {
        name = newName;
    }


    /**
     * Obtém o email do usuário
     *
     * @return email do usuário
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Define o email do usuário
     *
     * @param newEmail novo email
     */
    public void setEmail(String newEmail)
    {
        email = newEmail;
    }


    /**
     * Obtém o hash da senha do usuário
     *
     * @return hash da senha
     */
    public int getPasswordHash()
    {
        return passwordHash;
    }

    /**
     * Define o hash da senha do usuário
     *
     * @param newPasswordHash novo hash de senha
     */
    public void setPasswordHash(int newPasswordHash)
    {
        passwordHash = newPasswordHash;
    }


    /**
     * Obtém o nível de acesso do usuário
     *
     * @return nível de acesso
     */
    public int getAccessLevel()
    {
        return accessLevel;
    }

    /**
     * Define o nível de acesso do usuário
     *
     * @param newAccessLevel novo nível de acesso
     */
    public void setAccessLevel(int newAccessLevel)
    {
        accessLevel = newAccessLevel;
    }

    /**
     * Obtém o índice do papel selecionado
     *
     * @return índice do papel atual
     */
    public EmployeeRoleDTO getSelectedRole()
    {
        return selectedRole;
    }

    /**
     * Define o índice do papel selecionado
     *
     * @param newSelectedRole novo índice de papel
     */
    public void setSelectedRole(EmployeeRoleDTO newSelectedRole)
    {
        selectedRole = newSelectedRole;
    }
}