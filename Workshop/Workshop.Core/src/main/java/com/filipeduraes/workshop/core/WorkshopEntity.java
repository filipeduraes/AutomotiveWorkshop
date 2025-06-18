package com.filipeduraes.workshop.core;

import java.util.UUID;

/**
 * Base para todas as entidades que são identificáveis por um ID.
 */
public class WorkshopEntity
{
    private UUID id;

    /**
     * Atribui um identificador único à entidade.
     *
     * @param newID novo identificador UUID da entidade
     */
    public void assignID(UUID newID)
    {
        id = newID;
    }

    /**
     * Obtém o identificador único da entidade.
     *
     * @return identificador UUID da entidade
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Verifica se um ID é igual ao ID da entidade.
     * @param otherID ID a ser comparado
     * @return {@code true} se forem iguais, {@code false} se forem diferentes
     */
    public boolean matchID(UUID otherID)
    {
        return id.equals(otherID);
    }
}
