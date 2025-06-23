// Copyright Filipe Durães. All rights reserved.

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
import java.util.stream.Collectors;

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
        MaintenanceStep maintenanceStep = serviceOrder.getCurrentMaintenanceStep();
        String shortDescription = serviceOrder.getCurrentStep().getShortDescription();
        String detailedDescription = serviceOrder.getCurrentStep().getDetailedDescription();

        Client owner = workshop.getClientRepository().getEntityWithID(serviceOrder.getClientID());
        Vehicle vehicle = workshop.getVehicleRepository().getEntityWithID(serviceOrder.getVehicleID());

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

    /**
     * Converte um tipo de etapa de manutenção do domínio para seu DTO correspondente.
     *
     * @param maintenanceStep a etapa de manutenção a ser convertida
     * @return o DTO correspondente à etapa de manutenção
     */
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

    /**
     * Converte um DTO de tipo de etapa de serviço para sua entidade de domínio correspondente.
     *
     * @param serviceStepTypeDTO o DTO a ser convertido
     * @return a etapa de manutenção correspondente
     */
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