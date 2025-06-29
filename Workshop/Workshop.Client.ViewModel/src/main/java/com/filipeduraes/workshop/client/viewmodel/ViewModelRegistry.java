// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

/**
 * Registro central de todos os ViewModels disponíveis na aplicação.
 * Esta classe gerencia as instâncias únicas de cada ViewModel, fornecendo
 * acesso centralizado a eles através de getters.
 *
 * @author Filipe Durães
 */

public class ViewModelRegistry
{
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final ServiceOrderViewModel serviceOrderViewModel;
    private final EmployeeViewModel employeeViewModel;
    private final EntityViewModel<VehicleDTO> vehicleViewModel;
    private final InventoryViewModel inventoryViewModel;

    /**
     * Inicializa um novo registro de ViewModels.
     * Cria novas instâncias de todos os ViewModels disponíveis.
     */
    public ViewModelRegistry()
    {
        clientViewModel = new EntityViewModel<>();
        serviceOrderViewModel = new ServiceOrderViewModel();
        employeeViewModel = new EmployeeViewModel();
        vehicleViewModel = new EntityViewModel<>();
        inventoryViewModel = new InventoryViewModel();
    }

    /**
     * Obtém o ViewModel para operações relacionadas a clientes.
     *
     * @return o ViewModel de clientes
     */
    public EntityViewModel<ClientDTO> getClientViewModel()
    {
        return clientViewModel;
    }

    /**
     * Obtém o ViewModel para operações relacionadas a serviços.
     *
     * @return o ViewModel de serviços
     */
    public ServiceOrderViewModel getServiceViewModel()
    {
        return serviceOrderViewModel;
    }

    /**
     * Obtém o ViewModel para operações relacionadas a funcionários.
     *
     * @return o ViewModel de funcionários
     */
    public EmployeeViewModel getEmployeeViewModel()
    {
        return employeeViewModel;
    }

    /**
     * Obtém o ViewModel para operações relacionadas a veículos.
     *
     * @return o ViewModel de veículos
     */
    public EntityViewModel<VehicleDTO> getVehicleViewModel()
    {
        return vehicleViewModel;
    }

    /**
     * Obtém o ViewModel para operações relacionadas ao inventário.
     *
     * @return o ViewModel de inventário
     */
    public InventoryViewModel getInventoryViewModel()
    {
        return inventoryViewModel;
    }
}
