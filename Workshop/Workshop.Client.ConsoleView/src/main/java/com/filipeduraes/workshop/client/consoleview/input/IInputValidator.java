package com.filipeduraes.workshop.client.consoleview.input;

public interface IInputValidator
{
    boolean validate(String input);
    String getErrorMessage();
}