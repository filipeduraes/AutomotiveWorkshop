// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.catalog.Product;
import com.filipeduraes.workshop.core.store.Purchase;
import com.filipeduraes.workshop.core.vehicle.VehicleStatus;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class ServiceOrder
{
    private Employee mechanic;
    private Inspection inspection;
    
    private LocalDate startDate;
    private LocalDate finishDate;
    
    private ArrayList<Product> services = new ArrayList<>();
    private Purchase purchase;
    
    public ServiceOrder(Inspection inspection, Employee mechanic)
    {
        this.inspection = inspection;
        this.mechanic = mechanic;
    }
    
    public void start()
    {
        startDate = LocalDate.now();
        inspection.getVehicle().setStatus(VehicleStatus.UNDER_MAINTENANCE);
    }
    
    public void finish(ArrayList<Product> services, Purchase purchase)
    {
        this.services = services;
        this.purchase = purchase;
        
        finishDate = LocalDate.now();
        inspection.getVehicle().setStatus(VehicleStatus.READY_FOR_DELIVERY);
    }
}