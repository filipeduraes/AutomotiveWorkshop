// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.CrudModule;
import com.filipeduraes.workshop.core.catalog.Product;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.store.Purchase;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private final CrudModule<ServiceOrder> serviceOrderModule;
    private final Set<UUID> userServices;
    private final Set<UUID> openedServices;
    private final UUID loggedEmployeeID;
    
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        ParameterizedType serviceIDListType = Persistence.createParameterizedType(HashSet.class, UUID.class);

        serviceOrderModule = new CrudModule<>(WorkshopPaths.SERVICES_PATH, ServiceOrder.class);
        openedServices = Persistence.loadFile(WorkshopPaths.OPENED_SERVICES_PATH, serviceIDListType, new HashSet<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), serviceIDListType, new HashSet<>());
    }

    public CrudModule<ServiceOrder> getServiceOrderModule()
    {
        return serviceOrderModule;
    }

    public Set<UUID> getUserServices()
    {
        return userServices;
    }

    public Set<UUID> getOpenedServices()
    {
        return openedServices;
    }

    public UUID registerNewAppointment(UUID clientID, UUID vehicleID, String shortDescription, String detailedDescription)
    {
        ServiceOrder serviceOrder = new ServiceOrder(clientID, vehicleID);

        serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
        serviceOrder.getCurrentStep().setShortDescription(shortDescription);
        serviceOrder.getCurrentStep().setDetailedDescription(detailedDescription);

        UUID serviceID = serviceOrderModule.registerEntity(serviceOrder);
        openedServices.add(serviceID);

        Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
        return serviceID;
    }

    public void startInspection(UUID serviceID)
    {
        if(serviceOrderModule.hasEntityWithID(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(serviceID);

            if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.APPOINTMENT)
            {
                serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
                openedServices.remove(serviceID);
                userServices.add(serviceID);

                Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
                Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
                serviceOrderModule.updateEntity(serviceOrder);
            }
        }
    }

    public void finishInspection(UUID serviceID, UUID newEmployee, String description)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(serviceID);

            if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
            {
                serviceOrder.getCurrentStep().setDetailedDescription(description);
                userServices.remove(serviceID);

                Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());

                ParameterizedType serviceIDListType = Persistence.createParameterizedType(HashSet.class, UUID.class);
                String newEmployeeUserPath = WorkshopPaths.getUserServicesPath(newEmployee);

                Set<UUID> newEmployeeUserServices = Persistence.loadFile(newEmployeeUserPath, serviceIDListType, new HashSet<>());
                newEmployeeUserServices.add(serviceID);
                Persistence.saveFile(newEmployeeUserServices, newEmployeeUserPath);

                serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
                serviceOrderModule.updateEntity(serviceOrder);
            }
        }
    }

    public void startMaintenance(UUID serviceID)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(serviceID);

            if(serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
            {
                ServiceStep serviceStep = new ServiceStep(loggedEmployeeID);
                serviceOrder.registerStep(serviceStep);

                serviceOrderModule.updateEntity(serviceOrder);
            }
        }
    }

    public void finishMaintenance(UUID serviceID, String description, ArrayList<Product> services, Purchase purchase)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(serviceID);
            ServiceStep currentStep = serviceOrder.getCurrentStep();
            currentStep.setDetailedDescription(description);
            serviceOrder.finish(services, purchase);

            ParameterizedType type = Persistence.createParameterizedType(HashSet.class, UUID.class);
            Set<UUID> finishedServices = Persistence.loadFile(WorkshopPaths.FINISHED_SERVICES_PATH, type, new HashSet<>());

            finishedServices.add(serviceID);
            userServices.remove(serviceID);

            Persistence.saveFile(finishedServices, WorkshopPaths.FINISHED_SERVICES_PATH);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
            serviceOrderModule.updateEntity(serviceOrder);
        }
    }

    private boolean loggedUserHasService(UUID serviceID)
    {
        return userServices.contains(serviceID) && serviceOrderModule.hasEntityWithID(serviceID);
    }
}