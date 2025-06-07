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
}