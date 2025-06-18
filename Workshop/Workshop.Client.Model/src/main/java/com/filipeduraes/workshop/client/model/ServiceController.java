// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepTypeDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceFilterType;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;
import com.filipeduraes.workshop.core.CrudModule;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Filipe Durães
 */
public class ServiceController
{
    private final ServiceViewModel serviceViewModel;
    private final VehicleViewModel vehicleViewModel;
    private final ClientViewModel clientViewModel;
    private final Workshop workshop;

    private List<ServiceOrder> queriedEntities = new ArrayList<>();

    public ServiceController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        serviceViewModel = viewModelRegistry.getServiceViewModel();
        vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();
        this.workshop = workshop;

        serviceViewModel.OnRegisterAppointmentRequest.addListener(this::registerNewService);
        serviceViewModel.OnStartStepRequest.addListener(this::startNextStep);
        serviceViewModel.OnSearchRequest.addListener(this::requestServices);
        serviceViewModel.OnLoadDataRequest.addListener(this::requestDetailedServiceInfo);
        serviceViewModel.OnEditServiceRequest.addListener(this::editSelectedService);
        serviceViewModel.OnDeleteRequest.addListener(this::deleteSelectedService);
    }

    public void dispose()
    {
        serviceViewModel.OnRegisterAppointmentRequest.removeListener(this::registerNewService);
        serviceViewModel.OnStartStepRequest.removeListener(this::startNextStep);
        serviceViewModel.OnSearchRequest.removeListener(this::requestServices);
        serviceViewModel.OnLoadDataRequest.removeListener(this::requestDetailedServiceInfo);
        serviceViewModel.OnEditServiceRequest.removeListener(this::editSelectedService);
        serviceViewModel.OnDeleteRequest.removeListener(this::deleteSelectedService);
    }

    private void registerNewService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();

        if(vehicleViewModel.hasLoadedDTO())
        {
            VehicleDTO selectedVehicle = vehicleViewModel.getSelectedDTO();
            String shortDescription = serviceViewModel.getCurrentStepShortDescription();
            String detailedDescription = serviceViewModel.getCurrentStepDetailedDescription();
            ClientDTO selectedClient = clientViewModel.getSelectedDTO();

            UUID serviceOrderID = maintenanceModule.registerNewAppointment(selectedClient.getID(), selectedVehicle.getID(), shortDescription, detailedDescription);
            ServiceOrder serviceOrder = maintenanceModule.getServiceOrderModule().getEntityWithID(serviceOrderID);

            serviceViewModel.setSelectedIndex(0);
            serviceViewModel.setSelectedDTO(ServiceOrderMapper.toDTO(serviceOrder, workshop));
            serviceViewModel.setWasRequestSuccessful(true);
        }
    }

    private void startNextStep()
    {
        ServiceOrderDTO serviceOrderDTO = serviceViewModel.getSelectedDTO();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        UUID selectedServiceOrderID = serviceOrderDTO.getID();

        ServiceOrder serviceOrder = maintenanceModule.getServiceOrderModule().getEntityWithID(selectedServiceOrderID);
        boolean canStartNextStep = serviceOrder.getCurrentStepWasFinished();

        if(canStartNextStep)
        {
            if(serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.APPOINTMENT)
            {
                maintenanceModule.startInspection(selectedServiceOrderID);
            }
            else if(serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
            {
                maintenanceModule.startMaintenance(selectedServiceOrderID);
            }

            requestDetailedServiceInfo();
        }

        serviceViewModel.setWasRequestSuccessful(canStartNextStep);
    }

    private void requestServices()
    {
        ServiceQueryType queryType = serviceViewModel.getQueryType();
        ServiceFilterType filterType = serviceViewModel.getFilterType();

        if(filterType == ServiceFilterType.NONE)
        {
            queriedEntities = getServicesWithoutFiltering(queryType);
        }
        else if(filterType == ServiceFilterType.CLIENT)
        {
            queriedEntities = getServicesFilteringByClient(queryType, clientViewModel.getSelectedDTO().getID());
        }
        else if(filterType == ServiceFilterType.DESCRIPTION_PATTERN)
        {
            String descriptionQueryPattern = serviceViewModel.getDescriptionQueryPattern();
            queriedEntities = getServicesFilteringByDescription(queryType, descriptionQueryPattern);
        }

        List<String> descriptions = queriedEntities.stream()
                                        .map(this::getServiceListingName)
                                        .collect(Collectors.toList());

        serviceViewModel.setFoundEntitiesDescriptions(descriptions);
        serviceViewModel.setWasRequestSuccessful(true);
    }

    private void requestDetailedServiceInfo()
    {
        int selectedMaintenanceIndex = serviceViewModel.getSelectedIndex();

        if(selectedMaintenanceIndex < 0 || selectedMaintenanceIndex >= queriedEntities.size())
        {
            serviceViewModel.setWasRequestSuccessful(false);
            return;
        }

        ServiceOrder serviceOrder = queriedEntities.get(selectedMaintenanceIndex);
        ServiceOrderDTO serviceOrderDTO = ServiceOrderMapper.toDTO(serviceOrder, workshop);

        serviceViewModel.setSelectedDTO(serviceOrderDTO);
        serviceViewModel.setWasRequestSuccessful(true);
    }

    private void editSelectedService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        CrudModule<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderModule();
        UUID selectedServiceID = serviceViewModel.getSelectedDTO().getID();
        ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(selectedServiceID);

        switch (serviceViewModel.getEditFieldType())
        {
            case CLIENT ->
            {
                ClientDTO clientDTO = clientViewModel.getSelectedDTO();
                CrudModule<Client> clientModule = workshop.getClientModule();
                Client client = clientModule.getEntityWithID(clientDTO.getID());
                UUID vehicleID = vehicleViewModel.getSelectedDTO().getID();

                boolean clientHasVehicle = client.hasVehicleWithID(vehicleID);
                serviceViewModel.setWasRequestSuccessful(clientHasVehicle);

                if (!clientHasVehicle)
                {
                    return;
                }

                serviceOrder.setVehicleID(vehicleID);
                serviceOrder.setClientID(clientDTO.getID());
                serviceOrderModule.updateEntity(serviceOrder);
            }
            case VEHICLE ->
            {
                VehicleDTO vehicleDTO = vehicleViewModel.getSelectedDTO();

                serviceOrder.setVehicleID(vehicleDTO.getID());
                serviceOrderModule.updateEntity(serviceOrder);
            }
            case SHORT_DESCRIPTION ->
            {

            }
            case DETAILED_DESCRIPTION ->
            {

            }
        }

        requestDetailedServiceInfo();
    }

    private void deleteSelectedService()
    {
        UUID selectedServiceID = serviceViewModel.getSelectedDTO().getID();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        maintenanceModule.deleteServiceOrder(selectedServiceID);
    }

    private String getServiceListingName(ServiceOrder service)
    {
        ServiceStep currentStep = service.getCurrentStep();
        UUID clientID = service.getClientID();
        UUID vehicleID = service.getVehicleID();

        Client owner = workshop.getClientModule().getEntityWithID(clientID);
        Vehicle vehicle = workshop.getVehicleModule().getEntityWithID(vehicleID);

        if(owner == null || vehicle == null)
        {
            return "INVALID_SERVICE";
        }

        LocalDateTime startDate = currentStep.getStartDate();
        String shortDescription;

        if(!currentStep.getWasFinished())
        {
            shortDescription = new ArrayList<>(service.getSteps()).get(1).getShortDescription();
        }
        else
        {
            shortDescription = currentStep.getShortDescription();
        }

        return String.format("%s — %s — %s — %s", shortDescription, owner.getName(), vehicle, TextUtils.formatDate(startDate));
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