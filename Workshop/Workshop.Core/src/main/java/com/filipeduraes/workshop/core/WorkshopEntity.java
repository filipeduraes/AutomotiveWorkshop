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

    public boolean matchID(UUID otherID)
    {
        return id.equals(otherID);
    }
}
