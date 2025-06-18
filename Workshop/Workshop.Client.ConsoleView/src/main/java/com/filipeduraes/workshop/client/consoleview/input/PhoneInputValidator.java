package com.filipeduraes.workshop.client.consoleview.input;

public class PhoneInputValidator implements IInputValidator
{
    @Override
    public boolean validate(String input)
    {
        String digits = input.replaceAll("\\D", "");

        return digits.length() >= 10 && digits.length() <= 11;
    }

    @Override
    public String getErrorMessage()
    {
        return "Telefone inválido. Insira DDD + número com 10 ou 11 dígitos.";
    }

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