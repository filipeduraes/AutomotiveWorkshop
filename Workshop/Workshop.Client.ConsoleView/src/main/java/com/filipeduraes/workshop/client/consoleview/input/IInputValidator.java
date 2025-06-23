// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Interface para validação de entradas de dados do usuário.
 * Define métodos para validar, formatar e fornecer mensagens de erro
 * para entradas inválidas.
 *
 * @author Filipe Durães
 */
public interface IInputValidator
{
    /**
     * Valida uma entrada fornecida pelo usuário.
     *
     * @param input A entrada a ser validada
     * @return true se a entrada é válida, false caso contrário
     */
    boolean validate(String input);

    /**
     * Obtém a mensagem de erro para a última validação falha.
     *
     * @return mensagem descrevendo o motivo da validação ter falhado
     */
    String getErrorMessage();

    /**
     * Formata uma entrada válida para o formato apropriado.
     *
     * @param validInput A entrada válida a ser formatada
     * @return entrada formatada no padrão desejado
     */
    String formatValidInput(String validInput);
}