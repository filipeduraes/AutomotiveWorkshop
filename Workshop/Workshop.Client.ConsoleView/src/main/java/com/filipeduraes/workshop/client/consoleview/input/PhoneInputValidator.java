// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Validador de entrada para números de telefone.
 * Verifica se o número possui formato válido e fornece formatação padronizada.
 *
 * @author Filipe Durães
 */
public class PhoneInputValidator implements IInputValidator
{
    /**
     * Valida se a entrada corresponde a um número de telefone válido.
     *
     * @param input texto a ser validado
     * @return true se o número de telefone é válido, false caso contrário
     */
    @Override
    public boolean validate(String input)
    {
        String digits = input.replaceAll("\\D", "");

        return digits.length() >= 10 && digits.length() <= 11;
    }

    /**
     * Obtém a mensagem de erro para entrada inválida.
     *
     * @return mensagem descrevendo o formato esperado do telefone
     */
    @Override
    public String getErrorMessage()
    {
        return "Telefone inválido. Insira DDD + número com 10 ou 11 dígitos.";
    }

    /**
     * Formata um número de telefone válido no padrão (XX) XXXX-XXXX ou (XX) XXXXX-XXXX.
     *
     * @param validInput número de telefone válido a ser formatado
     * @return número de telefone formatado
     */
    @Override
    public String formatValidInput(String validInput)
    {
        int divisorIndex = validInput.length() == 11 ? 7 : 6;

        String ddd = validInput.substring(0, 2);
        String firstPart = validInput.substring(2, divisorIndex);
        String secondPart = validInput.substring(divisorIndex);

        return String.format("(%s) %s-%s", ddd, firstPart, secondPart);
    }
}