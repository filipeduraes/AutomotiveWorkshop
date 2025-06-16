// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.core.catalog.Product;
import com.filipeduraes.workshop.core.store.Purchase;

import java.util.*;

/**
 * @author Filipe Durães
 */
public class ServiceOrder extends WorkshopEntity
{
    private final Deque<ServiceStep> steps = new ArrayDeque<>();
    private List<Product> services = new ArrayList<>();

    private Purchase purchase;
    private final UUID clientID;
    private final UUID vehicleID;
    private boolean finished = false;

    public ServiceOrder(UUID clientID, UUID vehicleID)
    {
        this.vehicleID = vehicleID;
        this.clientID = clientID;
    }

    public boolean getFinished()
    {
        return finished;
    }

    public void registerStep(ServiceStep step)
    {
        finishCurrentStep();
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
        finishCurrentStep();

        finished = true;
    }

    private void finishCurrentStep()
    {
        if(!steps.isEmpty())
        {
            getCurrentStep().finishStep();
        }
    }

    public UUID getClientID()
    {
        return clientID;
    }

    public UUID getVehicleID()
    {
        return vehicleID;
    }

    public boolean getCurrentStepWasFinished()
    {
        return getCurrentStep().getWasFinished();
    }
}