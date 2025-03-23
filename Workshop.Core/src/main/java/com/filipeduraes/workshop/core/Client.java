// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core;

import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class Client extends Entity
{
    private ArrayList<String> ownedVehiclesIDs = new ArrayList<>();
    
    public Client(String id, String name, String email) 
    {
        super(id, name, email);
    }
}
