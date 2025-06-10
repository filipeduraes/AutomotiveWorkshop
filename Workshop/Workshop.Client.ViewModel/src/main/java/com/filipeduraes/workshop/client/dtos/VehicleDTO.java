package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

public class VehicleDTO
{
    private UUID id;
    private UUID ownerID;
    private final String model;
    private final String color;
    private final String vinNumber;
    private final String licensePlate;
    private final int year;

    public VehicleDTO(UUID id, String model, String color, String vinNumber, String licensePlate, int year)
    {
        this.id = id;
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    public VehicleDTO(String model, String color, String vinNumber, String licensePlate, int year)
    {
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    public UUID getId()
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

    public String getVinNumber()
    {
        return vinNumber;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public int getYear()
    {
        return year;
    }
}
