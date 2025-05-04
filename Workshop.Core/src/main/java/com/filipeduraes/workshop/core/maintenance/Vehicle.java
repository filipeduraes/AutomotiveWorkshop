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
    private String vinNumber;
    private String licensePlate;
    private int year;
    private VehicleStatus status = VehicleStatus.RECEIVED;

    public Vehicle(Client owner, String model, String color, String vinNumber, String licensePlate, int year)
    {
        this.owner = owner;
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    public String getModel() 
    {
        return model;
    }

    public String getColor() 
    {
        return color;
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

    public String getVinNumber()
    {
        return vinNumber;
    }

    public int getYear()
    {
        return year;
    }
}