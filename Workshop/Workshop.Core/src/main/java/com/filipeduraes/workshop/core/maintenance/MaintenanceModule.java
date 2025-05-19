// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private ServiceOrder[] userServices;
    private Employee loggedEmployee;
    
    public MaintenanceModule(Employee loggedEmployee)
    {
        this.loggedEmployee = loggedEmployee;
    }
    
    public void registerNewAppointment(Vehicle vehicle, String problemDescription)
    {
        Appointment appointment = new Appointment(vehicle, problemDescription, loggedEmployee);
        
        ParameterizedType type = Persistence.createParameterizedType(ArrayList.class, Appointment.class);
        ArrayList<Appointment> appointments = Persistence.loadFile(WorkshopPaths.OpenedAppointmentsPath, type, new ArrayList<>());
        
        appointments.add(appointment);
        
        Persistence.saveFile(appointments, WorkshopPaths.OpenedAppointmentsPath);
    }
}