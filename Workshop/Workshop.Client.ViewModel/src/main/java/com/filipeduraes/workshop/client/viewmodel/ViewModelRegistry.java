// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

/**
 * Registro central de todos os ViewModels disponíveis na aplicação.
 * Esta classe gerencia as instâncias únicas de cada ViewModel, fornecendo
 * acesso centralizado a eles por getters.
 *
 * @author Filipe Durães
 */

public class ViewModelRegistry
{
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final ServiceOrderViewModel serviceOrderViewModel;
    private final EmployeeViewModel employeeViewModel;
    private final EntityViewModel<VehicleDTO> vehicleViewModel;
    private final EntityViewModel<PricedItemDTO> serviceItemsViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final ExpenseViewModel expenseViewModel;

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
        serviceItemsViewModel = new EntityViewModel<>();
        inventoryViewModel = new InventoryViewModel();
        expenseViewModel = new ExpenseViewModel();
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
    public ServiceOrderViewModel getServiceOrderViewModel()
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
     * Obtém o ViewModel para operações relacionadas aos itens de serviço.
     *
     * @return o ViewModel de itens de serviço
     */
    public EntityViewModel<PricedItemDTO> getServiceItemsViewModel()
    {
        return serviceItemsViewModel;
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

    /**
     * Obtém o ViewModel para operações relacionadas às despesas.
     *
     * @return o ViewModel de despesas
     */
    public ExpenseViewModel getExpenseViewModel()
    {
        return expenseViewModel;
    }
}
