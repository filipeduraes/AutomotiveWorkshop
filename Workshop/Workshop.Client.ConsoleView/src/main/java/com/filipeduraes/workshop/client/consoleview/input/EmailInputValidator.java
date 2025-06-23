// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Validador de entrada para endereços de email.
 * Verifica se uma string fornecida representa um endereço de email válido.
 *
 * @author Filipe Durães
 */
public class EmailInputValidator implements IInputValidator
{
    /**
     * Valida se a entrada representa um endereço de email válido.
     *
     * @param input String a ser validada
     * @return true se for um email válido, false caso contrário
     */
    @Override
    public boolean validate(String input)
    {
        return input != null && input.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    /**
     * Retorna a mensagem de erro para entradas inválidas.
     *
     * @return mensagem descrevendo o formato esperado para o email
     */
    @Override
    public String getErrorMessage()
    {
        return "Email inválido. Use o formato usuario@dominio.com";
    }

    /**
     * Formata uma entrada válida de email.
     *
     * @param validInput email válido a ser formatado
     * @return email formatado
     */
    @Override
    public String formatValidInput(String validInput)
    {
        return validInput;
    }
}