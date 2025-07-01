// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.employee;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa um registro de ponto (clock-in) de um funcionário.
 * Mantém informações sobre o tipo de ponto, funcionário responsável
 * e momento exato do registro.
 *
 * @author Filipe Durães
 */
public class ClockIn
{
    private final ClockInType type;
    private final UUID employeeID;
    private final LocalDateTime timestamp;

    /**
     * Cria um novo registro de ponto para um funcionário.
     * O timestamp é automaticamente definido como o momento atual.
     *
     * @param type tipo do ponto (entrada, saída, início de pausa, fim de pausa)
     * @param employeeID identificador único do funcionário
     */
    public ClockIn(ClockInType type, UUID employeeID)
    {
        this.type = type;
        this.employeeID = employeeID;
        timestamp = LocalDateTime.now();
    }

    /**
     * Obtém o tipo do ponto registrado.
     *
     * @return tipo do ponto (entrada, saída, início de pausa, fim de pausa)
     */
    public ClockInType getType()
    {
        return type;
    }

    /**
     * Obtém o identificador único do funcionário que registrou o ponto.
     *
     * @return ID do funcionário
     */
    public UUID getEmployeeID()
    {
        return employeeID;
    }

    /**
     * Obtém o momento exato em que o ponto foi registrado.
     *
     * @return timestamp do registro do ponto
     */
    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }
}