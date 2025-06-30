// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos.service;

/**
 * Representa os diferentes tipos de etapas no processo de serviço da oficina.
 * Cada tipo possui um nome de exibição associado para apresentação ao usuário.
 *
 * @author Filipe Durães
 */
public enum ServiceStepTypeDTO
{
    /**
     * Etapa inicial quando o serviço é criado no sistema.
     */
    CREATED("Criado"),

    /**
     * Etapa de agendamento do serviço.
     */
    APPOINTMENT("Agendamento"),

    /**
     * Etapa de inspeção do veículo.
     */
    ASSESSMENT("Inspecao"),

    /**
     * Etapa de execução da manutenção do veículo.
     */
    MAINTENANCE("Manutencao");

    private final String displayName;

    /**
     * Constrói um ServiceStepTypeDTO com o nome de exibição especificado.
     *
     * @param displayName O nome de exibição da etapa de serviço
     */
    ServiceStepTypeDTO(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * Retorna o nome de exibição da etapa de serviço.
     *
     * @return nome de exibição da etapa
     */
    @Override
    public String toString()
    {
        return displayName;
    }
}
