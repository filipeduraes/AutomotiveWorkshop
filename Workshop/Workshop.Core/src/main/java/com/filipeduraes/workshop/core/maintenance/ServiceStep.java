package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceStep
{
    private final UUID employeeID;
    private final LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String shortDescription = "";
    private String detailedDescription = "";
    private boolean wasFinished = false;

    public ServiceStep(UUID employeeID)
    {
        this.employeeID = employeeID;

        startDate = LocalDateTime.now();
    }

    public void finishStep()
    {
        finishDate = LocalDateTime.now();
        wasFinished = true;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public void setDetailedDescription(String detailedDescription)
    {
        this.detailedDescription = detailedDescription;
    }

    public String getDetailedDescription()
    {
        return detailedDescription;
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

    public boolean getWasFinished()
    {
        return wasFinished;
    }
}
