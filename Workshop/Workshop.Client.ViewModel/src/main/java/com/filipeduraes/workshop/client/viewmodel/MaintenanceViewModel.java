package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;

import java.util.UUID;

public class MaintenanceViewModel
{
    public final Observer OnMaintenanceRequest = new Observer();

    private String currentStepDescription;
    private UUID currentMaintenanceID;
    private MaintenanceRequest maintenanceRequest;

    public String getCurrentStepDescription()
    {
        return currentStepDescription;
    }

    public void setCurrentStepDescription(String currentStepDescription)
    {
        this.currentStepDescription = currentStepDescription;
    }

    public MaintenanceRequest getMaintenanceRequest()
    {
        return maintenanceRequest;
    }

    public void setMaintenanceRequest(MaintenanceRequest maintenanceRequest)
    {
        if(this.maintenanceRequest != maintenanceRequest)
        {
            this.maintenanceRequest = maintenanceRequest;
            OnMaintenanceRequest.broadcast();
        }
    }

    public UUID getCurrentMaintenanceID()
    {
        return currentMaintenanceID;
    }

    public void setCurrentMaintenanceID(UUID currentMaintenanceID)
    {
        this.currentMaintenanceID = currentMaintenanceID;
    }
}