// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

/**
 * Representa os diferentes tipos de registro de ponto de funcionários da oficina.
 * Cada tipo possui um nome de exibição associado para apresentação ao usuário.
 *
 * @author Filipe Durães
 */
public enum ClockInTypeDTO
{
    /**
     * Registro de entrada no expediente de trabalho.
     */
    IN("Comecar expediente"),
    
    /**
     * Registro de início de pausa durante o expediente.
     */
    START_BREAK("Iniciar pausa"),
    
    /**
     * Registro de retorno da pausa para o expediente.
     */
    END_BREAK("Voltar da pausa"),
    
    /**
     * Registro de saída do expediente de trabalho.
     */
    OUT("Finalizar expediente");

    private final String displayName;

    /**
     * Constrói um ClockInTypeDTO com o nome de exibição especificado.
     *
     * @param displayName o nome de exibição do tipo de registro
     */
    ClockInTypeDTO(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * Retorna o nome de exibição do tipo de registro de ponto.
     *
     * @return nome de exibição do tipo
     */
    @Override
    public String toString()
    {
        return displayName;
    }
}
