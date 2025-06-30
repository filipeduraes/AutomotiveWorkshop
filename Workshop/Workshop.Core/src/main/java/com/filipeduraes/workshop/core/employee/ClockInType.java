// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.employee;

public enum ClockInType
{
    IN("Comecar expediente"),
    START_BREAK("Iniciar pausa"),
    END_BREAK("Voltar da pausa"),
    OUT("Finalizar expediente");

    private final String displayName;

    ClockInType(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
