// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class ActiveMaintenance extends ScheduledMaintenance
{
    private ArrayList<Service> services = new ArrayList<>();
    private ArrayList<UUID> purchaseIDs = new ArrayList<>();

    public ActiveMaintenance(LocalDate plannedStartDate, String problemDescription, UUID clientID, UUID employeeID, Vehicle vehicle) 
    {
        super(plannedStartDate, problemDescription, clientID, employeeID, vehicle);
    }
}