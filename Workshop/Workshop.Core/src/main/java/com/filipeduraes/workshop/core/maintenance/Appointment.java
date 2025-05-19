// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.core.vehicle.VehicleStatus;

import java.time.LocalDate;

/**
 *
 * @author Filipe Durães
 */
public class Appointment 
{
    private Vehicle vehicle;
    private LocalDate scheduledDate;
    private String problemDescription;
    private transient Employee schedulerEmployee;
    
    public Appointment(Vehicle vehicle, String problemDescription, Employee schedulerEmployee)
    {
        scheduledDate = LocalDate.now();
        
        this.vehicle = vehicle;
        this.problemDescription = problemDescription;
        this.schedulerEmployee = schedulerEmployee;
        
        vehicle.setStatus(VehicleStatus.RECEIVED);
    }
    
    protected Vehicle getVehicle()
    {
        return vehicle;
    }
}