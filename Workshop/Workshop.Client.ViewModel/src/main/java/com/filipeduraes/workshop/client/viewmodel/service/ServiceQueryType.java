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
     * Representa consultas por serviços que estão atualmente abertos.
     */
    OPENED("Servicos Abertos"),

    /**
     * Representa consultas por serviços associados ao usuário logado.
     */
    USER("Servicos do Usuario"),

    /**
     * Representa consultas gerais por serviços, sem filtros específicos.
     */
    GENERAL("Servicos Gerais"),

    /**
     * Representa consultas por serviços já finalizados.
     */
    CLOSED_IN_CURRENT_MONTH("Servicos Finalizados no Mes");

    private final String displayName;

    ServiceQueryType(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
