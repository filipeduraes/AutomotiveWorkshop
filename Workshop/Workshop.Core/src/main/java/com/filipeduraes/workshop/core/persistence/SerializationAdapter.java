// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import com.google.gson.TypeAdapter;

/**
 * Grupo que mantém informações de serialização e deserialização para um tipo específico.
 * Armazena o tipo da classe e seus adaptadores de serialização/deserialização correspondentes.
 *
 * @author Filipe Durães
 */
public class SerializationAdapter
{
    private final Class<?> type;
    private final TypeAdapter<?> adapter;

    /**
     * Inicializa um novo grupo de adaptadores de serialização.
     *
     * @param type O tipo da classe a ser serializada/deserializada
     * @param serializer O adaptador de serialização JSON para o tipo especificado
     */
    public SerializationAdapter(Class<?> type, TypeAdapter<?> serializer)
    {
        this.type = type;
        this.adapter = serializer;
    }

    /**
     * Retorna o tipo da classe vinculada a este grupo de serialização.
     *
     * @return O tipo da classe associada
     */
    public Class<?> getType()
    {
        return type;
    }

    /**
     * Retorna o adaptador de serialização JSON vinculado a este grupo.
     *
     * @return O adaptador de serialização JSON
     */
    public TypeAdapter<?> getAdapter()
    {
        return adapter;
    }
}
