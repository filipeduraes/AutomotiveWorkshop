package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepDTO;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceStep;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

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
            toServiceStepDTO(maintenanceStep),
            shortDescription,
            detailedDescription,
            owner != null ? owner.getName() : "CLIENTE INVALIDO",
            vehicle != null ? vehicle.toString() : "VEICULO INVALIDO",
            serviceOrder.getCurrentStepWasFinished()
        );
    }

    public static ServiceStepDTO toServiceStepDTO(MaintenanceStep maintenanceStep)
    {
        return switch (maintenanceStep)
        {
            case CREATED -> ServiceStepDTO.CREATED;
            case APPOINTMENT -> ServiceStepDTO.APPOINTMENT;
            case ASSESSMENT -> ServiceStepDTO.ASSESSMENT;
            case MAINTENANCE -> ServiceStepDTO.MAINTENANCE;
        };
    }

    public static MaintenanceStep fromServiceStepDTO(ServiceStepDTO serviceStepDTO)
    {
        return switch (serviceStepDTO)
        {
            case CREATED -> MaintenanceStep.CREATED;
            case APPOINTMENT -> MaintenanceStep.APPOINTMENT;
            case ASSESSMENT -> MaintenanceStep.ASSESSMENT;
            case MAINTENANCE -> MaintenanceStep.MAINTENANCE;
        };
    }
}
