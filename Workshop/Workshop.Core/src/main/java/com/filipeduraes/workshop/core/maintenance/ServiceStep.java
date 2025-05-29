package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceStep
{
    private final UUID employeeID;
    private final LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String description;

    public ServiceStep(UUID employeeID)
    {
        this.employeeID = employeeID;

        startDate = LocalDateTime.now();
    }

    public void finishStep()
    {
        finishDate = LocalDateTime.now();
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public UUID getEmployeeID()
    {
        return employeeID;
    }

    public LocalDateTime getStartDate()
    {
        return startDate;
    }

    public LocalDateTime getFinishDate()
    {
        return finishDate;
    }
}
