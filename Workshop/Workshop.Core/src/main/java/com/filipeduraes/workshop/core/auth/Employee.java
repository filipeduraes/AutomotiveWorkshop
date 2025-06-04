// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import com.filipeduraes.workshop.core.WorkshopEntity;

import java.util.UUID;

/**
 * Representa um funcionário com ID único, nome, email e cargo.
 * Fornece métodos para retornar os detalhes do funcionário.
 *
 * @author Filipe Durães
 */
public class Employee extends WorkshopEntity
{
    private String name;
    private String email;
    private final EmployeeRole role;

    /**
     * Cria uma instância de Employee com o nome, email e cargo especificados.
     *
     * @param name  o nome do funcionário
     * @param email o endereço de email do funcionário
     * @param role  o cargo atribuído ao funcionário
     */
    public Employee(String name, String email, EmployeeRole role)
    {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Retorna uma representação em string do funcionário.
     *
     * @return uma string contendo o ID, nome, email e cargo do funcionário
     */
    @Override
    public String toString()
    {
        return String.format("Employee[id=%s, name='%s', email='%s', role=%s]", getID(), name, email, role);
    }

    /**
     * Obtém o cargo do funcionário.
     *
     * @return o cargo do funcionário
     */
    public EmployeeRole getRole()
    {
        return role;
    }

    /**
     * Obtém o nome do funcionário.
     *
     * @return o nome do funcionário
     */
    public String getName()
    {
        return name;
    }

    /**
     * Obtém o endereço de email do funcionário.
     *
     * @return o endereço de email do funcionário
     */
    public String getEmail()
    {
        return email;
    }
}