// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

public enum ClockInTypeDTO
{
    IN("Comecar expediente"),
    START_BREAK("Iniciar pausa"),
    END_BREAK("Voltar da pausa"),
    OUT("Finalizar expediente");

    private final String displayName;

    ClockInTypeDTO(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
