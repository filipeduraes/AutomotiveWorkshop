package com.filipeduraes.workshop.client.viewmodel.maintenance;

import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.utils.Observer;

public class ServiceViewModel extends EntityViewModel<ServiceOrderDTO>
{
    public final Observer OnRegisterAppointmentRequest = new Observer();
    public final Observer OnStartStepRequest = new Observer();
    public final Observer OnFinishStepRequest = new Observer();

    private ServiceQueryType queryType = ServiceQueryType.GENERAL;
    private ServiceFilterType filterType = ServiceFilterType.NONE;
    private String descriptionQueryPattern;

    private String[] servicesDescriptions;
    private String currentStepDetailedDescription;
    private String currentStepShortDescription;
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


    public void setDescriptionQueryPattern(String pattern)
    {
        descriptionQueryPattern = pattern;
    }

    public String getDescriptionQueryPattern()
    {
        return descriptionQueryPattern;
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