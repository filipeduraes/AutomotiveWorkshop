package com.filipeduraes.workshop.core.maintenance;

public enum MaintenanceStep
{
    CREATED("Criado"),
    APPOINTMENT("Agendamento"),
    ASSESSMENT("Avaliacao"),
    MAINTENANCE("Manutencao");

    private String displayName;

    MaintenanceStep(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }

    public static MaintenanceStep fromInt(int stepValue)
    {
        MaintenanceStep[] values = values();

        if(stepValue < 0 || stepValue >= values.length)
        {
            return MaintenanceStep.CREATED;
        }

        return values[stepValue];
    }
}
