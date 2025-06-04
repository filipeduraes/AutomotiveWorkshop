package com.filipeduraes.workshop.core;

import java.util.UUID;

public class WorkshopEntity
{
    private UUID id;

    public void assignID(UUID newID)
    {
        id = newID;
    }

    public UUID getID()
    {
        return id;
    }
}
