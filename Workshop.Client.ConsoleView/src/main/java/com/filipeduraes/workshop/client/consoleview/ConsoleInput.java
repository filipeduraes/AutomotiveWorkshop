// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
            return GetInstance().reader.readLine();
        }
        catch (IOException e)
        {
            System.err.println(String.format("Erro ao ler entrada do usuário: %s", e.getMessage()));
            e.printStackTrace(System.out);
            return "";
        }
    }
    
    private static ConsoleInput GetInstance()
    {
        if(inputInstance == null)
        {
            inputInstance = new ConsoleInput();
        }
        
        return inputInstance;
    }
}
