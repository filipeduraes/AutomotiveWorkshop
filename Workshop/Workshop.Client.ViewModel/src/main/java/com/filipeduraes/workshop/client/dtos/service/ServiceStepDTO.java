// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos.service;

/**
 * Representa uma etapa de serviço no sistema da oficina.
 * Esta classe mantém todas as informações sobre uma etapa específica de um serviço,
 * incluindo suas descrições, datas e estado atual.
 *
 * @author Filipe Durães
 */
public class ServiceStepDTO
{
    private final String shortDescription;
    private final String detailedDescription;
    private final String startDate;
    private final String endDate;
    private final String owner;
    private final boolean hasBeenFinished;

    /**
     * Cria uma nova instância de etapa de serviço com as informações fornecidas.
     *
     * @param shortDescription Descrição curta da etapa
     * @param detailedDescription Descrição detalhada da etapa
     * @param startDate Data de início da etapa
     * @param endDate Data de término da etapa
     * @param owner Responsável pela etapa
     * @param hasBeenFinished Indica se a etapa foi finalizada
     */
    public ServiceStepDTO(String shortDescription, String detailedDescription, String startDate, String endDate, String owner, boolean hasBeenFinished)
    {
        this.shortDescription = shortDescription;
        this.detailedDescription = detailedDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.owner = owner;
        this.hasBeenFinished = hasBeenFinished;
    }

    /**
     * Obtém a descrição curta da etapa.
     *
     * @return descrição curta da etapa
     */
    public String getShortDescription()
    {
        return shortDescription;
    }

    /**
     * Obtém a descrição detalhada da etapa.
     *
     * @return descrição detalhada da etapa
     */
    public String getDetailedDescription()
    {
        return detailedDescription;
    }

    /**
     * Obtém a data de início da etapa.
     *
     * @return data de início da etapa
     */
    public String getStartDate()
    {
        return startDate;
    }

    /**
     * Obtém a data de término da etapa.
     *
     * @return data de término da etapa
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * Obtém o responsável pela etapa.
     *
     * @return responsável pela etapa
     */
    public String getOwner()
    {
        return owner;
    }

    /**
     * Verifica se a etapa foi finalizada.
     *
     * @return true se a etapa foi finalizada, false caso contrário
     */
    public boolean getHasBeenFinished()
    {
        return hasBeenFinished;
    }

    /**
     * Retorna uma representação em string da etapa de serviço.
     * Se a etapa estiver finalizada, inclui todas as informações disponíveis.
     * Caso contrário, apresenta apenas as informações básicas.
     *
     * @return string representando a etapa de serviço
     */
    @Override
    public String toString()
    {
        if (getHasBeenFinished())
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