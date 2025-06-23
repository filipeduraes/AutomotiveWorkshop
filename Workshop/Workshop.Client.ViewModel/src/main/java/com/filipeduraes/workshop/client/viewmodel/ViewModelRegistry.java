// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

/**
 * Registro central de todos os ViewModels disponíveis na aplicação.
 * Esta classe gerencia as instâncias únicas de cada ViewModel, fornecendo
 * acesso centralizado a eles através de getters.
 *
 * @author Filipe Durães
 */

public class ViewModelRegistry
{
    /**
     * ViewModel para operações relacionadas a clientes.
     */
    private final ClientViewModel clientViewModel;

    /**
     * ViewModel para operações relacionadas a serviços.
     */
    private final ServiceViewModel serviceViewModel;

    /**
     * ViewModel para operações relacionadas a funcionários.
     */
    private final EmployeeViewModel employeeViewModel;

    /**
     * ViewModel para operações relacionadas a veículos.
     */
    private final VehicleViewModel vehicleViewModel;

    /**
     * Inicializa um novo registro de ViewModels.
     * Cria novas instâncias de todos os ViewModels disponíveis.
     */
    public ViewModelRegistry()
    {
        clientViewModel = new ClientViewModel();
        serviceViewModel = new ServiceViewModel();
        employeeViewModel = new EmployeeViewModel();
        vehicleViewModel = new VehicleViewModel();
    }

    /**
     * Obtém o ViewModel para operações relacionadas a clientes.
     *
     * @return o ViewModel de clientes
     */
    public ClientViewModel getClientViewModel()
    {
        return clientViewModel;
    }

    /**
     * Obtém o ViewModel para operações relacionadas a serviços.
     *
     * @return o ViewModel de serviços
     */
    public ServiceViewModel getServiceViewModel()
    {
        return serviceViewModel;
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
    public VehicleViewModel getVehicleViewModel()
    {
        return vehicleViewModel;
    }
}
