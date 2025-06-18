package com.filipeduraes.workshop.client.consoleview.input;

public class YearInputValidator implements IInputValidator
{
    @Override
    public boolean validate(String input)
    {
        boolean hasFourDigits = input.length() == 4;
        boolean isIntegerNumber = input.matches("\\d+");

        return hasFourDigits && isIntegerNumber;
    }

    @Override
    public String getErrorMessage()
    {
        return "Ano inserido invalido. Use o formato YYYY, com apenas numeros.";
    }

    @Override
    public String formatValidInput(String validInput)
    {
        return validInput;
    }
}
