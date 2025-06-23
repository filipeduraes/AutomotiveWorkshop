// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel.service;

/**
 * Define os tipos de consulta disponíveis para serviços na oficina.
 * Cada tipo representa uma forma diferente de filtrar e buscar serviços no sistema.
 *
 * @author Filipe Durães
 */
public enum ServiceQueryType
{
    /**
     * Representa consultas por serviços que estão atualmente abertos/em andamento.
     */
    OPENED,

    /**
     * Representa consultas por serviços associados ao usuário logado.
     */
    USER,

    /**
     * Representa consultas gerais por serviços, sem filtros específicos.
     */
    GENERAL
}
