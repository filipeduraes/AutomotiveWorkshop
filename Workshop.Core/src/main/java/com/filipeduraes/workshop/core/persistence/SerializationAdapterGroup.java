// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

/**
 *
 * @author Filipe Durães
 */
public record SerializationAdapterGroup(Class<?> type, JsonSerializer<?> serializer, JsonDeserializer<?> deserializer) 
{
}
