// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.auth;

/**
 *
 * @author Filipe Durães
 */
public class LocalEmployee extends Employee
{
    private final int hashPassword;
    
    public LocalEmployee(String name, String email, EmployeeRole role, int hashPassword) 
    {
        super(name, email, role);
        this.hashPassword = hashPassword;
    }

    public boolean isPasswordValid(int comparedHashPassword)
    {
        return comparedHashPassword == hashPassword;
    }
}
