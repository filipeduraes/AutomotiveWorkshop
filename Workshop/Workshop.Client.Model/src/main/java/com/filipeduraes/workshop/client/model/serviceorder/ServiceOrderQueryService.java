// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.serviceorder;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.service.ServiceOrderDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.ServiceOrderModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ServiceOrderQueryService
{
    private final ServiceOrderViewModel serviceOrderViewModel;
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final Workshop workshop;
    private List<ServiceOrder> queriedEntities = new ArrayList<>();

    public ServiceOrderQueryService(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();

        this.workshop = workshop;

        serviceOrderViewModel.OnSearchRequest.addListener(this::requestServices);
        serviceOrderViewModel.OnLoadDataRequest.addListener(this::requestDetailedServiceInfo);
    }

    public void dispose()
    {
        serviceOrderViewModel.OnSearchRequest.removeListener(this::requestServices);
        serviceOrderViewModel.OnLoadDataRequest.removeListener(this::requestDetailedServiceInfo);
    }

    public void refreshSelectedEntity()
    {
        if(serviceOrderViewModel.hasLoadedDTO() && serviceOrderViewModel.hasValidSelectedIndex())
        {
            ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();

            ServiceOrderDTO selectedDTO = serviceOrderViewModel.getSelectedDTO();
            ServiceOrder updatedServiceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(selectedDTO.getID());
            ServiceOrderDTO updatedServiceOrderDTO = ServiceOrderMapper.toDTO(updatedServiceOrder, workshop);

            queriedEntities.set(serviceOrderViewModel.getSelectedIndex(), updatedServiceOrder);
            serviceOrderViewModel.setSelectedDTO(updatedServiceOrderDTO);
        }
    }

    private void requestServices()
    {
        ServiceQueryType queryType = serviceOrderViewModel.getQueryType();
        FieldType filterType = serviceOrderViewModel.getFieldType();

        if (filterType == FieldType.NONE)
        {
            queriedEntities = getServicesWithoutFiltering(queryType);
        }
        else if (filterType == FieldType.CLIENT)
        {
            ClientDTO selectedDTO = clientViewModel.getSelectedDTO();

            if (selectedDTO != null)
            {
                queriedEntities = getServicesFilteringByClient(queryType, selectedDTO.getID());
            }
            else
            {
                queriedEntities = getServicesWithoutFiltering(queryType);
            }
        }
        else if (filterType == FieldType.DESCRIPTION)
        {
            String descriptionQueryPattern = serviceOrderViewModel.getDescriptionQueryPattern();
            queriedEntities = getServicesFilteringByDescription(queryType, descriptionQueryPattern);
        }

        List<String> descriptions = queriedEntities.stream()
                                                   .map(this::getServiceListingName)
                                                   .collect(Collectors.toList());

        serviceOrderViewModel.setFoundEntitiesDescriptions(descriptions);
        serviceOrderViewModel.setRequestWasSuccessful(true);
    }

    private void requestDetailedServiceInfo()
    {
        int selectedMaintenanceIndex = serviceOrderViewModel.getSelectedIndex();

        if (selectedMaintenanceIndex < 0 || selectedMaintenanceIndex >= queriedEntities.size())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrder queriedServiceOrder = queriedEntities.get(selectedMaintenanceIndex);
        ServiceOrder serviceOrder = workshop.getMaintenanceModule().getServiceOrderRepository().getEntityWithID(queriedServiceOrder.getID());
        ServiceOrderDTO serviceOrderDTO = ServiceOrderMapper.toDTO(serviceOrder, workshop);

        queriedEntities.set(selectedMaintenanceIndex, serviceOrder);
        serviceOrderViewModel.setSelectedDTO(serviceOrderDTO);
        serviceOrderViewModel.setRequestWasSuccessful(true);
    }

    private String getServiceListingName(ServiceOrder service)
    {
        ServiceStep currentStep = service.getCurrentStep();
        UUID clientID = service.getClientID();
        UUID vehicleID = service.getVehicleID();

        Client owner = workshop.getClientRepository().getEntityWithID(clientID);
        Vehicle vehicle = workshop.getVehicleRepository().getEntityWithID(vehicleID);

        if (owner == null || vehicle == null)
        {
            return "INVALID_SERVICE";
        }

        LocalDateTime startDate = currentStep.getStartDate();
        String shortDescription;

        if (!currentStep.getWasFinished())
        {
            shortDescription = service.getPreviousStep().getShortDescription();
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

        return getServicesFiltering
        (
            queryType, s ->
            {
                String shortDescription = s.getCurrentStep().getShortDescription().toLowerCase();
                String detailedDescription = s.getCurrentStep().getDetailedDescription().toLowerCase();

                return shortDescription.contains(lowerCasePattern) || detailedDescription.contains(lowerCasePattern);
            }
        );
    }

    private List<ServiceOrder> getServicesFiltering(ServiceQueryType queryType, Predicate<ServiceOrder> filter)
    {
        ServiceOrderModule maintenanceModule = workshop.getMaintenanceModule();
        final CrudRepository<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderRepository();
        Set<UUID> loadedClosedServices = maintenanceModule.loadClosedServicesInMonth(LocalDateTime.now());

        Predicate<ServiceOrder> typePredicate = switch (queryType)
        {
            case OPENED -> s -> maintenanceModule.getOpenedServices().contains(s.getID());
            case USER -> s -> maintenanceModule.getUserServices().contains(s.getID());
            case GENERAL -> s -> true;
            case CLOSED_IN_CURRENT_MONTH -> s -> loadedClosedServices.contains(s.getID());
        };

        return serviceOrderModule.findEntitiesWithPredicate(s -> typePredicate.test(s) && filter.test(s));
    }
}
