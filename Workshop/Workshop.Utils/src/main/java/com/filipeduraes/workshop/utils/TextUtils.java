// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.Locale;

/**
 * Fornece utilidades para manipulação e formatação de texto.
 *
 * @author Filipe Durães
 */
public final class TextUtils
{
    private static final String SECTION_HEADER_SEPARATOR = "========================================================";
    private static final String SUB_SEPARATOR = "--------------------------------------------------------";

    private TextUtils()
    {
    }

    /**
     * Torna maiúscula a primeira letra de uma string.
     *
     * @param input String que será modificada
     * @return String com a primeira letra capitalizada
     */
    public static String capitalizeFirstLetter(String input)
    {
        if (input.isEmpty())
        {
            return "";
        }

        char firstLetter = input.charAt(0);
        char upperCaseFirstLetter = Character.toUpperCase(firstLetter);
        return upperCaseFirstLetter + input.substring(1);
    }

    /**
     * Formata uma data para exibição no formato "dia mês HH:mm" em português.
     *
     * @param date Data a ser formatada
     * @return String formatada com a data, ou string vazia se a data for nula
     */
    public static String formatDate(LocalDateTime date)
    {
        if (date == null)
        {
            return "";
        }

        String monthName = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pt-br"));
        String formattedDate = String.format("%d %s %02d:%02d", date.getDayOfMonth(), monthName, date.getHour(), date.getMinute());

        return formattedDate;
    }

    public static String formatMonthDate(LocalDateTime date)
    {
        if (date == null)
        {
            return "";
        }

        String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("pt-br"));
        String formattedDate = String.format("%s %04d", capitalizeFirstLetter(monthName), date.getYear());
        return formattedDate;
    }

    /**
     * Formata um valor monetário para exibição no formato da moeda real (R$).
     *
     * @param price Valor monetário a ser formatado
     * @return String formatada com o valor monetário em reais
     */
    public static String formatPrice(BigDecimal price)
    {
        Locale locale = Locale.forLanguageTag("pt-BR");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(price);
    }

    /**
     * Converte um array de qualquer tipo para um array de strings.
     *
     * @param <T> Tipo dos elementos do array de entrada
     * @param array Array a ser convertido
     * @return Array de strings com a representação textual dos elementos
     */
    public static <T> String[] convertToStringArray(T[] array)
    {
        return convertToStringArray(array, 0);
    }

    /**
     * Converte um array de qualquer tipo para um array de strings, pulando elementos iniciais.
     *
     * @param <T> Tipo dos elementos do array de entrada
     * @param array Array a ser convertido
     * @param skip Quantidade de elementos a serem pulados do início
     * @return Array de strings com a representação textual dos elementos
     */
    public static <T> String[] convertToStringArray(T[] array, int skip)
    {
        return Arrays.stream(array)
                     .skip(skip)
                     .map(Object::toString)
                     .toArray(String[]::new);
    }

    public static void appendSection(StringBuilder builder, String sectionTitle, String display)
    {
        appendSectionTitle(builder, sectionTitle);
        builder.append(display);
    }

    public static void appendSectionTitle(StringBuilder builder, String sectionTitle)
    {
        builder.append("\n").append(SECTION_HEADER_SEPARATOR).append("\n");
        builder.append(String.format("%s\n", sectionTitle));
        builder.append(SECTION_HEADER_SEPARATOR).append("\n");
    }

    public static void appendSeparator(StringBuilder builder)
    {
        builder.append(SUB_SEPARATOR).append("\n");
    }

    public static void appendSubtitle(StringBuilder builder, String subtitle)
    {
        builder.append("### ").append(subtitle).append(" ###\n");
    }

    public static void appendFirstList(StringBuilder builder, String listItem)
    {
        appendFirstList(builder, listItem, '>');
    }

    public static void appendFirstList(StringBuilder builder, String listItem, char symbol)
    {
        builder.append("  ").append(symbol).append(" ").append(listItem).append("\n");
    }

    public static void appendSecondList(StringBuilder builder, String listItem)
    {
        builder.append("      - ").append(listItem).append("\n");
    }
}
