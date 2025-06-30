// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.serviceorder;

import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.maintenance.ElevatorType;

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
    private final Workshop workshop;

    private final ServiceOrderQueryService queryService;
    private final ServiceOrderModificationService modificationService;
    private final ServiceOrderLifecycleService lifecycleService;

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

        this.workshop = workshop;

        queryService = new ServiceOrderQueryService(viewModelRegistry, workshop);
        modificationService = new ServiceOrderModificationService(viewModelRegistry, workshop, queryService);
        lifecycleService = new ServiceOrderLifecycleService(viewModelRegistry, workshop, queryService);

        serviceOrderViewModel.OnElevatorTypeCheckRequest.addListener(this::checkElevatorAvailability);
    }

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o controlador não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        queryService.dispose();
        modificationService.dispose();
        lifecycleService.dispose();

        serviceOrderViewModel.OnElevatorTypeCheckRequest.removeListener(this::checkElevatorAvailability);
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
}