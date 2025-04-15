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
    
    public static String ReadLine(String message)
    {
        System.out.println(message);        
        return ReadLine();
    }
    
    public static String ReadLine()
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
