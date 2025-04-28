// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 *
 * @author Filipe Durães
 */
public class SerializationAdapterGroup
{
    private final Class<?> type;
    private final JsonSerializer<?> serializer;
    private final JsonDeserializer<?> deserializer;
            
    public SerializationAdapterGroup(Class<?> type, JsonSerializer<?> serializer, JsonDeserializer<?> deserializer)
    {
        this.type = type;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }
    
    public Class<?> getType()
    {
        return type;
    }

    public JsonSerializer<?> getSerializer() 
    {
        return serializer;
    }

    public JsonDeserializer<?> getDeserializer() 
    {
        return deserializer;
    }  
}
