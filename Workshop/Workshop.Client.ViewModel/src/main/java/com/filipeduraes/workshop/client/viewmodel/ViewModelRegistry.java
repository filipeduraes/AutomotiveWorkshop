package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;

public class ViewModelRegistry
{
    private final ClientViewModel clientViewModel;
    private final MaintenanceViewModel maintenanceViewModel;
    private final AuthViewModel authViewModel;
    private final VehicleViewModel vehicleViewModel;

    public ViewModelRegistry()
    {
        clientViewModel = new ClientViewModel();
        maintenanceViewModel = new MaintenanceViewModel();
        authViewModel = new AuthViewModel();
        vehicleViewModel = new VehicleViewModel();
    }

    public ClientViewModel getClientViewModel()
    {
        return clientViewModel;
    }

    public MaintenanceViewModel getMaintenanceViewModel()
    {
        return maintenanceViewModel;
    }

    public AuthViewModel getUserInfoViewModel()
    {
        return authViewModel;
    }

    public VehicleViewModel getVehicleViewModel()
    {
        return vehicleViewModel;
    }
}
