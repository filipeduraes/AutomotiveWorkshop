package com.filipeduraes.workshop.client.dtos;

import java.util.Deque;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServiceOrderDTO
{
    private final UUID id;
    private final UUID clientID;
    private final UUID vehicleID;
    private final String shortDescription;
    private final String detailedDescription;
    private final String clientName;
    private final String vehicleDescription;
    private final ServiceStepTypeDTO serviceStep;
    private final Deque<ServiceStepDTO> steps;
    private final boolean currentStepWasFinished;

    public ServiceOrderDTO(UUID id, UUID clientID, UUID vehicleID, ServiceStepTypeDTO serviceStep, String shortDescription, String detailedDescription, String clientName, String vehicleDescription, boolean currentStepWasFinished, Deque<ServiceStepDTO> steps)
    {
        this.id = id;
        this.clientID = clientID;
        this.vehicleID = vehicleID;
        this.serviceStep = serviceStep;
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.clientName = clientName;
        this.vehicleDescription = vehicleDescription;
        this.currentStepWasFinished = currentStepWasFinished;
        this.steps = steps;
    }

    public UUID getID()
    {
        return id;
    }

    public UUID getClientID()
    {
        return clientID;
    }

    public ServiceStepTypeDTO getServiceStep()
    {
        return serviceStep;
    }

    public String getShortDescription()
    {
        return shortDescription == null ? "Nao atribuida" : shortDescription;
    }

    public String getDetailedDescription()
    {
        return detailedDescription == null ? "Nao atribuida" : detailedDescription;
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

    public Deque<ServiceStepDTO> getSteps()
    {
        return steps;
    }

    @Override
    public String toString()
    {
        return String.format
        (
            " - ID: %s%n - Estado: %s%n - Cliente: %s%n - Veiculo: %s%n - Etapas:%n%s",
            getID(),
            getServiceStep(),
            getClientName(),
            getVehicleDescription(),
            getStepsDisplay()
        );
    }

    public UUID getVehicleID()
    {
        return vehicleID;
    }

    private String getStepsDisplay()
    {
        StringBuilder builder = new StringBuilder();
        int stepIndex = steps.size();

        for(ServiceStepDTO serviceStepDTO : steps)
        {
            ServiceStepTypeDTO serviceStepTypeDTO = ServiceStepTypeDTO.values()[stepIndex];
            builder.append(String.format(" ==> %s%n%s%n", serviceStepTypeDTO, serviceStepDTO));
            stepIndex--;
        }

        return builder.toString();
    }
}