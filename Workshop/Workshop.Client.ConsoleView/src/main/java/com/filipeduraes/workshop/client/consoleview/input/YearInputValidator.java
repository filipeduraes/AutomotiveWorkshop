// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Validador para entradas de ano no sistema.
 * Verifica se a entrada corresponde ao formato de ano válido (YYYY).
 *
 * @author Filipe Durães
 */
public class YearInputValidator implements IInputValidator
{
    /**
     * Valida se a entrada corresponde a um ano válido.
     *
     * @param input texto a ser validado
     * @return true se a entrada for um ano válido, false caso contrário
     */
    @Override
    public boolean validate(String input)
    {
        boolean hasFourDigits = input.length() == 4;
        boolean isIntegerNumber = input.matches("\\d+");

        return hasFourDigits && isIntegerNumber;
    }

    /**
     * Obtém a mensagem de erro para entradas inválidas.
     *
     * @return mensagem descrevendo o formato esperado para o ano
     */
    @Override
    public String getErrorMessage()
    {
        return "Ano inserido invalido. Use o formato YYYY, com apenas numeros.";
    }

    /**
     * Formata uma entrada válida de ano.
     *
     * @param validInput texto do ano validado
     * @return texto do ano formatado
     */
    @Override
    public String formatValidInput(String validInput)
    {
        return validInput;
    }
}
