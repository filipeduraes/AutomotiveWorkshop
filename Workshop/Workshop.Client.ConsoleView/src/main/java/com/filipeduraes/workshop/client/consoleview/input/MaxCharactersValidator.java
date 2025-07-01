// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Validador que verifica se uma entrada de texto não excede um número máximo de caracteres.
 * Útil para limitar o tamanho de campos de texto na interface do usuário.
 *
 * @author Filipe Durães
 */
public class MaxCharactersValidator implements IInputValidator
{
    private final int maxCharacters;

    /**
     * Cria um novo validador com o número máximo de caracteres especificado.
     * Se o valor fornecido for negativo, será definido como 0.
     *
     * @param maxCharacters número máximo de caracteres permitidos
     */
    public MaxCharactersValidator(int maxCharacters)
    {
        this.maxCharacters = Math.max(maxCharacters, 0);
    }

    /**
     * Valida se a entrada não excede o número máximo de caracteres.
     *
     * @param input texto a ser validado
     * @return true se o texto não exceder o limite, false caso contrário
     */
    @Override
    public boolean validate(String input)
    {
        return input.length() <= maxCharacters;
    }

    /**
     * Obtém a mensagem de erro para entradas que excedem o limite de caracteres.
     *
     * @return mensagem informando o tamanho máximo permitido
     */
    @Override
    public String getErrorMessage()
    {
        return String.format("Texto digitado muito grande. Tamanho maximo: %d", maxCharacters);
    }

    /**
     * Formata uma entrada válida (não excede o limite de caracteres).
     *
     * @param validInput texto válido a ser formatado
     * @return texto formatado (mantém o valor original)
     */
    @Override
    public String formatValidInput(String validInput)
    {
        return validInput;
    }
}
