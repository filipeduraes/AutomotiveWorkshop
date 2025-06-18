package com.filipeduraes.workshop.client.dtos;

public class ServiceStepDTO
{
    private final String shortDescription;
    private final String detailedDescription;
    private final String startDate;
    private final String endDate;
    private final String owner;
    private final boolean hasBeenFinished;

    public ServiceStepDTO(String shortDescription, String detailedDescription, String startDate, String endDate, String owner, boolean hasBeenFinished)
    {
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.hasBeenFinished = hasBeenFinished;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public String getDetailedDescription()
    {
        return detailedDescription;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public String getOwner()
    {
        return owner;
    }

    public boolean getHasBeenFinished()
    {
        return hasBeenFinished;
    }

    @Override
    public String toString()
    {
        if(getHasBeenFinished())
        {
            return String.format
            (
                "   > Descricao Curta: %s%n   > Descricao Detalhada: %s%n   > Responsavel: %s%n   > Data de Inicio: %s%n   > Data de Termino: %s%n   > Estado: Finalizada%n",
                getShortDescription(),
                getDetailedDescription(),
                getOwner(),
                getStartDate(),
                getEndDate()
            );
        }

        return String.format
        (
            "   > Descricao indisponivel%n   > Responsavel: %s%n   > Data de Inicio: %s%n   > Estado: Em andamento%n",
            getOwner(),
            getStartDate()
        );
    }
}