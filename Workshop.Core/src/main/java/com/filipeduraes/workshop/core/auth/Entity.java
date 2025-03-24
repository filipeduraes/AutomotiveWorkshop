// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Entity
{
    private UUID id;
    private String name = "";
    private String email = "";
    
    public Entity(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
    
    public Entity(UUID id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public UUID getID()
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
