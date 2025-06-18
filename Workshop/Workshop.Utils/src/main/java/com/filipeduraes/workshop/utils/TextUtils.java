package com.filipeduraes.workshop.utils;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public final class TextUtils
{
    private TextUtils(){}

    /**
     * Torna maiúscula a primeira letra de uma string.
     * @param input String que será modificada
     * @return String com a primeira letra capitalizada
     */
    public static String capitalizeFirstLetter(String input)
    {
        if(input.isEmpty())
        {
            return "";
        }

        char firstLetter = input.charAt(0);
        char upperCaseFirstLetter = Character.toUpperCase(firstLetter);
        return upperCaseFirstLetter + input.substring(1);
    }

    public static String formatDate(LocalDateTime date)
    {
        if(date == null)
        {
            return "";
        }

        String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pt-br"));
        String formattedDate = String.format("%d %s %02d:%02d", date.getDayOfMonth(), monthName, date.getHour(), date.getMinute());

        return formattedDate;
    }
}
