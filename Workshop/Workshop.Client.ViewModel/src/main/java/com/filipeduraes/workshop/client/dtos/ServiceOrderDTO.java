package com.filipeduraes.workshop.client.dtos;

import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.List;
import java.util.UUID;


/**
 * Representa uma ordem de serviço no sistema da oficina.
 * Esta classe mantém todas as informações relevantes sobre uma ordem de serviço,
 * incluindo dados do cliente, veículo e etapas do serviço.
 *
 * @author Filipe Durães
 */
public class ServiceOrderDTO
{
    private final UUID id;
    private final UUID clientID;
    private final UUID vehicleID;
    private final String clientName;
    private final String vehicleDescription;
    private final List<ServiceStepDTO> steps;
    private final boolean currentStepWasFinished;

    /**
     * Constrói uma nova ordem de serviço com os parâmetros fornecidos.
     *
     * @param id identificador único da ordem de serviço
     * @param clientID identificador do cliente
     * @param vehicleID identificador do veículo
     * @param clientName nome do cliente
     * @param vehicleDescription descrição do veículo
     * @param currentStepWasFinished indica se a etapa atual foi finalizada
     * @param steps coleção de etapas do serviço
     */
    public ServiceOrderDTO(UUID id, UUID clientID, UUID vehicleID, String clientName, String vehicleDescription, boolean currentStepWasFinished, List<ServiceStepDTO> steps)
    {
        this.id = id;
        this.clientID = clientID;
        this.vehicleID = vehicleID;
        this.clientName = clientName;
        this.vehicleDescription = vehicleDescription;
        this.currentStepWasFinished = currentStepWasFinished;
        this.steps = steps;
    }

    /**
     * Obtém o identificador único da ordem de serviço.
     *
     * @return identificador da ordem de serviço
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Obtém o identificador do cliente associado à ordem de serviço.
     *
     * @return identificador do cliente
     */
    public UUID getClientID()
    {
        return clientID;
    }

    /**
     * Obtém o tipo da etapa atual do serviço.
     *
     * @return tipo da etapa atual
     */
    public ServiceStepTypeDTO getServiceStep()
    {
        return ServiceStepTypeDTO.values()[steps.size()];
    }

    /**
     * Obtém o nome do cliente associado à ordem de serviço.
     *
     * @return nome do cliente
     */
    public String getClientName()
    {
        return clientName;
    }

    /**
     * Obtém a descrição do veículo associado à ordem de serviço.
     *
     * @return descrição do veículo
     */
    public String getVehicleDescription()
    {
        return vehicleDescription;
    }

    /**
     * Verifica se a etapa atual do serviço foi finalizada.
     *
     * @return true se a etapa atual foi finalizada, false caso contrário
     */
    public boolean getCurrentStepWasFinished()
    {
        return currentStepWasFinished;
    }

    /**
     * Obtém a lista de etapas do serviço.
     *
     * @return coleção de etapas do serviço
     */
    public List<ServiceStepDTO> getSteps()
    {
        return steps;
    }

    /**
     * Retorna uma representação em string da ordem de serviço.
     * A string contém o ID, estado atual, cliente, veículo e todas as etapas
     * do serviço em um formato estruturado.
     *
     * @return string formatada com os detalhes da ordem de serviço
     */
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

    /**
     * Obtém o identificador do veículo associado à ordem de serviço.
     *
     * @return identificador do veículo
     */
    public UUID getVehicleID()
    {
        return vehicleID;
    }

    private String getStepsDisplay()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = steps.size() - 1; i >= 0; i--)
        {
            ServiceStepDTO serviceStepDTO = steps.get(i);
            ServiceStepTypeDTO serviceStepTypeDTO = ServiceStepTypeDTO.values()[i + 1];
            builder.append(String.format(" ==> %s%n%s", serviceStepTypeDTO, serviceStepDTO));
        }

        return builder.toString();
    }
}