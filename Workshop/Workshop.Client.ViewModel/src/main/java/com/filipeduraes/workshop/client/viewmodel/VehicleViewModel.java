package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel responsável por gerenciar os estados e dados relacionados aos veículos no sistema.
 *
 * @author Filipe Durães
 */
public class VehicleViewModel extends EntityViewModel<VehicleDTO>
{
    public final Observer OnVehicleRegistrationRequest = new Observer();

    private boolean wasRequestSuccessful = false;


    public boolean getWasRequestSuccessful()
    {
        return wasRequestSuccessful;
    }

    public void setWasRequestSuccessful(boolean wasRequestSuccessful)
    {
        this.wasRequestSuccessful = wasRequestSuccessful;
    }

    /**
     * Obtém o veículo do cliente a partir de um índice específico.
     *
     * @param index Índice do veículo na lista
     * @return O veículo correspondente ao índice
     */
    public String getSelectedClientVehicleFromIndex(int index)
    {
        return getFoundEntitiesDescriptions().get(index);
    }
}