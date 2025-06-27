// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepDTO;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceStep;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.util.Deque;
import java.util.List;

/**
 * Realiza o mapeamento entre as ordens de serviço do domínio e seus DTOs correspondentes.
 * Esta classe fornece métodos para converter objetos entre as camadas de domínio e apresentação.
 *
 * @author Filipe Durães
 */
public final class ServiceOrderMapper
{
    /**
     * Converte uma ordem de serviço do domínio para seu DTO correspondente.
     *
     * @param serviceOrder a ordem de serviço a ser convertida
     * @param workshop a oficina que contém os repositórios necessários
     * @return o DTO correspondente à ordem de serviço
     */
    public static ServiceOrderDTO toDTO(ServiceOrder serviceOrder, Workshop workshop)
    {
        Client owner = workshop.getClientRepository().getEntityWithID(serviceOrder.getClientID());
        Vehicle vehicle = workshop.getVehicleRepository().getEntityWithID(serviceOrder.getVehicleID());

        return new ServiceOrderDTO
        (
            serviceOrder.getID(),
            serviceOrder.getClientID(),
            serviceOrder.getVehicleID(),
            owner != null ? owner.getName() : "CLIENTE INVALIDO",
            vehicle != null ? vehicle.toString() : "VEICULO INVALIDO",
            serviceOrder.getCurrentStepWasFinished(),
            toServiceStepsDTO(workshop.getAuthModule(), serviceOrder.getSteps())
        );
    }

    private static List<ServiceStepDTO> toServiceStepsDTO(AuthModule authModule, List<ServiceStep> serviceSteps)
    {
        return serviceSteps.stream()
                           .map(s -> toServiceStepDTO(authModule, s))
                           .toList();
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