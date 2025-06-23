package com.filipeduraes.workshop.client.consoleview.input;

public class MaxCharactersValidator implements IInputValidator
{
    private final int maxCharacters;

    public MaxCharactersValidator(int maxCharacters)
    {
        this.maxCharacters = Math.max(maxCharacters, 0);
    }

    @Override
    public boolean validate(String input)
    {
        return input.length() <= maxCharacters;
    }

    @Override
    public String getErrorMessage()
    {
        return String.format("Texto digitado muito grande. Tamanho maximo: %d", maxCharacters);
    }

    @Override
    public String formatValidInput(String validInput)
    {
        return validInput;
    }
}
