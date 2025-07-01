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
import com.filipeduraes.workshop.core.maintenance.ServiceOrderModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.financial.Sale;

import java.util.UUID;

/**
 * Serviço responsável por gerenciar as modificações em ordens de serviço.
 * Esta classe coordena operações de edição, adição de itens de serviço e vendas,
 * e exclusão de ordens de serviço, atuando como intermediária entre a interface
 * do usuário e o módulo de manutenção da oficina.
 *
 * @author Filipe Durães
 */
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

    /**
     * Constrói um novo serviço de modificação de ordens de serviço.
     * Inicializa as referências necessárias e registra os listeners para
     * responder aos eventos da interface do usuário.
     *
     * @param viewModelRegistry registro contendo as referências para os ViewModels
     * @param workshop instância principal da oficina contendo os módulos do sistema
     * @param queryService serviço de consulta de ordens de serviço
     */
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

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o serviço não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        serviceOrderViewModel.OnEditServiceRequest.removeListener(this::editService);
        serviceOrderViewModel.OnEditServiceStepRequest.removeListener(this::editServiceStep);
        serviceOrderViewModel.OnAddServiceItemRequested.removeListener(this::addServiceItem);
        serviceOrderViewModel.OnAddSaleRequested.removeListener(this::addSale);
        serviceOrderViewModel.OnDeleteRequest.removeListener(this::deleteService);
    }

    /**
     * Edita informações básicas do serviço (cliente ou veículo).
     * Atualiza os dados do serviço com base no tipo de campo selecionado.
     */
    private void editService()
    {
        ServiceOrderModule maintenanceModule = workshop.getMaintenanceModule();
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

    /**
     * Edita uma etapa específica do serviço.
     * Atualiza as descrições da etapa selecionada com base nos dados fornecidos.
     */
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
        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();
        ServiceOrder originalServiceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(id);

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

        boolean couldUpdate = serviceOrderModule.getServiceOrderRepository().updateEntity(editedServiceOrder);

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
    }

    /**
     * Adiciona um item de serviço à ordem de serviço.
     * Registra um novo item de serviço na ordem de serviço selecionada.
     */
    private void addServiceItem()
    {
        if(!serviceOrderViewModel.hasLoadedDTO() || !serviceItemViewModel.hasLoadedDTO())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();
        ServiceOrder serviceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(serviceOrderDTO.getID());

        if(serviceOrder == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        PricedItemDTO serviceItemDTO = serviceItemViewModel.getSelectedDTO();
        PricedItem pricedItem = ServiceItemMapper.fromDTO(serviceItemDTO);

        serviceOrder.registerService(pricedItem);
        boolean couldUpdate = serviceOrderModule.getServiceOrderRepository().updateEntity(serviceOrder);

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
    }

    /**
     * Adiciona uma venda à ordem de serviço.
     * Registra uma venda existente na ordem de serviço selecionada.
     */
    private void addSale()
    {
        if(!serviceOrderViewModel.hasLoadedDTO() || inventoryViewModel.getSaleID() == null)
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();
        ServiceOrderDTO serviceOrderDTO = serviceOrderViewModel.getSelectedDTO();
        ServiceOrder serviceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(serviceOrderDTO.getID());

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
        boolean couldUpdate = serviceOrderModule.getServiceOrderRepository().updateEntity(serviceOrder);

        queryService.refreshSelectedEntity();
        serviceOrderViewModel.setRequestWasSuccessful(couldUpdate);
    }

    /**
     * Exclui a ordem de serviço selecionada.
     * Remove permanentemente a ordem de serviço do sistema.
     */
    private void deleteService()
    {
        UUID selectedServiceID = serviceOrderViewModel.getSelectedDTO().getID();
        ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();
        serviceOrderModule.deleteServiceOrder(selectedServiceID);
    }

    /**
     * Aplica as edições à etapa específica da ordem de serviço.
     * Cria uma cópia da ordem de serviço original e aplica as modificações
     * na etapa selecionada com base no tipo de campo.
     *
     * @param originalServiceOrder ordem de serviço original
     * @param selectedStepIndex índice da etapa a ser editada
     * @return ordem de serviço editada ou null se a edição não for possível
     */
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