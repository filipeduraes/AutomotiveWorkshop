package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

public class ViewModelRegistry
{
    private final ClientViewModel clientViewModel;
    private final ServiceViewModel serviceViewModel;
    private final EmployeeViewModel employeeViewModel;
    private final VehicleViewModel vehicleViewModel;

    public ViewModelRegistry()
    {
        clientViewModel = new ClientViewModel();
        serviceViewModel = new ServiceViewModel();
        employeeViewModel = new EmployeeViewModel();
        vehicleViewModel = new VehicleViewModel();
    }

    public ClientViewModel getClientViewModel()
    {
        return clientViewModel;
    }

    public ServiceViewModel getServiceViewModel()
    {
        return serviceViewModel;
    }

    public EmployeeViewModel getEmployeeViewModel()
    {
        return employeeViewModel;
    }

    public VehicleViewModel getVehicleViewModel()
    {
        return vehicleViewModel;
    }
}
