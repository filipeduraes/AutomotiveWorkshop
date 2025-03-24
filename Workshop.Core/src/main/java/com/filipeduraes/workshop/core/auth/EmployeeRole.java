// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

/**
 *
 * @author Filipe Durães
 */
public enum EmployeeRole
{
    COSTUMER_SERVICE("Atendimento ao Cliente"),
    MECHANIC("Mecanico"),
    SPECIALIST_MECHANIC("Mecanico Especialista"),
    ADMINISTRATOR("Administrador");
    
    String displayName;
    
    EmployeeRole(String displayName)
    {
        this.displayName = displayName;
    }
    
    public String getDisplayName()
    {
        return displayName;
    }
}
