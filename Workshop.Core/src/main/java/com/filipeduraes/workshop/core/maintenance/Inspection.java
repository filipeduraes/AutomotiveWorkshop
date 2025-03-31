// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.auth.Employee;
import java.time.LocalDate;

/**
 *
 * @author Filipe Durães
 */
public class Inspection
{
    private Appointment appointment;
    private Employee mechanic;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String resultsDescription;
    
    public Inspection(Appointment appointment, Employee mechanic)
    {
        this.appointment = appointment;
        this.mechanic = mechanic;
    }
    
    public void start()
    {
        startDate = LocalDate.now();
        getVehicle().setStatus(VehicleStatus.IN_INSPECTION);
    }
    
    public void finish(String description)
    {
        finishDate = LocalDate.now();
        resultsDescription = description;
        getVehicle().setStatus(VehicleStatus.WAITING_MAINTENANCE);
    }
    
    protected Vehicle getVehicle()
    {
        return appointment.getVehicle();
    }
}
