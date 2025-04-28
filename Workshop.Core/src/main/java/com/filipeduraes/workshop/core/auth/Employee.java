// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import java.util.UUID;

/**
 * Represents an employee with a unique ID, name, email, and role.
 * Provides accessors to retrieve the employee's details.
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
     * Creates a new Employee instance with the specified name, email, and role.
     * 
     * @param name the name of the employee
     * @param email the email address of the employee
     * @param role the role assigned to the employee
     */
    public Employee(String name, String email, EmployeeRole role)
    {
        this.name = name;
        this.email = email;
        this.role = role;
    }
    
    /**
     * Gets the unique ID of the employee.
     * 
     * @return the ID of the employee
     */
    public UUID getID()
    {
        return id;
    }
    
    /**
     * Sets the unique ID of the employee.
     * 
     * @param id the unique ID to assign to the employee
     */
    public void setID(UUID id)
    {
        this.id = id;
    }
    
    /**
     * Gets the role of the employee.
     * 
     * @return the role of the employee
     */
    public EmployeeRole getRole()
    {
        return role;
    }
    
    /**
     * Gets the name of the employee.
     * 
     * @return the name of the employee
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the email address of the employee.
     * 
     * @return the email address of the employee
     */
    public String getEmail()
    {
        return email;
    }
}