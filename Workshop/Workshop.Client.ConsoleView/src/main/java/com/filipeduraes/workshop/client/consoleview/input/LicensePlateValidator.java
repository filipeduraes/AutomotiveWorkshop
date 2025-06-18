package com.filipeduraes.workshop.client.consoleview.input;

public class LicensePlateValidator implements IInputValidator
{
    @Override
    public boolean validate(String input)
    {
        return input.matches("^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}");
    }

    @Override
    public String getErrorMessage()
    {
        return "Placa invalida. Insira uma placa no formato: ABC-1234 ou ABC-1D23";
    }

    @Override
    public String formatValidInput(String validInput)
    {
        String alphaNumericPlate = validInput.replaceAll("[^a-zA-Z0-9]", "");
        String firstThreeDigits = alphaNumericPlate.substring(0, 4);
        String lastFourDigits = alphaNumericPlate.substring(4);

        return String.format("%s-%s", firstThreeDigits, lastFourDigits);
    }
}
