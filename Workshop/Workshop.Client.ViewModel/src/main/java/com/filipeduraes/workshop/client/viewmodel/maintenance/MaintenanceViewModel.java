package com.filipeduraes.workshop.client.viewmodel.maintenance;

import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.UUID;

public class MaintenanceViewModel
{
    public final Observer OnServicesRequest = new Observer();
    public final Observer OnDetailedServiceInfoRequest = new Observer();
    public final Observer OnRegisterAppointmentRequest = new Observer();
    public final Observer OnStartStepRequest = new Observer();
    public final Observer OnFinishStepRequest = new Observer();
    public final Observer OnDeleteRequest = new Observer();

    private ServiceQueryType queryType = ServiceQueryType.GENERAL;
    private ServiceFilterType filterType = ServiceFilterType.NONE;
    private String descriptionQueryPattern;

    private ServiceOrderDTO selectedService;
    private String[] servicesDescriptions;
    private String currentStepDetailedDescription;
    private String currentStepShortDescription;
    private UUID currentMaintenanceID;
    private int selectedMaintenanceIndex = -1;
    private boolean wasRequestSuccessful = false;


    public ServiceQueryType getQueryType()
    {
        return queryType;
    }

    public void setQueryType(ServiceQueryType queryType)
    {
        this.queryType = queryType;
    }


    public ServiceFilterType getFilterType()
    {
        return filterType;
    }

    public void setFilterType(ServiceFilterType filterType)
    {
        this.filterType = filterType;
    }


    public ServiceOrderDTO getSelectedService()
    {
        return selectedService;
    }

    public void setSelectedService(ServiceOrderDTO selectedService)
    {
        this.selectedService = selectedService;
    }

    
    public String[] getServicesDescriptions()
    {
        return servicesDescriptions;
    }

    public void setServicesDescriptions(String[] servicesDescriptions)
    {
        this.servicesDescriptions = servicesDescriptions;
    }


    public String getCurrentStepShortDescription()
    {
        return currentStepShortDescription;
    }

    public void setCurrentStepShortDescription(String currentStepShortDescription)
    {
        this.currentStepShortDescription = currentStepShortDescription;
    }


    public String getCurrentStepDetailedDescription()
    {
        return currentStepDetailedDescription;
    }

    public void setCurrentStepDetailedDescription(String currentStepDetailedDescription)
    {
        this.currentStepDetailedDescription = currentStepDetailedDescription;
    }


    public UUID getCurrentMaintenanceID()
    {
        return currentMaintenanceID;
    }


    public void setDescriptionQueryPattern(String pattern)
    {
        descriptionQueryPattern = pattern;
    }

    public String getDescriptionQueryPattern()
    {
        return descriptionQueryPattern;
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

    public boolean getWasRequestSuccessful()
    {
        return wasRequestSuccessful;
    }

    public void setWasRequestSuccessful(boolean wasRequestSuccessful)
    {
        this.wasRequestSuccessful = wasRequestSuccessful;
    }

    public void resetQuery()
    {
        queryType = ServiceQueryType.GENERAL;
        filterType = ServiceFilterType.NONE;
        descriptionQueryPattern = "";
    }
}