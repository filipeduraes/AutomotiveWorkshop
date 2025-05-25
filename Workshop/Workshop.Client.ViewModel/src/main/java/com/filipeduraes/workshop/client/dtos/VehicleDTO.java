package com.filipeduraes.workshop.client.dtos;

public class VehicleDTO
{
    private String model;
    private String color;
    private String vinNumber;
    private String licensePlate;
    private int year;

    public VehicleDTO(String model, String color, String vinNumber, String licensePlate, int year)
    {
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
