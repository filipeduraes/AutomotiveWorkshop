package com.filipeduraes.workshop.client.consoleview.input;

public class CPFInputValidator implements IInputValidator
{
    @Override
    public boolean validate(String input)
    {
        String numberOnlyCPF = input.replaceAll("\\D", "");

        if(numberOnlyCPF.length() != 11 || numberOnlyCPF.chars().distinct().count() == 1)
        {
            return false;
        }

        return isFirstDigitValid(numberOnlyCPF) && isSecondDigitValid(numberOnlyCPF);
    }

    @Override
    public String getErrorMessage()
    {
        return "CPF inválido. Insira um número válido com 11 dígitos.";
    }

    @Override
    public String formatValidInput(String validInput)
    {
        return maskCPF(validInput);
    }

    private static String maskCPF(String cpf)
    {
        String numberOnlyCPF = cpf.replaceAll("\\D", ""); // Substitui qualquer caractere não numérico por uma string vazia

        if(numberOnlyCPF.length() != 11)
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

        for(int i = 0; i < count; i++)
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

        if(remainder == 10)
        {
            remainder = 0;
        }

        return remainder == digit;
    }
}