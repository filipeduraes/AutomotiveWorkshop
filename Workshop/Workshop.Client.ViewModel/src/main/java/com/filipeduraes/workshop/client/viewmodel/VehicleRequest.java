package com.filipeduraes.workshop.client.viewmodel;

/**
 * Enumeração que representa os diferentes estados de requisição relacionados a veículos no sistema.
 *
 * @author Filipe Durães
 */
public enum VehicleRequest
{
    /**
     * Estado inicial, aguardando uma requisição.
     */
    WAITING_REQUEST,

    /**
     * Requisição para obter a lista de veículos do cliente.
     */
    REQUEST_CLIENT_VEHICLES,

    /**
     * Requisição para registrar um novo veículo.
     */
    REQUEST_VEHICLE_REGISTRATION,

    /**
     * Requisição para obter detalhes de um veículo selecionado.
     */
    REQUEST_SELECTED_VEHICLE_DETAILS,

    /**
     * Indica que a requisição foi concluída com sucesso.
     */
    REQUEST_SUCCESS,

    /**
     * Indica que a requisição falhou.
     */
    REQUEST_FAILED
}