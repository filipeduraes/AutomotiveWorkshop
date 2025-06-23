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
}