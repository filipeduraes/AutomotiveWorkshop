// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core;

/**
 *
 * @author Filipe Durães
 */
public class Entity
{
    private String id = "";
    private String name = "";
    private String email = "";
    
    public Entity(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
    
    public Entity(String id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public String getID()
    {
        return id;
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
