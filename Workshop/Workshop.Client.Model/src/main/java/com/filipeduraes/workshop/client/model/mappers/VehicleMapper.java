// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.util.UUID;

/**
 * Responsável pela conversão entre objetos Vehicle e VehicleDTO.
 * Fornece métodos para mapear dados entre as diferentes representações
 * de veículos no sistema.
 *
 * @author Filipe Durães
 */
public final class VehicleMapper
{
    /**
     * Converte um VehicleDTO para um objeto Vehicle.
     *
     * @param clientID ID do cliente proprietário do veículo
     * @param vehicleDTO DTO contendo os dados do veículo
     * @return novo objeto Vehicle com os dados do DTO
     */
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

    /**
     * Converte um objeto Vehicle para VehicleDTO.
     *
     * @param vehicle objeto Vehicle a ser convertido
     * @return novo VehicleDTO com os dados do veículo
     */
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
