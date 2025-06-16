package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

public class ServiceOrderDTO
{
    private final UUID id;
    private final String shortDescription;
    private final String detailedDescription;
    private final String clientName;
    private final String vehicleDescription;
    private final ServiceStepDTO serviceStep;
    private final boolean currentStepWasFinished;

    public ServiceOrderDTO(UUID id, ServiceStepDTO serviceStep, String shortDescription, String detailedDescription, String clientName, String vehicleDescription, boolean currentStepWasFinished)
    {
        this.id = id;
        this.serviceStep = serviceStep;
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.clientName = clientName;
        this.vehicleDescription = vehicleDescription;
        this.currentStepWasFinished = currentStepWasFinished;
    }

    public UUID getID()
    {
        return id;
    }

    public ServiceStepDTO getServiceStep()
    {
        return serviceStep;
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

    public boolean getCurrentStepWasFinished()
    {
        return currentStepWasFinished;
    }

    @Override
    public String toString()
    {
        return String.format
        (
            " - ID: %s%n - Estado: %s%n - Descricao Curta: %s%n - Descricao Detalhada: %s%n - Cliente: %s%n - Veiculo: %s",
            getID(),
            getServiceStep(),
            getShortDescription(),
            getDetailedDescription(),
            getClientName(),
            getVehicleDescription()
        );
    }
}