package com.filipeduraes.workshop.client.viewmodel.maintenance;

public enum ServiceFilterType
{
    NONE("Nenhum"),
    CLIENT("Cliente"),
    DESCRIPTION_PATTERN("Descricao");

    private final String displayName;

    ServiceFilterType(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
