// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence.serializers;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador para serialização e deserialização de objetos LocalDateTime em formato JSON.
 * Utiliza o formato ISO_LOCAL_DATE_TIME para conversão entre string e objeto.
 *
 * @author Filipe Durães
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime>
{
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Serializa um objeto LocalDateTime para formato JSON.
     *
     * @param jsonWriter escritor JSON para output
     * @param time data e hora a ser serializada
     * @throws IOException se ocorrer erro durante a escrita
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime time) throws IOException
    {
        if (time != null)
        {
            jsonWriter.value(time.format(FORMATTER));
        }
        else
        {
            jsonWriter.nullValue();
        }
    }

    /**
     * Deserializa uma string JSON para objeto LocalDateTime.
     *
     * @param jsonReader leitor JSON para input
     * @return objeto LocalDateTime deserializado, ou null se o valor for nulo
     * @throws IOException se ocorrer erro durante a leitura
     */
    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException
    {
        if (jsonReader.peek() == com.google.gson.stream.JsonToken.NULL)
        {
            jsonReader.nextNull();
            return null;
        }

        String dateTimeString = jsonReader.nextString();
        return LocalDateTime.parse(dateTimeString, FORMATTER);
    }
}