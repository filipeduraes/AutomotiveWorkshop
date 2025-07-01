// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.employee;

/**
 * Representa os diferentes cargos que um funcionário pode ter na oficina automotiva.
 * Cada cargo possui um nome de exibição associado.
 *
 * @author Filipe Durães
 */
public enum EmployeeRole
{
    COSTUMER_SERVICE("Atendimento ao Cliente"),
    MECHANIC("Mecanico"),
    SPECIALIST_MECHANIC("Mecanico Especialista"),
    ADMINISTRATOR("Administrador");

    /**
     * O nome de exibição do cargo
     */
    private final String displayName;

    /**
     * Constrói um EmployeeRole com o nome de exibição especificado.
     *
     * @param displayName O nome de exibição do cargo
     */
    EmployeeRole(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
