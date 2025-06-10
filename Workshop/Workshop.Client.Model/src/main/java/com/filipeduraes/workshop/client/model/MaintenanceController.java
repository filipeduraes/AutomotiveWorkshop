// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
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

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Predicate;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceController 
{
    private final Map<MaintenanceRequest, Runnable> handlers = Map.of
    (
        MaintenanceRequest.REQUEST_REGISTER_APPOINTMENT, this::registerNewService,
        MaintenanceRequest.REQUEST_SERVICES, this::requestServices,
        MaintenanceRequest.REQUEST_DETAILED_SERVICE_INFO, this::requestDetailedServiceInfo
    );

    private final MaintenanceViewModel maintenanceViewModel;
    private final VehicleViewModel vehicleViewModel;
    private final ClientViewModel clientViewModel;
    private final Workshop workshop;

    private List<ServiceOrder> queriedEntities = new ArrayList<>();

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
        MaintenanceRequest maintenanceRequest = maintenanceViewModel.getMaintenanceRequest();

        if(handlers.containsKey(maintenanceRequest))
        {
            Runnable handler = handlers.get(maintenanceRequest);
            handler.run();
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

        if(filterType == ServiceFilterType.NONE)
        {
            queriedEntities = getServicesWithoutFiltering(queryType);
        }
        else if(filterType == ServiceFilterType.CLIENT)
        {
            queriedEntities = getServicesFilteringByClient(queryType, clientViewModel.getClient().getID());
        }
        else if(filterType == ServiceFilterType.DESCRIPTION_PATTERN)
        {
            String descriptionQueryPattern = maintenanceViewModel.getDescriptionQueryPattern();
            queriedEntities = getServicesFilteringByDescription(queryType, descriptionQueryPattern);
        }

        String[] descriptions = queriedEntities.stream()
                                        .map(this::getServiceListingName)
                                        .toArray(String[]::new);

        maintenanceViewModel.setServicesDescriptions(descriptions);
        maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_SUCCESS);
    }

    private void requestDetailedServiceInfo()
    {
        int selectedMaintenanceIndex = maintenanceViewModel.getSelectedMaintenanceIndex();

        if(selectedMaintenanceIndex < 0 || selectedMaintenanceIndex >= queriedEntities.size())
        {
            maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_FAILED);
            return;
        }

        ServiceOrder serviceOrder = queriedEntities.get(selectedMaintenanceIndex);
        ServiceOrderDTO serviceOrderDTO = ServiceOrderMapper.toDTO(serviceOrder, workshop);

        maintenanceViewModel.setSelectedService(serviceOrderDTO);
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

        LocalDateTime startDate = service.getCurrentStep().getStartDate();
        String monthName = startDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pt-br"));
        String formattedDate = String.format("%d %s %d:%d", startDate.getDayOfMonth(), monthName, startDate.getHour(), startDate.getMinute());

        return String.format("%s — %s — %s — %s", shortDescription, owner.getName(), vehicle, formattedDate);
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
        String lowerCasePattern = pattern.toLowerCase();

        return getServicesFiltering(queryType, s ->
        {
            String shortDescription = s.getCurrentStep().getShortDescription().toLowerCase();
            String detailedDescription = s.getCurrentStep().getDetailedDescription().toLowerCase();

            return shortDescription.contains(lowerCasePattern) || detailedDescription.contains(lowerCasePattern);
        });
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