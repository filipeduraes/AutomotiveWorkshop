// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Employee extends Entity
{
    private final EmployeeRole role;
    private final int hashPassword;
    
    public Employee(UUID id, Employee copy)
    {
        super(id, copy.getName(), copy.getEmail());
        
        this.role = copy.getRole();
        this.hashPassword = copy.hashPassword;        
    }
    
    
    public Employee(String name, String email, EmployeeRole role, int hashPassword)
    {
        super(name, email);
        
        this.role = role;
        this.hashPassword = hashPassword;        
    }
    
    public Employee(UUID id, String name, String email, EmployeeRole role, int hashPassword)
    {
        super(id, name, email);
        
        this.role = role;
        this.hashPassword = hashPassword;        
    }
    
    public EmployeeRole getRole()
    {
        return role;
    }
    
    public boolean isPasswordValid(int comparedHashPassword)
    {
        return comparedHashPassword == hashPassword;
    }
}