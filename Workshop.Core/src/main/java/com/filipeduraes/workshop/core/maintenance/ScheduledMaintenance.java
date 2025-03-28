// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class ScheduledMaintenance 
{
    private LocalDate scheduledDate;
    
    private LocalDate plannedStartDate;
    private LocalDate startDate;
    
    private LocalDate plannedFinishDate;
    private LocalDate finishDate;
    
    private String problemDescription;
    private UUID clientID;
    private UUID employeeID;
    private Vehicle vehicle;
    
    public ScheduledMaintenance(LocalDate plannedStartDate, String problemDescription, UUID clientID, UUID employeeID, Vehicle vehicle)
    {
        scheduledDate = LocalDate.now();
        
        this.plannedStartDate = plannedStartDate;
        this.problemDescription = problemDescription;
        this.clientID = clientID;
        this.employeeID = employeeID;
        this.vehicle = vehicle;
    }
}