// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Employee
{
    private UUID id;
    private String name;
    private String email;
    private final EmployeeRole role;
    
    public Employee(String name, String email, EmployeeRole role)
    {
        this.name = name;
        this.email = email;
        this.role = role;
    }
    
    public UUID getID()
    {
        return id;
    }
    
    public void setID(UUID id)
    {
        this.id = id;
    }
    
    public EmployeeRole getRole()
    {
        return role;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getEmail()
    {
        return email;
    }
}