package com.filipeduraes.workshop.client.consoleview.input;

public class EmailInputValidator implements IInputValidator
{
    @Override
    public boolean validate(String input)
    {
        return input != null && input.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    @Override
    public String getErrorMessage()
    {
        return "Email inválido. Use o formato usuario@dominio.com";
    }
}