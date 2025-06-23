// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel.service;

/**
 * Define os tipos de filtros disponíveis para busca de serviços na oficina.
 * Cada tipo de filtro possui um nome de exibição associado.
 *
 * @author Filipe Durães
 */
public enum ServiceFilterType
{

    /**
     * Representa ausência de filtro na busca de serviços
     */
    NONE("Nenhum"),

    /**
     * Filtro para buscar serviços por cliente
     */
    CLIENT("Cliente"),

    /**
     * Filtro para buscar serviços por padrão na descrição
     */
    DESCRIPTION_PATTERN("Descricao");

    private final String displayName;

    /**
     * Constrói um ServiceFilterType com o nome de exibição especificado.
     *
     * @param displayName O nome de exibição do filtro
     */
    ServiceFilterType(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * Retorna o nome de exibição do filtro.
     *
     * @return nome de exibição do filtro
     */
    @Override
    public String toString()
    {
        return displayName;
    }
}
