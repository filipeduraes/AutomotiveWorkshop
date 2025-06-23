package com.filipeduraes.workshop.client.dtos;

import java.util.Deque;
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
    private final String shortDescription;
    private final String detailedDescription;
    private final String clientName;
    private final String vehicleDescription;
    private final ServiceStepTypeDTO serviceStep;
    private final Deque<ServiceStepDTO> steps;
    private final boolean currentStepWasFinished;

    /**
     * Constrói uma nova ordem de serviço com os parâmetros fornecidos.
     *
     * @param id identificador único da ordem de serviço
     * @param clientID identificador do cliente
     * @param vehicleID identificador do veículo
     * @param serviceStep tipo da etapa atual do serviço
     * @param shortDescription descrição curta do serviço
     * @param detailedDescription descrição detalhada do serviço
     * @param clientName nome do cliente
     * @param vehicleDescription descrição do veículo
     * @param currentStepWasFinished indica se a etapa atual foi finalizada
     * @param steps coleção de etapas do serviço
     */
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
        return serviceStep;
    }

    /**
     * Obtém a descrição curta do serviço.
     *
     * @return descrição curta do serviço ou "Nao atribuida" se não houver descrição
     */
    public String getShortDescription()
    {
        return shortDescription == null ? "Nao atribuida" : shortDescription;
    }

    /**
     * Obtém a descrição detalhada do serviço.
     *
     * @return descrição detalhada do serviço ou "Nao atribuida" se não houver descrição
     */
    public String getDetailedDescription()
    {
        return detailedDescription == null ? "Nao atribuida" : detailedDescription;
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
    public Deque<ServiceStepDTO> getSteps()
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
        int stepIndex = steps.size();

        for (ServiceStepDTO serviceStepDTO : steps)
        {
            ServiceStepTypeDTO serviceStepTypeDTO = ServiceStepTypeDTO.values()[stepIndex];
            builder.append(String.format(" ==> %s%n%s%n", serviceStepTypeDTO, serviceStepDTO));
            stepIndex--;
        }

        return builder.toString();
    }
}