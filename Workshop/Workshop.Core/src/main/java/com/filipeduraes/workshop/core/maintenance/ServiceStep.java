// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa uma etapa de serviço realizada durante a manutenção de um veículo.
 * Mantém o registro do funcionário responsável, horários de início e término,
 * além das descrições da atividade realizada.
 *
 * @author Filipe Durães
 */
public class ServiceStep
{
    private UUID employeeID;
    private LocalDateTime finishDate;
    private String shortDescription = "";
    private String detailedDescription = "";
    private boolean wasFinished = false;
    private final LocalDateTime startDate;

    /**
     * Cria uma nova etapa de serviço associada a um funcionário.
     * A data de início é automaticamente definida como o momento atual.
     *
     * @param employeeID ID do funcionário responsável pela etapa
     */
    public ServiceStep(UUID employeeID)
    {
        this.employeeID = employeeID;

        startDate = LocalDateTime.now();
    }

    /**
     * Construtor de cópia que cria uma nova etapa de serviço a partir de uma existente.
     * Todos os atributos da etapa original são copiados para a nova instância.
     *
     * @param serviceStep A etapa de serviço a ser copiada
     */
    public ServiceStep(ServiceStep serviceStep)
    {
        employeeID = serviceStep.employeeID;
        startDate = serviceStep.startDate;
        finishDate = serviceStep.finishDate;
        shortDescription = serviceStep.shortDescription;
        detailedDescription = serviceStep.detailedDescription;
        wasFinished = serviceStep.wasFinished;
    }

    /**
     * Finaliza a etapa de serviço, registrando a data e hora de término.
     */
    public void finishStep()
    {
        finishDate = LocalDateTime.now();
        wasFinished = true;
    }

    /**
     * Obtém a descrição resumida da etapa de serviço.
     *
     * @return descrição resumida da etapa
     */
    public String getShortDescription()
    {
        return shortDescription;
    }

    /**
     * Define a descrição resumida da etapa de serviço.
     *
     * @param shortDescription descrição resumida a ser definida
     */
    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    /**
     * Define a descrição detalhada da etapa de serviço.
     *
     * @param detailedDescription descrição detalhada a ser definida
     */
    public void setDetailedDescription(String detailedDescription)
    {
        this.detailedDescription = detailedDescription;
    }

    /**
     * Obtém a descrição detalhada da etapa de serviço.
     *
     * @return descrição detalhada da etapa
     */
    public String getDetailedDescription()
    {
        return detailedDescription;
    }

    /**
     * Sets the employee ID associated with the service step.
     *
     * @param employeeID the unique identifier of the employee responsible for the step
     */
    public void setEmployeeID(UUID employeeID)
    {
        this.employeeID = employeeID;
    }

    /**
     * Obtém o ID do funcionário responsável pela etapa.
     *
     * @return ID do funcionário
     */
    public UUID getEmployeeID()
    {
        return employeeID;
    }

    /**
     * Obtém a data e hora de início da etapa.
     *
     * @return data e hora de início
     */
    public LocalDateTime getStartDate()
    {
        return startDate;
    }

    /**
     * Obtém a data e hora de término da etapa.
     *
     * @return data e hora de término
     */
    public LocalDateTime getFinishDate()
    {
        return finishDate;
    }

    /**
     * Verifica se a etapa foi finalizada.
     *
     * @return true se a etapa foi finalizada, false caso contrário
     */
    public boolean getWasFinished()
    {
        return wasFinished;
    }
}
