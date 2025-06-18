package com.filipeduraes.workshop.utils;

public final class TextUtils
{
    private TextUtils(){}

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
}
