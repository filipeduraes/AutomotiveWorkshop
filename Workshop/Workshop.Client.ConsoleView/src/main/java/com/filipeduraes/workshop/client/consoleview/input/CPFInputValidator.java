// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Validador de entrada para CPF (Cadastro de Pessoas Físicas).
 * Verifica se um CPF é válido e fornece formatação adequada para exibição.
 *
 * @author Filipe Durães
 */
public class CPFInputValidator implements IInputValidator
{
    /**
     * Valida se uma string representa um CPF válido.
     *
     * @param input String contendo o CPF a ser validado
     * @return true se o CPF for válido, false caso contrário
     */
    @Override
    public boolean validate(String input)
    {
        String numberOnlyCPF = input.replaceAll("\\D", "");

        if (numberOnlyCPF.length() != 11 || numberOnlyCPF.chars().distinct().count() == 1)
        {
            return false;
        }

        return isFirstDigitValid(numberOnlyCPF) && isSecondDigitValid(numberOnlyCPF);
    }

    /**
     * Obtém a mensagem de erro para CPF inválido.
     *
     * @return mensagem de erro para CPF inválido
     */
    @Override
    public String getErrorMessage()
    {
        return "CPF invalido. Insira um numero válido com 11 digitos.";
    }

    /**
     * Formata um CPF válido para exibição, mascarando os primeiros dígitos.
     *
     * @param validInput CPF válido a ser formatado
     * @return CPF formatado com máscara
     */
    @Override
    public String formatValidInput(String validInput)
    {
        return maskCPF(validInput);
    }

    private static String maskCPF(String cpf)
    {
        String numberOnlyCPF = cpf.replaceAll("\\D", ""); // Substitui qualquer caractere não numérico por uma string vazia

        if (numberOnlyCPF.length() != 11)
        {
            return "";
        }

        String thirdPart = numberOnlyCPF.substring(6, 9);
        String fourthPart = numberOnlyCPF.substring(9);
        return String.format("XXX.XXX.%s-%s", thirdPart, fourthPart);
    }

    private static boolean isFirstDigitValid(String numberOnlyCPF)
    {
        int sum = generateCPFValidationSum(numberOnlyCPF, 10, 9);
        return validateSumWithDigit(sum, Character.getNumericValue(numberOnlyCPF.charAt(9)));
    }

    private static boolean isSecondDigitValid(String numberOnlyCPF)
    {
        int sum = generateCPFValidationSum(numberOnlyCPF, 11, 10);
        return validateSumWithDigit(sum, Character.getNumericValue(numberOnlyCPF.charAt(10)));
    }

    private static int generateCPFValidationSum(String numberOnlyCPF, int startMultiplier, int count)
    {
        int sum = 0;

        for (int i = 0; i < count; i++)
        {
            char currentDigit = numberOnlyCPF.charAt(i);
            int numericDigit = Character.getNumericValue(currentDigit);

            sum += numericDigit * (startMultiplier - i);
        }

        return sum;
    }

    private static boolean validateSumWithDigit(int sum, int digit)
    {
        int remainder = (sum * 10) % 11;

        if (remainder == 10)
        {
            remainder = 0;
        }

        return remainder == digit;
    }
}