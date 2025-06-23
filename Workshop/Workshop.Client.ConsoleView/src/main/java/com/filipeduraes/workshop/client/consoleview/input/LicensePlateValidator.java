// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.input;

/**
 * Validador para placas de veículos no formato brasileiro.
 * Verifica se a placa está no formato correto e fornece formatação padronizada.
 *
 * @author Filipe Durães
 */
public class LicensePlateValidator implements IInputValidator
{
    /**
     * Valida se a string fornecida representa uma placa de veículo válida.
     *
     * @param input string a ser validada
     * @return true se a placa é válida, false caso contrário
     */
    @Override
    public boolean validate(String input)
    {
        return input.matches("^[A-Z]{3}-?[0-9][A-Z0-9][0-9]{2}");
    }

    /**
     * Retorna a mensagem de erro para quando a placa é inválida.
     *
     * @return mensagem de erro para placa inválida
     */
    @Override
    public String getErrorMessage()
    {
        return "Placa invalida. Insira uma placa no formato: ABC-1234 ou ABC-1D23";
    }

    /**
     * Formata uma placa válida para o formato padrão com hífen.
     *
     * @param validInput placa válida a ser formatada
     * @return placa formatada com hífen
     */
    @Override
    public String formatValidInput(String validInput)
    {
        String alphaNumericPlate = validInput.replaceAll("[^a-zA-Z0-9]", "");
        String firstThreeDigits = alphaNumericPlate.substring(0, 4);
        String lastFourDigits = alphaNumericPlate.substring(4);

        return String.format("%s-%s", firstThreeDigits, lastFourDigits);
    }
}
