package com.filipeduraes.workshop.client.dtos;

public enum ServiceStepTypeDTO
{
    CREATED("Criado"),
    APPOINTMENT("Agendamento"),
    ASSESSMENT("Inspecao"),
    MAINTENANCE("Manutencao");

    private final String displayName;

    ServiceStepTypeDTO(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
