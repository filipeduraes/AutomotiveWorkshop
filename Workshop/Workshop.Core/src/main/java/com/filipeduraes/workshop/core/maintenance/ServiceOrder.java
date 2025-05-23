// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.catalog.Product;
import com.filipeduraes.workshop.core.store.Purchase;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

/**
 * @author Filipe Durães
 */
public class ServiceOrder
{
    private UUID id;

    private Stack<ServiceStep> steps = new Stack<>();
    private ArrayList<Product> services = new ArrayList<>();

    private Purchase purchase;
    private UUID vehicleID;

    public ServiceOrder(UUID serviceID, UUID vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    public UUID getID()
    {
        return id;
    }

    public void registerStep(ServiceStep step)
    {
        steps.push(step);
    }

    public ServiceStep getCurrentStep()
    {
        return steps.peek();
    }

    public MaintenanceStep getCurrentMaintenanceStep()
    {
        return MaintenanceStep.fromInt(steps.size());
    }

    public void finish(ArrayList<Product> services, Purchase purchase)
    {
        this.services = services;
        this.purchase = purchase;
    }
}