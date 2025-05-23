package com.filipeduraes.workshop.core.maintenance;

public enum MaintenanceStep
{
    CREATED,
    APPOINTMENT,
    ASSESSMENT,
    MAINTENANCE;

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
