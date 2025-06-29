// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.input;

import com.filipeduraes.workshop.utils.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Utiliza um buffered reader para ler as entradas do System.in.
 * Oferece métodos para leitura e validação de diferentes tipos de entrada do usuário,
 * incluindo texto, números, confirmações e seleções de lista.
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
        if (!message.isBlank())
        {
            System.out.println(message);
        }

        return readLine();
    }

    /**
     * Lê um número inteiro da entrada do console.
     *
     * @return número inteiro inserido pelo usuário
     */
    public static int readLineInteger()
    {
        return readLineInteger("");
    }

    /**
     * Lê um número inteiro da entrada do console acima de um valor mínimo.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @param minValue valor mínimo que o input pode receber
     * @return número inteiro inserido pelo usuário
     */
    public static int readLineInteger(String message, int minValue)
    {
        return readLineInteger(message, minValue, Integer.MAX_VALUE);
    }

    /**
     * Lê um número inteiro da entrada do console em uma faixa de valores.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @param minValue valor mínimo que o input pode receber
     * @param maxValue valor máximo que o input pode receber
     * @return número inteiro inserido pelo usuário
     */
    public static int readLineInteger(String message, int minValue, int maxValue)
    {
        int input = readLineInteger(message);

        while (input < minValue || input > maxValue)
        {
            System.out.printf("O valor inserido deve estar entre %d e %d%n", minValue, maxValue);
            input = readLineInteger(message);
        }

        return input;
    }

    /**
     * Exibe uma mensagem e lê um número inteiro da entrada do console.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @return número inteiro inserido pelo usuário
     */
    public static int readLineInteger(String message)
    {
        int readInteger;

        while (true)
        {
            try
            {
                String input = readLine(message);
                input = input.trim();

                readInteger = Integer.parseInt(input);
                break;
            }
            catch (Exception e)
            {
                System.out.println("Insira apenas numeros inteiros. Tente novamente.");
            }
        }

        return readInteger;
    }

    /**
     * Exibe uma mensagem e lê um número decimal (BigDecimal) da entrada do console.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @return BigDecimal inserido pelo usuário
     */
    public static BigDecimal readLinePositiveBigDecimal(String message)
    {
        BigDecimal readBigDecimal;

        while (true)
        {
            try
            {
                String input = readLine(message);
                input = input.trim();
                input = input.replace(',', '.');

                if(input.contains("-") || input.isEmpty())
                {
                    continue;
                }

                readBigDecimal = new BigDecimal(input);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Entrada invalida. Por favor, insira um valor numerico (ex: 123.45 ou 123,45).");
            }
        }

        return readBigDecimal;
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
            System.err.printf("Erro ao ler entrada do usuiario: %s%n", e.getMessage());
            e.printStackTrace(System.out);
            return "";
        }
    }

    /**
     * Lê e valida uma linha de texto da entrada do console.
     *
     * @param inputValidator validador para verificar a entrada
     * @return texto validado e formatado
     */
    public static String readValidatedLine(IInputValidator inputValidator)
    {
        return readValidatedLine("", inputValidator);
    }

    /**
     * Lê e valida uma linha de texto da entrada do console.
     *
     * @param message mensagem a ser exibida antes da leitura
     * @param inputValidator validador para verificar a entrada
     * @return texto validado e formatado
     */
    public static String readValidatedLine(String message, IInputValidator inputValidator)
    {
        String input = readLine(message);

        while (!inputValidator.validate(input))
        {
            System.out.println(inputValidator.getErrorMessage());
            input = readLine(message);
        }

        return inputValidator.formatValidInput(input);
    }

    /**
     * Exibe uma lista numerada de opções e lê a escolha do usuário.
     *
     * @param message mensagem a ser exibida antes das opções
     * @param options array com as opções disponíveis
     * @return índice da opção escolhida pelo usuário
     */
    public static <T> int readOptionFromList(String message, T[] options)
    {
        return readOptionFromList(message, options, false);
    }

    /**
     * Exibe uma lista numerada de opções e lê a escolha do usuário.
     *
     * @param message mensagem a ser exibida antes das opções
     * @param options array com as opções disponíveis
     * @return índice da opção escolhida pelo usuário
     */
    public static <T> int readOptionFromList(String message, T[] options, boolean showCancelOption)
    {
        if(options == null || options.length == 0)
        {
            return 0;
        }

        return readOptionFromList(message, TextUtils.convertToStringArray(options), showCancelOption);
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

        if (showCancelOption)
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

    /**
     * Exibe uma mensagem e lê uma confirmação (sim/não) do usuário.
     *
     * @param message mensagem a ser exibida antes da confirmação
     * @return true se confirmado, false caso contrário
     */
    public static boolean readConfirmation(String message)
    {
        message += " (s/n)";
        System.out.println(message);
        return readConfirmation();
    }

    /**
     * Lê uma confirmação (sim/não) do usuário.
     *
     * @return true se confirmado, false caso contrário
     */
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

            if (!isValidAnswer)
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
