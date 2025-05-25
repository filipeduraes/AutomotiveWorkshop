// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.vehicle;

import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Vehicle
{
    private UUID id;
    private UUID ownerID;
    private String model;
    private String color;
    private String vinNumber;
    private String licensePlate;
    private int year;
    private VehicleStatus status = VehicleStatus.RECEIVED;

    public Vehicle(UUID ownerID, String model, String color, String vinNumber, String licensePlate, int year)
    {
        this.ownerID = ownerID;
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    public UUID getID()
    {
        return id;
    }

    public void setID(UUID id)
    {
        this.id = id;
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

    public UUID getOwnerID()
    {
        return ownerID;
    }
}