package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

public final class ServiceOrderMapper
{
    public static ServiceOrderDTO toDTO(ServiceOrder serviceOrder, Workshop workshop)
    {
        String serviceState = serviceOrder.getCurrentMaintenanceStep().toString();
        String shortDescription = serviceOrder.getCurrentStep().getShortDescription();
        String detailedDescription = serviceOrder.getCurrentStep().getDetailedDescription();

        Client owner = workshop.getClientModule().getEntityWithID(serviceOrder.getClientID());
        Vehicle vehicle = workshop.getVehicleModule().getEntityWithID(serviceOrder.getVehicleID());

        return new ServiceOrderDTO
        (
            serviceOrder.getID(),
            serviceState,
            shortDescription,
            detailedDescription,
            owner != null ? owner.getName() : "CLIENTE INVALIDO",
            vehicle != null ? vehicle.toString() : "VEICULO INVALIDO"
        );
    }
}
