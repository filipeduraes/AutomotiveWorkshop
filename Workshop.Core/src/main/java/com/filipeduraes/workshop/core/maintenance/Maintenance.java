// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.store.Purchase;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class Maintenance
{
    private Employee mechanic;
    private Inspection inspection;
    
    private LocalDate startDate;
    private LocalDate finishDate;
    
    private ArrayList<Service> services = new ArrayList<>();
    private ArrayList<Purchase> purchases = new ArrayList<>();
    
    public Maintenance(Inspection inspection, Employee mechanic)
    {
        this.inspection = inspection;
        this.mechanic = mechanic;
    }
    
    public void start()
    {
        startDate = LocalDate.now();
        inspection.getVehicle().setStatus(VehicleStatus.UNDER_MAINTENANCE);
    }
    
    public void finish(ArrayList<Service> services, ArrayList<Purchase> purchases)
    {
        this.services = services;
        this.purchases = purchases;
        
        finishDate = LocalDate.now();
        inspection.getVehicle().setStatus(VehicleStatus.READY_FOR_DELIVERY);
    }
}