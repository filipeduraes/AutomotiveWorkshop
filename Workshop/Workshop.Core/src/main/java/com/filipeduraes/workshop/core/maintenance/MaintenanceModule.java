// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private Map<UUID, ServiceOrder> ongoingServices;
    private List<UUID> userServices;
    private List<UUID> openedServices;
    private UUID loggedEmployeeID;
    
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        ParameterizedType ongoingServicesType = Persistence.createParameterizedType(HashMap.class, UUID.class, ServiceOrder.class);
        ParameterizedType serviceIDListType = Persistence.createParameterizedType(ArrayList.class, UUID.class);

        ongoingServices = Persistence.loadFile(WorkshopPaths.OngoingServicesPath, ongoingServicesType, new HashMap<>());
        openedServices = Persistence.loadFile(WorkshopPaths.OpenedServicesPath, serviceIDListType, new ArrayList<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), serviceIDListType, new ArrayList<>());
    }

    public void registerNewAppointment(UUID vehicleID, String problemDescription)
    {
        UUID serviceID = Persistence.generateUniqueID(ongoingServices);

        ServiceOrder serviceOrder = new ServiceOrder(serviceID, vehicleID);
        serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
        serviceOrder.getCurrentStep().setDescription(problemDescription);

        ongoingServices.put(serviceID, serviceOrder);

        Persistence.saveFile(ongoingServices, WorkshopPaths.OngoingServicesPath);
    }

    public void startInspection(UUID serviceID)
    {
        ServiceOrder serviceOrder = ongoingServices.get(serviceID);

        if (serviceOrder != null)
        {
            serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
            openedServices.remove(serviceID);
            userServices.add(serviceID);

            Persistence.saveFile(openedServices, WorkshopPaths.OpenedServicesPath);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
        }
    }

    public void finishInspection(UUID serviceID, UUID newEmployee, String description)
    {
        ServiceOrder serviceOrder = ongoingServices.get(serviceID);

        if (serviceOrder != null)
        {
            serviceOrder.getCurrentStep().setDescription(description);

            userServices.remove(serviceID);
        }
    }
}