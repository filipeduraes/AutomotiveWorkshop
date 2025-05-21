package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDate;
import java.util.UUID;

public class ServiceStep
{
    private UUID employeeID;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String description;

    public ServiceStep(UUID employeeID)
    {
        this.employeeID = employeeID;

        startDate = LocalDate.now();
    }

    public void finishStep()
    {
        finishDate = LocalDate.now();
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

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public LocalDate getFinishDate()
    {
        return finishDate;
    }
}
