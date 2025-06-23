// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar os estados e dados relacionados aos veículos no sistema.
 * Esta classe mantém o estado da interface do usuário e coordena as operações
 * relacionadas aos veículos no sistema da oficina.
 *
 * @author Filipe Durães
 */
public class VehicleViewModel extends EntityViewModel<VehicleDTO>
{
    /**
     * Observer que notifica quando há uma solicitação de registro de veículo.
     */
    public final Observer OnVehicleRegistrationRequest = new Observer();

    /**
     * Indica se a última requisição foi bem-sucedida.
     */
    private boolean wasRequestSuccessful = false;


    /**
     * Obtém o status da última requisição.
     *
     * @return {@code true} se a última requisição foi bem-sucedida, {@code false} caso contrário
     */
    public boolean getWasRequestSuccessful()
    {
        return wasRequestSuccessful;
    }

    /**
     * Define o status da última requisição.
     *
     * @param wasRequestSuccessful true se a requisição foi bem-sucedida, false caso contrário
     */
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