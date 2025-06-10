package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;

public class ViewModelRegistry
{
    private ClientViewModel clientViewModel;
    private MaintenanceViewModel maintenanceViewModel;
    private UserInfoViewModel userInfoViewModel;
    private VehicleViewModel vehicleViewModel;

    public ViewModelRegistry()
    {
        clientViewModel = new ClientViewModel();
        maintenanceViewModel = new MaintenanceViewModel();
        userInfoViewModel = new UserInfoViewModel();
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

    public UserInfoViewModel getUserInfoViewModel()
    {
        return userInfoViewModel;
    }

    public VehicleViewModel getVehicleViewModel()
    {
        return vehicleViewModel;
    }
}
