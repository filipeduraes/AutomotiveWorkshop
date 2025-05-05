// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import java.util.UUID;

/**
 * Representa um funcionário com ID único, nome, email e cargo.
 * Fornece métodos para retornar os detalhes do funcionário.
 *
 * @author Filipe Durães
 */
public class Employee
{
    private UUID id;
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
        return String.format("Employee[id=%s, name='%s', email='%s', role=%s]", id, name, email, role);
    }

    /**
     * Obtém o ID único do funcionário.
     *
     * @return o ID do funcionário
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Define o ID único do funcionário.
     *
     * @param id o ID único a ser atribuído ao funcionário
     */
    public void setID(UUID id)
    {
        this.id = id;
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