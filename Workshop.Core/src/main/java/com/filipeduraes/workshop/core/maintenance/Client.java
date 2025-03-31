// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Client
{
    private UUID id;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String maskedCPF;
    
    private ArrayList<UUID> ownedVehiclesIDs = new ArrayList<>();
    
    public Client(UUID id, String name, String phoneNumber, String address, String email, String maskedCPF)
    {
        this.id = id; 
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.maskedCPF = maskedCPF;
    }
}
