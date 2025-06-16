package com.filipeduraes.workshop.client.dtos;

public enum ServiceStepDTO
{
    CREATED("Criado"),
    APPOINTMENT("Agendamento"),
    ASSESSMENT("Inspecao"),
    MAINTENANCE("Manutencao");

    private final String displayName;

    ServiceStepDTO(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
