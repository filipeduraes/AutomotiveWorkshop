// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDateTime;
import java.util.Comparator;

public class ServiceOrderAppointmentDateComparator implements Comparator<ServiceOrder>
{
    @Override
    public int compare(ServiceOrder firstServiceOrder, ServiceOrder secondServiceOrder)
    {
        ServiceStep fistAppointmentStep = firstServiceOrder.getSteps().get(0);
        ServiceStep secondAppointmentStep = secondServiceOrder.getSteps().get(0);

        LocalDateTime firstCreationDate = fistAppointmentStep.getStartDate();
        LocalDateTime secondCreationDate = secondAppointmentStep.getStartDate();

        if(firstCreationDate.isBefore(secondCreationDate))
        {
            return -1;
        }
        else if(firstCreationDate.isAfter(secondCreationDate))
        {
            return 1;
        }

        return 0;
    }
}
