// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

/**
 *
 * @author Filipe Durães
 */
public class Vehicle
{
    private String model;
    private String color;
    private String type;
    private String licensePlate;
    
    public Vehicle(String model, String color, String type, String licensePlate)
    {
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
}