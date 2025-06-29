// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

import java.util.UUID;

/**
 * Representa um elevador automotivo para manutenção de veículos.
 * Gerencia o estado de uso e disponibilidade do elevador.
 *
 * @author Filipe Durães
 */
public class Elevator
{
    private ElevatorType elevatorType = ElevatorType.GENERAL_USE;
    private UUID userID;
    private boolean isAvailable = true;

    /**
     * Cria um novo elevador do tipo especificado.
     *
     * @param elevatorType tipo do elevador a ser criado
     */
    public Elevator(ElevatorType elevatorType)
    {
        this.elevatorType = elevatorType;
    }

    /**
     * Obtém o tipo do elevador.
     *
     * @return tipo do elevador
     */
    public ElevatorType getElevatorType()
    {
        return elevatorType;
    }

    /**
     * Verifica se o elevador está disponível para uso.
     *
     * @return true se o elevador estiver disponível, false caso contrário
     */
    public boolean getIsAvailable()
    {
        return isAvailable;
    }

    /**
     * Verifica se o elevador está disponível e corresponde ao tipo especificado.
     *
     * @param type o tipo de elevador a ser verificado
     * @return true se o elevador estiver disponível e corresponder ao tipo especificado, false caso contrário
     */
    public boolean isAvailableAndIsOfType(ElevatorType type)
    {
        return getElevatorType() == type && getIsAvailable();
    }

    /**
     * Inicia o uso do elevador por um usuário específico.
     *
     * @param userID identificador único do usuário que utilizará o elevador
     */
    public void startUsing(UUID userID)
    {
        this.userID = userID;
        isAvailable = false;
    }

    /**
     * Finaliza o uso atual do elevador, tornando-o disponível novamente.
     */
    public void stopUsing()
    {
        userID = null;
        isAvailable = true;
    }

    /**
     * Obtém o identificador do usuário atual do elevador.
     *
     * @return identificador único do usuário atual ou null se não estiver em uso
     */
    public UUID getUserID()
    {
        return userID;
    }
}
