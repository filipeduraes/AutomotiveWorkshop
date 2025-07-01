// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Comparador que ordena ordens de serviço por data de agendamento.
 * Utiliza a data de início da primeira etapa (agendamento) para determinar
 * a ordem cronológica das ordens de serviço.
 *
 * @author Filipe Durães
 */
public class ServiceOrderAppointmentDateComparator implements Comparator<ServiceOrder>
{
    /**
     * Compara duas ordens de serviço com base na data de agendamento.
     * Utiliza a data de início da primeira etapa de cada ordem de serviço
     * para determinar a ordem cronológica.
     *
     * @param firstServiceOrder primeira ordem de serviço a ser comparada
     * @param secondServiceOrder segunda ordem de serviço a ser comparada
     * @return valor negativo se a primeira ordem for anterior,
     *         valor positivo se a primeira ordem for posterior,
     *         zero se as ordens tiverem a mesma data de agendamento
     */
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
