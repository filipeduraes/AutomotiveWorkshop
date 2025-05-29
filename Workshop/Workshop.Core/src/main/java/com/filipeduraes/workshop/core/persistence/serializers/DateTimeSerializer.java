// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 *
 * @author Filipe Durães
 */
public class DateTimeSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime>
{
    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsc)
    {
        final String formattedDate = localDateTime.toString();
        return new JsonPrimitive(formattedDate);
    }

    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jdc) throws JsonParseException 
    {
        final String formattedDate = jsonElement.getAsString();
        final LocalDateTime date = LocalDateTime.parse(formattedDate);

        return date;
    }
}