// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

/**
 * Representa os tipos de requisições possíveis relacionadas a clientes no sistema.
 * Usado para controlar o fluxo de operações envolvendo dados de clientes.
 *
 * @author Filipe Durães
 */
public enum ClientRequest
{
    /**
     * Nenhuma requisição ativa/pendente
     */
    NONE,

    /**
     * Requisição para registrar um novo cliente
     */
    REGISTER_CLIENT,

    /**
     * Requisição para buscar clientes cadastrados
     */
    SEARCH_CLIENTS,

    /**
     * Requisição para carregar dados de um cliente específico
     */
    LOAD_CLIENT_DATA
}
