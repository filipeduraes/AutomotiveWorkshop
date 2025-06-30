// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.*;
import com.filipeduraes.workshop.client.model.mappers.ServiceItemMapper;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceFilterType;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.ElevatorType;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.store.Sale;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos serviços da oficina.
 * Esta classe atua como intermediária entre a camada de visualização (ViewModel) e a camada de domínio,
 * coordenando ações como registro de novos serviços, início de etapas, busca e edição de ordens de serviço.
 *
 * @author Filipe Durães
 */
public class ServiceOrderController
{
    private final ServiceOrderViewModel serviceOrderViewModel;
    private final EntityViewModel<VehicleDTO> vehicleViewModel;
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final EntityViewModel<PricedItemDTO> serviceItemViewModel;
    private final EmployeeViewModel employeeViewModel;
    private final InventoryViewModel inventoyViewModel;
    private final Workshop workshop;

    private List<ServiceOrder> queriedEntities = new ArrayList<>();

    /**
     * Constrói um novo controlador de serviços.
     * Inicializa as referências para os ViewModels necessários e registra os listeners
     * para responder aos eventos da interface do usuário.
     *
     * @param viewModelRegistry registro contendo as referências para os ViewModels
     * @param workshop instância principal da oficina contendo os módulos do sistema
     */
    public ServiceOrderController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();
        employeeViewModel = viewModelRegistry.getEmployeeViewModel();
        serviceItemViewModel = viewModelRegistry.getServiceItemsViewModel();
        inventoyViewModel = viewModelRegistry.getInventoryViewModel();

        this.workshop = workshop;

        serviceOrderViewModel.OnRegisterAppointmentRequest.addListener(this::registerNewService);
        serviceOrderViewModel.OnStartStepRequest.addListener(this::startNextStep);
        serviceOrderViewModel.OnFinishStepRequest.addListener(this::finishCurrentStep);
        serviceOrderViewModel.OnSearchRequest.addListener(this::requestServices);
        serviceOrderViewModel.OnLoadDataRequest.addListener(this::requestDetailedServiceInfo);
        serviceOrderViewModel.OnEditServiceRequest.addListener(this::editSelectedService);
        serviceOrderViewModel.OnEditServiceStepRequest.addListener(this::editSelectedServiceStep);
        serviceOrderViewModel.OnDeleteRequest.addListener(this::deleteSelectedService);
        serviceOrderViewModel.OnElevatorTypeCheckRequest.addListener(this::checkElevatorAvailability);
        serviceOrderViewModel.OnAddServiceItemRequested.addListener(this::addServiceItem);
        serviceOrderViewModel.OnAddSaleRequested.addListener(this::addSale);
    }

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o controlador não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        serviceOrderViewModel.OnRegisterAppointmentRequest.removeListener(this::registerNewService);
        serviceOrderViewModel.OnStartStepRequest.removeListener(this::startNextStep);
        serviceOrderViewModel.OnFinishStepRequest.removeListener(this::finishCurrentStep);
        serviceOrderViewModel.OnSearchRequest.removeListener(this::requestServices);
        serviceOrderViewModel.OnLoadDataRequest.removeListener(this::requestDetailedServiceInfo);
        serviceOrderViewModel.OnEditServiceRequest.removeListener(this::editSelectedService);
        serviceOrderViewModel.OnEditServiceStepRequest.removeListener(this::editSelectedServiceStep);
        serviceOrderViewModel.OnDeleteRequest.removeListener(this::deleteSelectedService);
        serviceOrderViewModel.OnElevatorTypeCheckRequest.removeListener(this::checkElevatorAvailability);
        serviceOrderViewModel.OnAddServiceItemRequested.removeListener(this::addServiceItem);
        serviceOrderViewModel.OnAddSaleRequested.removeListener(this::addSale);
    }

    private void registerNewService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();

        if (vehicleViewModel.hasLoadedDTO())
        {
            VehicleDTO selectedVehicle = vehicleViewModel.getSelectedDTO();
            String shortDescription = serviceOrderViewModel.getCurrentStepShortDescription();
            String detailedDescription = serviceOrderViewModel.getCurrentStepDetailedDescription();
            ClientDTO selectedClient = clientViewModel.getSelectedDTO();

            UUID serviceOrderID = maintenanceModule.registerNewAppointment(selectedClient.getID(), selectedVehicle.getID(), shortDescription, detailedDescription);
            ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(serviceOrderID);

            serviceOrderViewModel.setSelectedIndex(0);
            serviceOrderViewModel.setSelectedDTO(ServiceOrderMapper.toDTO(serviceOrder, workshop));
            serviceOrderViewModel.setRequestWasSuccessful(true);
        }
    }

    private void startNextStep()
    {
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        UUID selectedServiceOrderID = serviceOrderDTO.getID();

        ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(selectedServiceOrderID);
        boolean canStartNextStep = serviceOrder.getCurrentStepWasFinished();

        int selectedElevatorTypeIndex = serviceOrderViewModel.getSelectedElevatorTypeIndex();
        boolean isValidElevatorType = selectedElevatorTypeIndex >= 0 && selectedElevatorTypeIndex < ElevatorType.values().length;
        ElevatorType elevatorType = isValidElevatorType ? ElevatorType.values()[selectedElevatorTypeIndex] : null;

        if (canStartNextStep)
        {
            if (serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.APPOINTMENT)
            {
                canStartNextStep = maintenanceModule.startInspection(selectedServiceOrderID, elevatorType);
            }
            else if (serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
            {
                canStartNextStep = maintenanceModule.startMaintenance(selectedServiceOrderID, elevatorType);
            }

            requestDetailedServiceInfo();
        }

        serviceOrderViewModel.setRequestWasSuccessful(canStartNextStep);
    }

    private void finishCurrentStep()
    {
        String shortDescription = serviceOrderViewModel.getCurrentStepShortDescription();
        String detailedDescription = serviceOrderViewModel.getCurrentStepDetailedDescription();

        ServiceOrderDTO selectedDTO = serviceOrderViewModel.getSelectedDTO();

        if(!serviceOrderViewModel.hasLoadedDTO())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        boolean wasSuccessful = false;

        if(selectedDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
        {
            if(!employeeViewModel.hasLoadedDTO())
            {
                serviceOrderViewModel.setRequestWasSuccessful(false);
                return;
            }

            EmployeeDTO newEmployee = employeeViewModel.getSelectedDTO();
            MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
            UUID serviceID = selectedDTO.getID();

            wasSuccessful = maintenanceModule.finishInspection(serviceID, newEmployee.getID(), shortDescription, detailedDescription);

            if(wasSuccessful)
            {
                ServiceOrder updatedServiceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(serviceID);
                ServiceOrderDTO updatedServiceOrderDTO = ServiceOrderMapper.toDTO(updatedServiceOrder, workshop);

                queriedEntities.set(serviceOrderViewModel.getSelectedIndex(), updatedServiceOrder);
                serviceOrderViewModel.setSelectedDTO(updatedServiceOrderDTO);
            }
        }

        serviceOrderViewModel.setRequestWasSuccessful(wasSuccessful);
    }

    private void requestServices()
    {
        ServiceQueryType queryType = serviceOrderViewModel.getQueryType();
        ServiceFilterType filterType = serviceOrderViewModel.getFilterType();

        if (filterType == ServiceFilterType.NONE)
        {
            queriedEntities = getServicesWithoutFiltering(queryType);
        }
        else if (filterType == ServiceFilterType.CLIENT)
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
        else if (filterType == ServiceFilterType.DESCRIPTION_PATTERN)
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

    private void editSelectedService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        CrudRepository<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderRepository();
        UUID selectedServiceID = serviceOrderViewModel.getSelectedDTO().getID();
        ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(selectedServiceID);

        switch (serviceOrderViewModel.getFieldType())
        {
            case CLIENT ->
            {
                ClientDTO clientDTO = clientViewModel.getSelectedDTO();
                CrudRepository<Client> clientModule = workshop.getClientRepository();
                Client client = clientModule.getEntityWithID(clientDTO.getID());
                UUID vehicleID = vehicleViewModel.getSelectedDTO().getID();

                boolean clientHasVehicle = client.hasVehicleWithID(vehicleID);
                serviceOrderViewModel.setRequestWasSuccessful(clientHasVehicle);

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
        }

        requestDetailedServiceInfo();
    }

    private void editSelectedServiceStep()
    {
        int selectedStepIndex = serviceOrderViewModel.getSelectedStepIndex();
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();

        if(serviceOrderDTO == null || selectedStepIndex < 0 || selectedStepIndex >= serviceOrderDTO.getSteps().size())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        UUID id = serviceOrderDTO.getID();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        ServiceOrder originalServiceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(id);

        if(originalServiceOrder == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrder editedServiceOrder = applyEditingsToServiceOrderStep(originalServiceOrder, selectedStepIndex);

        if(editedServiceOrder == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        boolean couldUpdate = maintenanceModule.getServiceOrderRepository().updateEntity(editedServiceOrder);

        if(couldUpdate)
        {
            queriedEntities.set(serviceOrderViewModel.getSelectedIndex(), editedServiceOrder);
            requestDetailedServiceInfo();
        }

        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
    }

    private void deleteSelectedService()
    {
        UUID selectedServiceID = serviceOrderViewModel.getSelectedDTO().getID();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        maintenanceModule.deleteServiceOrder(selectedServiceID);
    }

    private void checkElevatorAvailability()
    {
        int selectedElevatorTypeIndex = serviceOrderViewModel.getSelectedElevatorTypeIndex();

        if(selectedElevatorTypeIndex < 0 || selectedElevatorTypeIndex >= ElevatorType.values().length)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        ElevatorType selectedElevatorType = ElevatorType.values()[selectedElevatorTypeIndex];
        boolean hasElevator = workshop.getMaintenanceModule().hasAvailableElevatorOfType(selectedElevatorType);
        serviceOrderViewModel.setRequestWasSuccessful(hasElevator);
    }

    private void addServiceItem()
    {
        if(!serviceOrderViewModel.hasLoadedDTO() || !serviceItemViewModel.hasLoadedDTO())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();
        ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(serviceOrderDTO.getID());

        if(serviceOrder == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        PricedItemDTO serviceItemDTO = serviceItemViewModel.getSelectedDTO();
        PricedItem pricedItem = ServiceItemMapper.fromDTO(serviceItemDTO);

        serviceOrder.registerService(pricedItem);
        boolean couldUpdate = maintenanceModule.getServiceOrderRepository().updateEntity(serviceOrder);

        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
        requestDetailedServiceInfo();
    }

    private void addSale()
    {
        if(!serviceOrderViewModel.hasLoadedDTO() || !inventoyViewModel.hasLoadedDTO())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();
        ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(serviceOrderDTO.getID());

        if(serviceOrder == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        UUID saleID = inventoyViewModel.getSaleID();
        Sale sale = workshop.getStore().getSaleWithID(saleID);

        if(sale == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        serviceOrder.registerSale(sale);
        boolean couldUpdate = maintenanceModule.getServiceOrderRepository().updateEntity(serviceOrder);

        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
        requestDetailedServiceInfo();
    }

    private ServiceOrder applyEditingsToServiceOrderStep(ServiceOrder originalServiceOrder, int selectedStepIndex)
    {
        ServiceOrder editedServiceOrder = new ServiceOrder(originalServiceOrder);
        ServiceStep editedServiceStep = editedServiceOrder.getSteps().get(selectedStepIndex);

        switch (serviceOrderViewModel.getFieldType())
        {
            case EMPLOYEE ->
            {
                if(!employeeViewModel.hasLoadedDTO())
                {
                    serviceOrderViewModel.setRequestWasSuccessful(false);
                    return null;
                }

                UUID newEmployeeID = employeeViewModel.getSelectedDTO().getID();
                editedServiceStep.setEmployeeID(newEmployeeID);
            }
            case SHORT_DESCRIPTION ->
            {
                editedServiceStep.setShortDescription(serviceOrderViewModel.getCurrentStepShortDescription());
            }
            case DETAILED_DESCRIPTION ->
            {
                editedServiceStep.setDetailedDescription(serviceOrderViewModel.getCurrentStepDetailedDescription());
            }
        }

        return editedServiceOrder;
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
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        final CrudRepository<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderRepository();

        Predicate<ServiceOrder> typePredicate = switch (queryType)
        {
            case OPENED -> s -> maintenanceModule.getOpenedServices().contains(s.getID());
            case USER -> s -> maintenanceModule.getUserServices().contains(s.getID());
            case GENERAL -> s -> true;
        };

        return serviceOrderModule.findEntitiesWithPredicate(s -> typePredicate.test(s) && filter.test(s));
    }
}