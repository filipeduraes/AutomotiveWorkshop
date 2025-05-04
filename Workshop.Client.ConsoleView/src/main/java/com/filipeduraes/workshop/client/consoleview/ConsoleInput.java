// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;

/**
 * Uses a buffered reader to read the System.in inputs
 * @author Filipe Durães
 */
public class ConsoleInput 
{
    private static ConsoleInput inputInstance;
    private final BufferedReader reader;
    
    private ConsoleInput()
    {
        inputInstance = this;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    
    public static String readLine(String message)
    {
        System.out.println(message);        
        return readLine();
    }
    
    public static int readLineInteger(String message)
    {
        System.out.println(message);
        return readLineInteger();
    }
    
    public static int readLineInteger()
    {
        String input = ConsoleInput.readLine();
        input = input.trim();
        return Integer.parseInt(input);
    }
    
    public static String readLine()
    {
        try
        {
            return getInstance().reader.readLine();
        }
        catch (IOException e)
        {
            System.err.println(String.format("Erro ao ler entrada do usuário: %s", e.getMessage()));
            e.printStackTrace(System.out);
            return "";
        }
    }
    
    public static int readOptionFromList(String message, String[] options)
    {
        StringJoiner joiner = new StringJoiner("\n");
        
        for(int i = 0; i < options.length; i++)
        {
            joiner.add(String.format("> [%d] %s", i, options[i]));
        }
        
        int input = -1;
        
        while(input < 0 || input >= options.length)
        {
            System.out.println(joiner);

            if(message != null && !message.isEmpty())
            {
                System.out.println(message);
            }

            input = readLineInteger();
        }
        
        return input;
    }
    
    private static ConsoleInput getInstance()
    {
        if(inputInstance == null)
        {
            inputInstance = new ConsoleInput();
        }
        
        return inputInstance;
    }
}
