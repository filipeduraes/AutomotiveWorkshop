// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.serviceorder;

import com.filipeduraes.workshop.client.dtos.*;
import com.filipeduraes.workshop.client.dtos.service.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.service.ServiceStepTypeDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.maintenance.ElevatorType;
import com.filipeduraes.workshop.core.maintenance.ServiceOrderModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;

import java.util.UUID;

public class ServiceOrderLifecycleService
{
    private final EntityViewModel<VehicleDTO> vehicleViewModel;
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final EntityViewModel<EmployeeDTO> employeeViewModel;

    private final ServiceOrderViewModel serviceOrderViewModel;
    private final Workshop workshop;

    private final ServiceOrderQueryService queryService;

    public ServiceOrderLifecycleService(ViewModelRegistry viewModelRegistry, Workshop workshop, ServiceOrderQueryService queryService)
    {
        serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();
        employeeViewModel = viewModelRegistry.getEmployeeViewModel();

        this.workshop = workshop;
        this.queryService = queryService;

        serviceOrderViewModel.OnRegisterAppointmentRequest.addListener(this::registerNewService);
        serviceOrderViewModel.OnStartStepRequest.addListener(this::startNextStep);
        serviceOrderViewModel.OnFinishStepRequest.addListener(this::finishCurrentStep);
    }

    public void dispose()
    {
        serviceOrderViewModel.OnRegisterAppointmentRequest.removeListener(this::registerNewService);
        serviceOrderViewModel.OnStartStepRequest.removeListener(this::startNextStep);
        serviceOrderViewModel.OnFinishStepRequest.removeListener(this::finishCurrentStep);
    }

    private void registerNewService()
    {
        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();

        if (vehicleViewModel.hasLoadedDTO())
        {
            VehicleDTO selectedVehicle = vehicleViewModel.getSelectedDTO();
            String shortDescription = serviceOrderViewModel.getCurrentStepShortDescription();
            String detailedDescription = serviceOrderViewModel.getCurrentStepDetailedDescription();
            ClientDTO selectedClient = clientViewModel.getSelectedDTO();

            UUID serviceOrderID = serviceOrderModule.registerNewAppointment(selectedClient.getID(), selectedVehicle.getID(), shortDescription, detailedDescription);
            ServiceOrder serviceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(serviceOrderID);

            serviceOrderViewModel.setSelectedIndex(0);
            serviceOrderViewModel.setSelectedDTO(ServiceOrderMapper.toDTO(serviceOrder, workshop));
            serviceOrderViewModel.setRequestWasSuccessful(true);
        }
    }

    private void startNextStep()
    {
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();
        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();
        UUID selectedServiceOrderID = serviceOrderDTO.getID();

        ServiceOrder serviceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(selectedServiceOrderID);
        boolean canStartNextStep = serviceOrder.getCurrentStepWasFinished();

        int selectedElevatorTypeIndex = serviceOrderViewModel.getSelectedElevatorTypeIndex();
        boolean isValidElevatorType = selectedElevatorTypeIndex >= 0 && selectedElevatorTypeIndex < ElevatorType.values().length;
        ElevatorType elevatorType = isValidElevatorType ? ElevatorType.values()[selectedElevatorTypeIndex] : null;

        if (canStartNextStep)
        {
            if (serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.APPOINTMENT)
            {
                canStartNextStep = serviceOrderModule.startInspection(selectedServiceOrderID, elevatorType);
            }
            else if (serviceOrderDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
            {
                canStartNextStep = serviceOrderModule.startMaintenance(selectedServiceOrderID, elevatorType);
            }

            queryService.refreshSelectedEntity();
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
        UUID serviceID = selectedDTO.getID();
        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();

        if(selectedDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
        {
            if(!employeeViewModel.hasLoadedDTO())
            {
                serviceOrderViewModel.setRequestWasSuccessful(false);
                return;
            }

            EmployeeDTO newEmployee = employeeViewModel.getSelectedDTO();
            wasSuccessful = serviceOrderModule.finishInspection(serviceID, newEmployee.getID(), shortDescription, detailedDescription);
        }
        else if(selectedDTO.getServiceStep() == ServiceStepTypeDTO.MAINTENANCE)
        {
            wasSuccessful = serviceOrderModule.finishMaintenance(serviceID, shortDescription, detailedDescription);
        }

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(wasSuccessful);
    }
}
