package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepTypeDTO;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceStep;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public final class ServiceOrderMapper
{
    public static ServiceOrderDTO toDTO(ServiceOrder serviceOrder, Workshop workshop)
    {
        MaintenanceStep maintenanceStep = serviceOrder.getCurrentMaintenanceStep();
        String shortDescription = serviceOrder.getCurrentStep().getShortDescription();
        String detailedDescription = serviceOrder.getCurrentStep().getDetailedDescription();

        Client owner = workshop.getClientModule().getEntityWithID(serviceOrder.getClientID());
        Vehicle vehicle = workshop.getVehicleModule().getEntityWithID(serviceOrder.getVehicleID());

        return new ServiceOrderDTO
        (
            serviceOrder.getID(),
            serviceOrder.getClientID(),
            serviceOrder.getVehicleID(),
            toServiceStepDTO(maintenanceStep),
            shortDescription,
            detailedDescription,
            owner != null ? owner.getName() : "CLIENTE INVALIDO",
            vehicle != null ? vehicle.toString() : "VEICULO INVALIDO",
            serviceOrder.getCurrentStepWasFinished(),
            toServiceStepsDTO(workshop.getAuthModule(), serviceOrder.getSteps())
        );
    }

    public static ServiceStepTypeDTO toServiceStepDTO(MaintenanceStep maintenanceStep)
    {
        return switch (maintenanceStep)
        {
            case CREATED -> ServiceStepTypeDTO.CREATED;
            case APPOINTMENT -> ServiceStepTypeDTO.APPOINTMENT;
            case ASSESSMENT -> ServiceStepTypeDTO.ASSESSMENT;
            case MAINTENANCE -> ServiceStepTypeDTO.MAINTENANCE;
        };
    }

    public static MaintenanceStep fromServiceStepDTO(ServiceStepTypeDTO serviceStepTypeDTO)
    {
        return switch (serviceStepTypeDTO)
        {
            case CREATED -> MaintenanceStep.CREATED;
            case APPOINTMENT -> MaintenanceStep.APPOINTMENT;
            case ASSESSMENT -> MaintenanceStep.ASSESSMENT;
            case MAINTENANCE -> MaintenanceStep.MAINTENANCE;
        };
    }

    private static Deque<ServiceStepDTO> toServiceStepsDTO(AuthModule authModule, Deque<ServiceStep> serviceSteps)
    {
        return serviceSteps.stream()
                           .map(s -> toServiceStepDTO(authModule, s))
                           .collect(Collectors.toCollection(ArrayDeque::new));
    }

    private static ServiceStepDTO toServiceStepDTO(AuthModule authModule, ServiceStep serviceStep)
    {
        return new ServiceStepDTO
        (
            serviceStep.getShortDescription(),
            serviceStep.getDetailedDescription(),
            TextUtils.formatDate(serviceStep.getStartDate()),
            TextUtils.formatDate(serviceStep.getFinishDate()),
            authModule.getUserFromID(serviceStep.getEmployeeID()).getName(),
            serviceStep.getWasFinished()
        );
    }
}