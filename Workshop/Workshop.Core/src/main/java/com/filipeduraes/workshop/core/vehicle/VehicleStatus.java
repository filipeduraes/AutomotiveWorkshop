// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.vehicle;

/**
 * Representa os diferentes estados possíveis de um veículo durante
 * o processo de manutenção na oficina.
 *
 * @author Filipe Durães
 */
public enum VehicleStatus
{
    /**
     * Veículo foi recebido na oficina
     */
    RECEIVED,

    /**
     * Veículo está sendo inspecionado
     */
    IN_INSPECTION,

    /**
     * Veículo aguarda início da manutenção
     */
    WAITING_MAINTENANCE,

    /**
     * Veículo está em processo de manutenção
     */
    UNDER_MAINTENANCE,

    /**
     * Manutenção concluída, veículo pronto para entrega
     */
    READY_FOR_DELIVERY,

    /**
     * Veículo já foi entregue ao cliente
     */
    DELIVERED
}
