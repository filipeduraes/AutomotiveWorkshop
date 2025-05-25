// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private final Map<UUID, ServiceOrder> ongoingServices;
    private final List<UUID> userServices;
    private final List<UUID> openedServices;
    private final UUID loggedEmployeeID;
    
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        ParameterizedType ongoingServicesType = Persistence.createParameterizedType(HashMap.class, UUID.class, ServiceOrder.class);
        ParameterizedType serviceIDListType = Persistence.createParameterizedType(ArrayList.class, UUID.class);

        ongoingServices = Persistence.loadFile(WorkshopPaths.ONGOING_SERVICES_PATH, ongoingServicesType, new HashMap<>());
        openedServices = Persistence.loadFile(WorkshopPaths.OPENED_SERVICES_PATH, serviceIDListType, new ArrayList<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), serviceIDListType, new ArrayList<>());
    }

    public void registerNewAppointment(UUID vehicleID, String problemDescription)
    {
        UUID serviceID = Persistence.generateUniqueID(ongoingServices);

        ServiceOrder serviceOrder = new ServiceOrder(serviceID, vehicleID);
        serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
        serviceOrder.getCurrentStep().setDescription(problemDescription);

        ongoingServices.put(serviceID, serviceOrder);

        Persistence.saveFile(ongoingServices, WorkshopPaths.ONGOING_SERVICES_PATH);
    }

    public void startInspection(UUID serviceID)
    {
        ServiceOrder serviceOrder = ongoingServices.get(serviceID);

        if (serviceOrder != null && serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.APPOINTMENT)
        {
            serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
            openedServices.remove(serviceID);
            userServices.add(serviceID);

            Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
        }
    }

    public void finishInspection(UUID serviceID, UUID newEmployee, String description)
    {
        ServiceOrder serviceOrder = ongoingServices.get(serviceID);

        if (serviceOrder != null && serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
        {
            serviceOrder.getCurrentStep().setDescription(description);
            userServices.remove(serviceID);
        
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
            
            ParameterizedType serviceIDListType = Persistence.createParameterizedType(ArrayList.class, UUID.class);
            String newEmployeeUserPath = WorkshopPaths.getUserServicesPath(newEmployee);
            
            List<UUID> newEmployeeUserServices = Persistence.loadFile(newEmployeeUserPath, serviceIDListType, new ArrayList<>());
            newEmployeeUserServices.add(serviceID);
            Persistence.saveFile(newEmployeeUserServices, newEmployeeUserPath);
        }
    }
}