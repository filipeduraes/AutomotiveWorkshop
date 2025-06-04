package com.filipeduraes.workshop.client.dtos;

public class ServiceDTO
{
    private String shortDescription;
    private String detailedDescription;
    private String clientName;
    private String vehicleDescription;

    public ServiceDTO(String shortDescription, String detailedDescription, String clientName, String vehicleDescription)
    {
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.clientName = clientName;
        this.vehicleDescription = vehicleDescription;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public String getDetailedDescription()
    {
        return detailedDescription;
    }

    public String getClientName()
    {
        return clientName;
    }

    public String getVehicleDescription()
    {
        return vehicleDescription;
    }
}