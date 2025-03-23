// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Client extends Entity
{
    private ArrayList<UUID> ownedVehiclesIDs = new ArrayList<>();
    
    public Client(UUID id, String name, String email) 
    {
        super(id, name, email);
    }
}
