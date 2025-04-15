// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.client;

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
    private String email;
    private String phoneNumber;
    private String address;
    private String maskedCPF;
    
    private ArrayList<UUID> ownedVehiclesIDs = new ArrayList<>();
    
    public Client(String name, String email, String phoneNumber, String address, String maskedCPF)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.maskedCPF = maskedCPF;
    }
    
    public void setID(UUID id)
    {
        this.id = id;
    }
    
    public UUID getID()
    {
        return id;
    }
    
    public boolean hasMatchingName(String pattern)
    {
        return name.toLowerCase().contains(pattern.toLowerCase());
    }

    public String getName() 
    {
        return name;
    }

    public String getEmail() 
    {
        return email;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public String getAddress() 
    {
        return address;
    }

    public String getMaskedCPF() 
    {
        return maskedCPF;
    }
}
