// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.vehicle;

import com.filipeduraes.workshop.core.WorkshopEntity;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Vehicle extends WorkshopEntity
{
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

    @Override
    public String toString()
    {
        return String.format("%s %s (%s)", model, color, year);
    }
}