package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

public class ServiceDTO
{
    private final UUID id;
    private final String serviceState;
    private final String shortDescription;
    private final String detailedDescription;
    private final String clientName;
    private final String vehicleDescription;

    public ServiceDTO(UUID id, String serviceState, String shortDescription, String detailedDescription, String clientName, String vehicleDescription)
    {
        this.id = id;
        this.serviceState = serviceState;
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.clientName = clientName;
        this.vehicleDescription = vehicleDescription;
    }

    public UUID getId()
    {
        return id;
    }

    public String getServiceState()
    {
        return serviceState;
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