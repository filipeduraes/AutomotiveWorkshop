// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

/**
 * Representa os diferentes estágios de manutenção de um veículo na oficina.
 * Cada estágio possui um nome de exibição associado.
 *
 * @author Filipe Durães
 */

public enum MaintenanceStep
{
    CREATED("Criado"),
    APPOINTMENT("Agendamento"),
    ASSESSMENT("Inspecao"),
    MAINTENANCE("Manutencao");

    /**
     * O nome de exibição do estágio de manutenção
     */
    private final String displayName;

    /**
     * Constrói um MaintenanceStep com o nome de exibição especificado.
     *
     * @param displayName O nome de exibição do estágio
     */
    MaintenanceStep(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }

    /**
     * Converte um valor inteiro para o estágio de manutenção correspondente.
     * Se o valor estiver fora do intervalo válido, retorna CREATED.
     *
     * @param stepValue O valor inteiro a ser convertido
     * @return O estágio de manutenção correspondente
     */
    public static MaintenanceStep fromInt(int stepValue)
    {
        MaintenanceStep[] values = values();

        if (stepValue < 0 || stepValue >= values.length)
        {
            return MaintenanceStep.CREATED;
        }

        return values[stepValue];
    }
}
