// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core;

/**
 *
 * @author Filipe Durães
 */
public class Employee
{
    private final String name;
    private final String email;
    private final EmployeeRole role;
    private final int hashPassword;
    
    public Employee(String name, String email, EmployeeRole role, int hashPassword)
    {
        this.name = name;
        this.email = email;
        this.role = role;
        this.hashPassword = hashPassword;        
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getEmail()
    {
        return email;
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