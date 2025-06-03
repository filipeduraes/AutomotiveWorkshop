package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ServiceDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.UUID;

public class MaintenanceViewModel
{
    public final Observer OnMaintenanceRequest = new Observer();

    private MaintenanceRequest maintenanceRequest;
    private ServiceDTO selectedService;
    private String[] openedAppointmentsDescriptions;
    private String currentStepDescription;
    private UUID currentMaintenanceID;
    private int selectedMaintenanceIndex = -1;

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

    public ServiceDTO getSelectedService()
    {
        return selectedService;
    }

    public void setSelectedService(ServiceDTO selectedService)
    {
        this.selectedService = selectedService;
    }

    public String[] getOpenedAppointmentsDescriptions()
    {
        return openedAppointmentsDescriptions;
    }

    public void setOpenedAppointmentsDescriptions(String[] openedAppointmentsDescriptions)
    {
        this.openedAppointmentsDescriptions = openedAppointmentsDescriptions;
    }

    public String getCurrentStepDescription()
    {
        return currentStepDescription;
    }

    public void setCurrentStepDescription(String currentStepDescription)
    {
        this.currentStepDescription = currentStepDescription;
    }

    public UUID getCurrentMaintenanceID()
    {
        return currentMaintenanceID;
    }

    public void setCurrentMaintenanceID(UUID currentMaintenanceID)
    {
        this.currentMaintenanceID = currentMaintenanceID;
    }

    public int getSelectedMaintenanceIndex()
    {
        return selectedMaintenanceIndex;
    }

    public void setSelectedMaintenanceIndex(int selectedMaintenanceIndex)
    {
        this.selectedMaintenanceIndex = selectedMaintenanceIndex;
    }
}