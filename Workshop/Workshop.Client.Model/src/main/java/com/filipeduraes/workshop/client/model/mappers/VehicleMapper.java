package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.util.UUID;

public final class VehicleMapper
{
    public static Vehicle fromDTO(UUID clientID, VehicleDTO vehicleDTO)
    {
        Vehicle vehicle = new Vehicle
        (
            clientID,
            vehicleDTO.getModel(),
            vehicleDTO.getColor(),
            vehicleDTO.getVinNumber(),
            vehicleDTO.getLicensePlate(),
            vehicleDTO.getYear()
        );

        return vehicle;
    }

    public static VehicleDTO toDTO(Vehicle vehicle)
    {
        VehicleDTO selectedVehicle = new VehicleDTO
        (
            vehicle.getID(),
            vehicle.getModel(),
            vehicle.getColor(),
            vehicle.getVinNumber(),
            vehicle.getLicensePlate(),
            vehicle.getYear()
        );

        return selectedVehicle;
    }
}
