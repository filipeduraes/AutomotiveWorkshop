// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel responsável por gerenciar e armazenar informações do usuário logado,
 * incluindo estado de login, dados pessoais e papéis disponíveis no sistema.
 *
 * @author Filipe Durães
 */
public class UserInfoViewModel
{
    /**
     * Observer notificado quando o estado de login do usuário é alterado
     */
    public final Observer OnLoginStateChanged = new Observer();

    private List<String> possibleRoles = new ArrayList<>();
    private LoginRequest loginRequest = LoginRequest.WAITING;
    private String name;
    private String email;
    private EmployeeRoleDTO selectedRole;
    private int passwordHash;
    private int accessLevel;

    /**
     * Obtém o estado atual de login do usuário
     *
     * @return estado atual de login
     */
    public LoginRequest getLoginState()
    {
        return loginRequest;
    }

    /**
     * Define um novo estado de login para o usuário
     *
     * @param newLoginRequest novo estado de login
     */
    public void setLoginState(LoginRequest newLoginRequest)
    {
        if (newLoginRequest != loginRequest)
        {
            loginRequest = newLoginRequest;
            OnLoginStateChanged.broadcast();
        }
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