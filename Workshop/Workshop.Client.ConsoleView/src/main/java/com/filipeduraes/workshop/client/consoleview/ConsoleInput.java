// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Utiliza um buffered reader para ler as entradas do System.in
 *
 * @author Filipe Durães
 */
public class ConsoleInput
{
    private static ConsoleInput inputInstance;
    private final BufferedReader reader;

    private static final Set<String> possibleConfirmationMessages = Set.of("sim", "s", "yes", "y", "[s]im");
    private static final Set<String> possibleDenyMessages = Set.of("nao", "n", "no", "[n]ao");
    private static final String CANCEL_OPTION_MESSAGE = "X Cancelar";

    private ConsoleInput()
    {
        inputInstance = this;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Exibe uma mensagem e lê uma linha de texto da entrada do console.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @return texto inserido pelo usuário
     */
    public static String readLine(String message)
    {
        System.out.println(message);
        return readLine();
    }

    /**
     * Exibe uma mensagem e lê um número inteiro da entrada do console.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @return número inteiro inserido pelo usuário
     */
    public static int readLineInteger(String message)
    {
        System.out.println(message);
        return readLineInteger();
    }

    /**
     * Lê um número inteiro da entrada do console.
     *
     * @return número inteiro inserido pelo usuário
     */
    public static int readLineInteger()
    {
        String input = ConsoleInput.readLine();
        input = input.trim();

        try
        {
            int readInteger = Integer.parseInt(input);
            return readInteger;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    /**
     * Lê uma linha de texto da entrada do console.
     *
     * @return texto inserido pelo usuário
     */
    public static String readLine()
    {
        try
        {
            return getInstance().reader.readLine();
        }
        catch (IOException e)
        {
            System.err.println(String.format("Erro ao ler entrada do usuiario: %s", e.getMessage()));
            e.printStackTrace(System.out);
            return "";
        }
    }

    /**
     * Exibe uma lista numerada de opções e lê a escolha do usuário.
     *
     * @param message mensagem a ser exibida antes das opções
     * @param options array com as opções disponíveis
     * @return índice da opção escolhida pelo usuário
     */
    public static int readOptionFromList(String message, String[] options)
    {
        return readOptionFromList(message, options, false);
    }

    /**
     * Exibe uma lista numerada de opções e lê a escolha do usuário.
     *
     * @param message mensagem a ser exibida antes das opções
     * @param options array com as opções disponíveis
     * @param showCancelOption mostra uma opção para cancelar a ação
     * @return índice da opção escolhida pelo usuário
     */
    public static int readOptionFromList(String message, String[] options, boolean showCancelOption)
    {
        StringJoiner joiner = new StringJoiner("\n");

        for (int i = 0; i < options.length; i++)
        {
            joiner.add(String.format("> [%d] %s", i, options[i]));
        }

        if(showCancelOption)
        {
            joiner.add(String.format("> [%d] %s", options.length, CANCEL_OPTION_MESSAGE));
        }

        int input = -1;
        int realSize = showCancelOption ? options.length + 1 : options.length;

        while (input < 0 || input >= realSize)
        {
            System.out.println(joiner);

            if (message != null && !message.isEmpty())
            {
                System.out.println(message);
            }

            input = readLineInteger();
        }

        return input;
    }

    public static boolean readConfirmation(String message)
    {
        message += " (s/n)";
        System.out.println(message);
        return readConfirmation();
    }

    public static boolean readConfirmation()
    {
        String answer = readLine();
        answer = answer.trim().toLowerCase();

        boolean isValidAnswer = false;
        boolean hasConfirmationAnswer = false;

        while (!isValidAnswer)
        {
            hasConfirmationAnswer = possibleConfirmationMessages.contains(answer);
            isValidAnswer = hasConfirmationAnswer || possibleDenyMessages.contains(answer);

            if(!isValidAnswer)
            {
                System.out.println("Resposta invalida, insira apenas [s]im ou [n]ao");
            }
        }

        return hasConfirmationAnswer;
    }

    private static ConsoleInput getInstance()
    {
        if (inputInstance == null)
        {
            inputInstance = new ConsoleInput();
        }

        return inputInstance;
    }
}
