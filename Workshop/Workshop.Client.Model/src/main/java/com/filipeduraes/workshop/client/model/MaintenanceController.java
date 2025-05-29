// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.MaintenanceRequest;
import com.filipeduraes.workshop.client.viewmodel.MaintenanceViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;

import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceController 
{
    private final MaintenanceViewModel maintenanceViewModel;
    private final VehicleViewModel vehicleViewModel;
    private final Workshop workshop;

    public MaintenanceController(MaintenanceViewModel maintenanceViewModel, VehicleViewModel vehicleViewModel, Workshop workshop)
    {
        this.maintenanceViewModel = maintenanceViewModel;
        this.vehicleViewModel = vehicleViewModel;
        this.workshop = workshop;

        maintenanceViewModel.OnMaintenanceRequest.addListener(this::processRequest);
    }

    public void dispose()
    {
        maintenanceViewModel.OnMaintenanceRequest.addListener(this::processRequest);
    }

    private void processRequest()
    {
        MaintenanceModule maintenanceModule = workshop.getMaintenanceModule();

        switch (maintenanceViewModel.getMaintenanceRequest())
        {
            case REQUEST_OPENED_APPOINTMENTS:
            {
                break;
            }
            case REQUEST_USER_SERVICES:
            {
                break;
            }
            case REQUEST_DETAILED_SERVICE_INFO:
            {
                break;
            }
            case REQUEST_REGISTER_APPOINTMENT:
            {
                if(vehicleViewModel.hasSelectedVehicle())
                {
                    VehicleDTO selectedVehicle = vehicleViewModel.getSelectedVehicle();
                    String description = maintenanceViewModel.getCurrentStepDescription();
                    UUID appointmentID = maintenanceModule.registerNewAppointment(selectedVehicle.getId(), description);

                    maintenanceViewModel.setCurrentMaintenanceID(appointmentID);
                    maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_SUCCESS);
                }
                break;
            }
            case REQUEST_START_STEP:
            {
                break;
            }
            case REQUEST_FINISH_STEP:
            {
                break;
            }
        }
    }
}