// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.CrudRepository;
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
    private final CrudRepository<ServiceOrder> serviceOrderRepository;
    private final Set<UUID> userServices;
    private final Set<UUID> openedServices;
    private final UUID loggedEmployeeID;
    
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        ParameterizedType serviceIDListType = Persistence.createParameterizedType(HashSet.class, UUID.class);

        serviceOrderRepository = new CrudRepository<>(WorkshopPaths.SERVICES_PATH, ServiceOrder.class);
        openedServices = Persistence.loadFile(WorkshopPaths.OPENED_SERVICES_PATH, serviceIDListType, new HashSet<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), serviceIDListType, new HashSet<>());
    }

    public CrudRepository<ServiceOrder> getServiceOrderRepository()
    {
        return serviceOrderRepository;
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
        serviceOrder.getCurrentStep().finishStep();

        UUID serviceID = serviceOrderRepository.registerEntity(serviceOrder);
        openedServices.add(serviceID);

        Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
        return serviceID;
    }

    public void startInspection(UUID serviceID)
    {
        if(serviceOrderRepository.hasEntityWithID(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);

            if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.APPOINTMENT)
            {
                serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
                openedServices.remove(serviceID);
                userServices.add(serviceID);

                Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
                Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
                serviceOrderRepository.updateEntity(serviceOrder);
            }
        }
    }

    public void finishInspection(UUID serviceID, UUID newEmployee, String description)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);

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
                serviceOrderRepository.updateEntity(serviceOrder);
            }
        }
    }

    public void startMaintenance(UUID serviceID)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);

            if(serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
            {
                ServiceStep serviceStep = new ServiceStep(loggedEmployeeID);
                serviceOrder.registerStep(serviceStep);

                serviceOrderRepository.updateEntity(serviceOrder);
            }
        }
    }

    public void finishMaintenance(UUID serviceID, String description, ArrayList<Product> services, Purchase purchase)
    {
        if(loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);
            ServiceStep currentStep = serviceOrder.getCurrentStep();
            currentStep.setDetailedDescription(description);
            serviceOrder.finish(services, purchase);

            ParameterizedType type = Persistence.createParameterizedType(HashSet.class, UUID.class);
            Set<UUID> finishedServices = Persistence.loadFile(WorkshopPaths.FINISHED_SERVICES_PATH, type, new HashSet<>());

            finishedServices.add(serviceID);
            userServices.remove(serviceID);

            Persistence.saveFile(finishedServices, WorkshopPaths.FINISHED_SERVICES_PATH);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
            serviceOrderRepository.updateEntity(serviceOrder);
        }
    }

    public void deleteServiceOrder(UUID selectedServiceID)
    {
        serviceOrderRepository.deleteEntityWithID(selectedServiceID);
    }

    private boolean loggedUserHasService(UUID serviceID)
    {
        return userServices.contains(serviceID) && serviceOrderRepository.hasEntityWithID(serviceID);
    }
}