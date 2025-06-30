// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.employee;

import com.filipeduraes.workshop.core.WorkshopEntity;

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
    private EmployeeRole role;
    private ClockInType lastClockIn = ClockInType.OUT;

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
     * Cria uma instância de Employee, copiando outro colaborador.
     * Construtor de cópia.
     *
     * @param employee colaborador a ser copiado
     */
    public Employee(Employee employee)
    {
        super(employee);

        name = employee.getName();
        email = employee.getEmail();
        role = employee.getRole();
        lastClockIn = employee.getLastClockIn();
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
     * Define o cargo do funcionário.
     *
     * @param role o cargo a ser atribuído ao funcionário
     */
    public void setRole(EmployeeRole role)
    {
        this.role = role;
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
     * Define o nome do funcionário.
     *
     * @param name o nome a ser atribuído ao funcionário
     */
    public void setName(String name)
    {
        this.name = name;
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

    /**
     * Define o endereço de email do funcionário.
     *
     * @param email o novo endereço de email do funcionário
     */
    public void setEmail(String email)
    {
        this.email = email;
    }


    /**
     * Retorna o último ponto batido pelo colaborador.
     *
     * @return último ponto batido
     */
    public ClockInType getLastClockIn()
    {
        if(lastClockIn == null)
        {
            lastClockIn = ClockInType.OUT;
        }

        return lastClockIn;
    }

    /**
     * Atribui o último ponto batido pelo colaborador.
     *
     * @param lastClockIn último ponto batido
     */
    public void setLastClockIn(ClockInType lastClockIn)
    {
        this.lastClockIn = lastClockIn;
    }
}