// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private Map<UUID, ServiceOrder> userServices;
    private Map<UUID, ServiceOrder> openServices;
    private UUID loggedEmployeeID;
    
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        ParameterizedType type = Persistence.createParameterizedType(ArrayList.class, ServiceOrder.class);

        openServices = Persistence.loadFile(WorkshopPaths.OpenedServicesPath, type, new HashMap<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), type, new HashMap<>());
    }

    public void registerNewAppointment(UUID vehicleID, String problemDescription)
    {
        UUID serviceID = Persistence.generateUniqueID(openServices);

        ServiceOrder serviceOrder = new ServiceOrder(serviceID, vehicleID);
        serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
        serviceOrder.getCurrentStep().setDescription(problemDescription);

        openServices.put(serviceID, serviceOrder);

        Persistence.saveFile(openServices, WorkshopPaths.OpenedServicesPath);
    }

    public void startInspection(UUID serviceID)
    {
        ServiceOrder serviceOrder = openServices.get(serviceID);

        if (serviceOrder != null)
        {
            serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
            openServices.remove(serviceID);
            userServices.put(serviceID, serviceOrder);

            Persistence.saveFile(openServices, WorkshopPaths.OpenedServicesPath);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
        }
    }

    public void finishInspection(UUID serviceID, UUID newEmployee, String description)
    {
        ServiceOrder serviceOrder = userServices.get(serviceID);

        if (serviceOrder != null)
        {
            serviceOrder.getCurrentStep().setDescription(description);
            serviceOrder.registerStep(new ServiceStep(newEmployee));

            userServices.remove(serviceID);
        }
    }
}