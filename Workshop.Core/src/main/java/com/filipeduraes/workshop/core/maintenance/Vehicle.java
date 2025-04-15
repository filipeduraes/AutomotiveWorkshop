// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.client.Client;

/**
 *
 * @author Filipe Durães
 */
public class Vehicle
{
    private transient Client owner;
    private String model;
    private String color;
    private String type;
    private String licensePlate;
    private VehicleStatus status = VehicleStatus.RECEIVED;
    
    public Vehicle(Client owner, String model, String color, String type, String licensePlate)
    {
        this.owner = owner;
        this.model = model;
        this.color = color;
        this.type = type;
        this.licensePlate = licensePlate;
    }

    public String getModel() 
    {
        return model;
    }

    public String getColor() 
    {
        return color;
    }

    public String getType() 
    {
        return type;
    }

    public String getLicensePlate() 
    {
        return licensePlate;
    }
    
    public VehicleStatus getStatus()
    {
        return status;
    }
    
    public void setStatus(VehicleStatus status)
    {
        this.status = status;
    }
}