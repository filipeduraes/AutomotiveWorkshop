// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.catalog.Product;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.store.Purchase;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private final Map<UUID, ServiceOrder> services;
    private final Set<UUID> userServices;
    private final Set<UUID> openedServices;
    private final UUID loggedEmployeeID;
    
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        ParameterizedType ongoingServicesType = Persistence.createParameterizedType(HashMap.class, UUID.class, ServiceOrder.class);
        ParameterizedType serviceIDListType = Persistence.createParameterizedType(ArrayList.class, UUID.class);

        services = Persistence.loadFile(WorkshopPaths.SERVICES_PATH, ongoingServicesType, new HashMap<>());
        openedServices = Persistence.loadFile(WorkshopPaths.OPENED_SERVICES_PATH, serviceIDListType, new HashSet<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), serviceIDListType, new HashSet<>());
    }

    public String[] fetchOpenedAppointmentsDescriptions()
    {
        String[] descriptions = new String[openedServices.size()];
        int index = 0;

        for(UUID openedServiceID : openedServices)
        {
            ServiceOrder serviceOrder = services.get(openedServiceID);
            ServiceStep currentStep = serviceOrder.getCurrentStep();

            LocalDateTime startDate = currentStep.getStartDate();
            String description = currentStep.getDescription();

            descriptions[index] = String.format("%s: %s", startDate.toString(), descriptions);
            index++;
        }

        return descriptions;
    }

    public UUID registerNewAppointment(UUID vehicleID, String problemDescription)
    {
        UUID serviceID = Persistence.generateUniqueID(services);

        ServiceOrder serviceOrder = new ServiceOrder(serviceID, vehicleID);
        serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
        serviceOrder.getCurrentStep().setDescription(problemDescription);

        services.put(serviceID, serviceOrder);
        openedServices.add(serviceID);

        Persistence.saveFile(services, WorkshopPaths.SERVICES_PATH);
        Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
        return serviceID;
    }

    public void startInspection(UUID serviceID)
    {
        if(services.containsKey(serviceID))
        {
            ServiceOrder serviceOrder = services.get(serviceID);

            if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.APPOINTMENT)
            {
                serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
                openedServices.remove(serviceID);
                userServices.add(serviceID);

                Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
                Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
            }
        }
    }

    public void finishInspection(UUID serviceID, UUID newEmployee, String description)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = services.get(serviceID);

            if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
            {
                serviceOrder.getCurrentStep().setDescription(description);
                userServices.remove(serviceID);

                Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());

                ParameterizedType serviceIDListType = Persistence.createParameterizedType(HashSet.class, UUID.class);
                String newEmployeeUserPath = WorkshopPaths.getUserServicesPath(newEmployee);

                Set<UUID> newEmployeeUserServices = Persistence.loadFile(newEmployeeUserPath, serviceIDListType, new HashSet<>());
                newEmployeeUserServices.add(serviceID);
                Persistence.saveFile(newEmployeeUserServices, newEmployeeUserPath);
            }
        }
    }

    public void startMaintenance(UUID serviceID)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = services.get(serviceID);

            if(serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
            {
                ServiceStep serviceStep = new ServiceStep(loggedEmployeeID);
                serviceOrder.registerStep(serviceStep);

                Persistence.saveFile(services, WorkshopPaths.SERVICES_PATH);
            }
        }
    }

    public void finishMaintenance(UUID serviceID, String description, ArrayList<Product> services, Purchase purchase)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = this.services.get(serviceID);
            ServiceStep currentStep = serviceOrder.getCurrentStep();
            currentStep.setDescription(description);
            serviceOrder.finish(services, purchase);

            ParameterizedType type = Persistence.createParameterizedType(HashSet.class, UUID.class);
            Set<UUID> finishedServices = Persistence.loadFile(WorkshopPaths.FINISHED_SERVICES_PATH, type, new HashSet<>());

            finishedServices.add(serviceID);
            userServices.remove(serviceID);

            Persistence.saveFile(finishedServices, WorkshopPaths.FINISHED_SERVICES_PATH);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
        }
    }

    private boolean loggedUserHasService(UUID serviceID)
    {
        return userServices.contains(serviceID) && services.containsKey(serviceID);
    }
}