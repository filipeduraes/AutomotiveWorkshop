// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceRequest;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceFilterType;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceQueryType;
import com.filipeduraes.workshop.core.CrudModule;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.util.*;
import java.util.function.Predicate;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceController 
{
    private final MaintenanceViewModel maintenanceViewModel;
    private final VehicleViewModel vehicleViewModel;
    private final ClientViewModel clientViewModel;
    private final Workshop workshop;

    public MaintenanceController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();
        vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();
        this.workshop = workshop;

        maintenanceViewModel.OnMaintenanceRequest.addListener(this::processRequest);
    }

    public void dispose()
    {
        maintenanceViewModel.OnMaintenanceRequest.addListener(this::processRequest);
    }

    private void processRequest()
    {
        switch (maintenanceViewModel.getMaintenanceRequest())
        {
            case REQUEST_REGISTER_APPOINTMENT:
            {
                registerNewService();
                break;
            }
            case REQUEST_SERVICES:
            {
                requestServices();
                break;
            }
            case REQUEST_DETAILED_SERVICE_INFO:
            {
                break;
            }
            case REQUEST_START_STEP:
            {
                break;
            }
            case REQUEST_FINISH_STEP:
            {
                break;
            }
        }
    }

    private void registerNewService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();

        if(vehicleViewModel.hasSelectedVehicle())
        {
            VehicleDTO selectedVehicle = vehicleViewModel.getSelectedVehicle();
            String shortDescription = maintenanceViewModel.getCurrentStepShortDescription();
            String detailedDescription = maintenanceViewModel.getCurrentStepDetailedDescription();
            ClientDTO selectedClient = clientViewModel.getClient();

            UUID appointmentID = maintenanceModule.registerNewAppointment(selectedClient.getID(), selectedVehicle.getId(), shortDescription, detailedDescription);

            maintenanceViewModel.setCurrentMaintenanceID(appointmentID);
            maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_SUCCESS);
        }
    }

    private void requestServices()
    {
        ServiceQueryType queryType = maintenanceViewModel.getQueryType();
        ServiceFilterType filterType = maintenanceViewModel.getFilterType();

        List<ServiceOrder> entities = new ArrayList<>();

        if(filterType == ServiceFilterType.NONE)
        {
            entities = getServicesWithoutFiltering(queryType);
        }
        else if(filterType == ServiceFilterType.CLIENT)
        {
            entities = getServicesFilteringByClient(queryType, clientViewModel.getClient().getID());
        }
        else if(filterType == ServiceFilterType.DESCRIPTION_PATTERN)
        {
            String descriptionQueryPattern = maintenanceViewModel.getDescriptionQueryPattern();
            entities = getServicesFilteringByDescription(queryType, descriptionQueryPattern);
        }

        String[] descriptions = entities.stream()
                                        .map(this::getServiceListingName)
                                        .toArray(String[]::new);

        maintenanceViewModel.setServicesDescriptions(descriptions);
        maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_SUCCESS);
    }

    private String getServiceListingName(ServiceOrder service)
    {
        String shortDescription = service.getCurrentStep().getShortDescription();
        UUID clientID = service.getClientID();
        UUID vehicleID = service.getVehicleID();

        Client owner = workshop.getClientModule().getEntityWithID(clientID);
        Vehicle vehicle = workshop.getVehicleModule().getEntityWithID(vehicleID);

        if(owner == null || vehicle == null)
        {
            return "INVALID_SERVICE";
        }

        String vehicleDescription = String.format("%s %s (%s)", vehicle.getModel(), vehicle.getColor(), vehicle.getLicensePlate());
        return String.format("%s — %s — %s — %s", shortDescription, owner.getName(), vehicleDescription, service.getCurrentStep().getFinishDate());
    }

    private List<ServiceOrder> getServicesWithoutFiltering(ServiceQueryType queryType)
    {
        return getServicesFiltering(queryType, s -> true);
    }

    private List<ServiceOrder> getServicesFilteringByClient(ServiceQueryType queryType, UUID clientID)
    {
        return getServicesFiltering(queryType, s -> s.getClientID().equals(clientID));
    }

    private List<ServiceOrder> getServicesFilteringByDescription(ServiceQueryType queryType, String pattern)
    {
        return getServicesFiltering(queryType, s -> s.getCurrentStep().getDetailedDescription().contains(pattern));
    }

    private List<ServiceOrder> getServicesFiltering(ServiceQueryType queryType, Predicate<ServiceOrder> filter)
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        final CrudModule<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderModule();

        Predicate<ServiceOrder> typePredicate = switch (queryType)
        {
            case OPENED -> s -> maintenanceModule.getOpenedServices().contains(s.getID());
            case USER -> s -> maintenanceModule.getUserServices().contains(s.getID());
            case GENERAL -> s -> true;
        };

        return serviceOrderModule.findEntitiesWithPredicate(s -> typePredicate.test(s) && filter.test(s));
    }
}