package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

public class ViewModelRegistry
{
    private final ClientViewModel clientViewModel;
    private final ServiceViewModel serviceViewModel;
    private final AuthViewModel authViewModel;
    private final VehicleViewModel vehicleViewModel;

    public ViewModelRegistry()
    {
        clientViewModel = new ClientViewModel();
        serviceViewModel = new ServiceViewModel();
        authViewModel = new AuthViewModel();
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

    public AuthViewModel getAuthViewModel()
    {
        return authViewModel;
    }

    public VehicleViewModel getVehicleViewModel()
    {
        return vehicleViewModel;
    }
}
