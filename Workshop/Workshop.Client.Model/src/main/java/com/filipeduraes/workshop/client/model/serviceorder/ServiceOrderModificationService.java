// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.serviceorder;

import com.filipeduraes.workshop.client.dtos.*;
import com.filipeduraes.workshop.client.dtos.service.ServiceOrderDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceItemMapper;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.financial.Sale;

import java.util.UUID;

public class ServiceOrderModificationService
{
    private final ServiceOrderViewModel serviceOrderViewModel;
    private final EmployeeViewModel employeeViewModel;
    private final EntityViewModel<VehicleDTO> vehicleViewModel;
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final EntityViewModel<PricedItemDTO> serviceItemViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final Workshop workshop;
    private final ServiceOrderQueryService queryService;

    public ServiceOrderModificationService(ViewModelRegistry viewModelRegistry, Workshop workshop, ServiceOrderQueryService queryService)
    {
        serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();
        employeeViewModel = viewModelRegistry.getEmployeeViewModel();
        inventoryViewModel = viewModelRegistry.getInventoryViewModel();
        serviceItemViewModel = viewModelRegistry.getServiceItemsViewModel();

        this.workshop = workshop;
        this.queryService = queryService;

        serviceOrderViewModel.OnEditServiceRequest.addListener(this::editService);
        serviceOrderViewModel.OnEditServiceStepRequest.addListener(this::editServiceStep);
        serviceOrderViewModel.OnAddServiceItemRequested.addListener(this::addServiceItem);
        serviceOrderViewModel.OnAddSaleRequested.addListener(this::addSale);
        serviceOrderViewModel.OnDeleteRequest.addListener(this::deleteService);
    }

    public void dispose()
    {
        serviceOrderViewModel.OnEditServiceRequest.removeListener(this::editService);
        serviceOrderViewModel.OnEditServiceStepRequest.removeListener(this::editServiceStep);
        serviceOrderViewModel.OnAddServiceItemRequested.removeListener(this::addServiceItem);
        serviceOrderViewModel.OnAddSaleRequested.removeListener(this::addSale);
        serviceOrderViewModel.OnDeleteRequest.removeListener(this::deleteService);
    }

    private void editService()
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

        queryService.refreshSelectedEntity();
    }

    private void editServiceStep()
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

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
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

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
    }

    private void addSale()
    {
        if(!serviceOrderViewModel.hasLoadedDTO() || inventoryViewModel.getSaleID() == null)
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

        UUID saleID = inventoryViewModel.getSaleID();
        Sale sale = workshop.getStore().getSaleWithID(saleID);

        if(sale == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        serviceOrder.registerSale(sale);
        boolean couldUpdate = maintenanceModule.getServiceOrderRepository().updateEntity(serviceOrder);

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
    }

    private void deleteService()
    {
        UUID selectedServiceID = serviceOrderViewModel.getSelectedDTO().getID();
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();
        maintenanceModule.deleteServiceOrder(selectedServiceID);
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
}