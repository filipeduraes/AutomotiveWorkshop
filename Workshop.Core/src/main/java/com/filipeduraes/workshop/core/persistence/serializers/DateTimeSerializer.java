// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence.serializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 *
 * @author Filipe Durães
 */
public class DateTimeSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate>
{
    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsc) 
    {
        formattedDate = localDate.toString();
        return new JsonPrimitive(formattedDate);
    }
    private String formattedDate;

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jdc) throws JsonParseException 
    {
        final String formattedDate = jsonElement.getAsString();
        final LocalDate date = LocalDate.parse(formattedDate);
        
        return date;
    }
}