// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.*;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceFilterType;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
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
public class ServiceController
{
    private final ServiceViewModel serviceViewModel;
    private final VehicleViewModel vehicleViewModel;
    private final ClientViewModel clientViewModel;
    private final EmployeeViewModel employeeViewModel;
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
    public ServiceController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        serviceViewModel = viewModelRegistry.getServiceViewModel();
        vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();
        employeeViewModel = viewModelRegistry.getEmployeeViewModel();

        this.workshop = workshop;

        serviceViewModel.OnRegisterAppointmentRequest.addListener(this::registerNewService);
        serviceViewModel.OnStartStepRequest.addListener(this::startNextStep);
        serviceViewModel.OnFinishStepRequest.addListener(this::finishCurrentStep);
        serviceViewModel.OnSearchRequest.addListener(this::requestServices);
        serviceViewModel.OnLoadDataRequest.addListener(this::requestDetailedServiceInfo);
        serviceViewModel.OnEditServiceRequest.addListener(this::editSelectedService);
        serviceViewModel.OnEditServiceStepRequest.addListener(this::editSelectedServiceStep);
        serviceViewModel.OnDeleteRequest.addListener(this::deleteSelectedService);
    }

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o controlador não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        serviceViewModel.OnRegisterAppointmentRequest.removeListener(this::registerNewService);
        serviceViewModel.OnStartStepRequest.removeListener(this::startNextStep);
        serviceViewModel.OnFinishStepRequest.removeListener(this::finishCurrentStep);
        serviceViewModel.OnSearchRequest.removeListener(this::requestServices);
        serviceViewModel.OnLoadDataRequest.removeListener(this::requestDetailedServiceInfo);
        serviceViewModel.OnEditServiceRequest.removeListener(this::editSelectedService);
        serviceViewModel.OnEditServiceStepRequest.removeListener(this::editSelectedServiceStep);
        serviceViewModel.OnDeleteRequest.removeListener(this::deleteSelectedService);
    }

    private void registerNewService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();

        if (vehicleViewModel.hasLoadedDTO())
        {
            VehicleDTO selectedVehicle = vehicleViewModel.getSelectedDTO();
            String shortDescription = serviceViewModel.getCurrentStepShortDescription();
            String detailedDescription = serviceViewModel.getCurrentStepDetailedDescription();
            ClientDTO selectedClient = clientViewModel.getSelectedDTO();

            UUID serviceOrderID = maintenanceModule.registerNewAppointment(selectedClient.getID(), selectedVehicle.getID(), shortDescription, detailedDescription);
            ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(serviceOrderID);

            serviceViewModel.setSelectedIndex(0);
            serviceViewModel.setSelectedDTO(ServiceOrderMapper.toDTO(serviceOrder, workshop));
            serviceViewModel.setRequestWasSuccessful(true);
        }
    }

    private void startNextStep()
    {
        ServiceOrderDTO serviceOrderDTO = serviceViewModel.getSelectedDTO();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        UUID selectedServiceOrderID = serviceOrderDTO.getID();

        ServiceOrder serviceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(selectedServiceOrderID);
        boolean canStartNextStep = serviceOrder.getCurrentStepWasFinished();

        if (canStartNextStep)
        {
            if (serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.APPOINTMENT)
            {
                canStartNextStep = maintenanceModule.startInspection(selectedServiceOrderID);
            }
            else if (serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
            {
                canStartNextStep = maintenanceModule.startMaintenance(selectedServiceOrderID);
            }

            requestDetailedServiceInfo();
        }

        serviceViewModel.setRequestWasSuccessful(canStartNextStep);
    }

    private void finishCurrentStep()
    {
        String shortDescription = serviceViewModel.getCurrentStepShortDescription();
        String detailedDescription = serviceViewModel.getCurrentStepDetailedDescription();

        ServiceOrderDTO selectedDTO = serviceViewModel.getSelectedDTO();

        if(!serviceViewModel.hasLoadedDTO())
        {
            serviceViewModel.setRequestWasSuccessful(false);
            return;
        }

        boolean wasSuccessful = false;

        if(selectedDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
        {
            if(!employeeViewModel.hasLoadedDTO())
            {
                serviceViewModel.setRequestWasSuccessful(false);
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

                queriedEntities.set(serviceViewModel.getSelectedIndex(), updatedServiceOrder);
                serviceViewModel.setSelectedDTO(updatedServiceOrderDTO);
            }
        }

        serviceViewModel.setRequestWasSuccessful(wasSuccessful);
    }

    private void requestServices()
    {
        ServiceQueryType queryType = serviceViewModel.getQueryType();
        ServiceFilterType filterType = serviceViewModel.getFilterType();

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
            String descriptionQueryPattern = serviceViewModel.getDescriptionQueryPattern();
            queriedEntities = getServicesFilteringByDescription(queryType, descriptionQueryPattern);
        }

        List<String> descriptions = queriedEntities.stream()
                                                   .map(this::getServiceListingName)
                                                   .collect(Collectors.toList());

        serviceViewModel.setFoundEntitiesDescriptions(descriptions);
        serviceViewModel.setRequestWasSuccessful(true);
    }

    private void requestDetailedServiceInfo()
    {
        int selectedMaintenanceIndex = serviceViewModel.getSelectedIndex();

        if (selectedMaintenanceIndex < 0 || selectedMaintenanceIndex >= queriedEntities.size())
        {
            serviceViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrder serviceOrder = queriedEntities.get(selectedMaintenanceIndex);
        ServiceOrderDTO serviceOrderDTO = ServiceOrderMapper.toDTO(serviceOrder, workshop);

        serviceViewModel.setSelectedDTO(serviceOrderDTO);
        serviceViewModel.setRequestWasSuccessful(true);
    }

    private void editSelectedService()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        CrudRepository<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderRepository();
        UUID selectedServiceID = serviceViewModel.getSelectedDTO().getID();
        ServiceOrder serviceOrder = serviceOrderModule.getEntityWithID(selectedServiceID);

        switch (serviceViewModel.getFieldType())
        {
            case CLIENT ->
            {
                ClientDTO clientDTO = clientViewModel.getSelectedDTO();
                CrudRepository<Client> clientModule = workshop.getClientRepository();
                Client client = clientModule.getEntityWithID(clientDTO.getID());
                UUID vehicleID = vehicleViewModel.getSelectedDTO().getID();

                boolean clientHasVehicle = client.hasVehicleWithID(vehicleID);
                serviceViewModel.setRequestWasSuccessful(clientHasVehicle);

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
        int selectedStepIndex = serviceViewModel.getSelectedStepIndex();
        ServiceOrderDTO serviceOrderDTO = serviceViewModel.getSelectedDTO();

        if(serviceOrderDTO == null || selectedStepIndex < 0 || selectedStepIndex >= serviceOrderDTO.getSteps().size())
        {
            serviceViewModel.setRequestWasSuccessful(false);
            return;
        }

        UUID id = serviceOrderDTO.getID();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        ServiceOrder originalServiceOrder = maintenanceModule.getServiceOrderRepository().getEntityWithID(id);

        if(originalServiceOrder == null)
        {
            serviceViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrder editedServiceOrder = applyEditingsToServiceOrderStep(originalServiceOrder, selectedStepIndex);

        if(editedServiceOrder == null)
        {
            serviceViewModel.setRequestWasSuccessful(false);
            return;
        }

        boolean couldUpdate = maintenanceModule.getServiceOrderRepository().updateEntity(editedServiceOrder);

        if(couldUpdate)
        {
            queriedEntities.set(serviceViewModel.getSelectedIndex(), editedServiceOrder);
            requestDetailedServiceInfo();
        }

        serviceViewModel.setRequestWasSuccessful(couldUpdate);
    }

    private void deleteSelectedService()
    {
        UUID selectedServiceID = serviceViewModel.getSelectedDTO().getID();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        maintenanceModule.deleteServiceOrder(selectedServiceID);
    }

    private ServiceOrder applyEditingsToServiceOrderStep(ServiceOrder originalServiceOrder, int selectedStepIndex)
    {
        ServiceOrder editedServiceOrder = new ServiceOrder(originalServiceOrder);
        ServiceStep editedServiceStep = editedServiceOrder.getSteps().get(selectedStepIndex);

        switch (serviceViewModel.getFieldType())
        {
            case EMPLOYEE ->
            {
                if(!employeeViewModel.hasLoadedDTO())
                {
                    serviceViewModel.setRequestWasSuccessful(false);
                    return null;
                }

                UUID newEmployeeID = employeeViewModel.getSelectedDTO().getID();
                editedServiceStep.setEmployeeID(newEmployeeID);
            }
            case SHORT_DESCRIPTION ->
            {
                editedServiceStep.setShortDescription(serviceViewModel.getCurrentStepShortDescription());
            }
            case DETAILED_DESCRIPTION ->
            {
                editedServiceStep.setDetailedDescription(serviceViewModel.getCurrentStepDetailedDescription());
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