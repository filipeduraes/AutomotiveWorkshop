// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 * Grupo que contém informações de serialização e deserialização para um tipo específico.
 * Mantém referências ao tipo da classe e seus serializadores/deserializadores correspondentes.
 *
 * @author Filipe Durães
 */
public class SerializationAdapterGroup
{
    private final Class<?> type;
    private final JsonSerializer<?> serializer;
    private final JsonDeserializer<?> deserializer;

    /**
     * Cria uma nova instância do grupo de adaptadores de serialização.
     *
     * @param type O tipo da classe que será serializada/deserializada
     * @param serializer O serializador JSON para o tipo especificado
     * @param deserializer O deserializador JSON para o tipo especificado
     */
    public SerializationAdapterGroup(Class<?> type, JsonSerializer<?> serializer, JsonDeserializer<?> deserializer)
    {
        this.type = type;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    /**
     * Obtém o tipo da classe associada a este grupo.
     *
     * @return O tipo da classe
     */
    public Class<?> getType()
    {
        return type;
    }

    /**
     * Obtém o serializador JSON associado a este grupo.
     *
     * @return O serializador JSON
     */
    public JsonSerializer<?> getSerializer()
    {
        return serializer;
    }

    /**
     * Obtém o deserializador JSON associado a este grupo.
     *
     * @return O deserializador JSON
     */
    public JsonDeserializer<?> getDeserializer()
    {
        return deserializer;
    }
}
