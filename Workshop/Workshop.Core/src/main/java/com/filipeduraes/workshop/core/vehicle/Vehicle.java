// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.vehicle;

import com.filipeduraes.workshop.core.WorkshopEntity;
import java.util.UUID;

/**
 * Representa um veículo no sistema da oficina.
 * Esta classe mantém todas as informações relevantes sobre um veículo, incluindo
 * suas características físicas e seu status atual na oficina.
 *
 * @author Filipe Durães
 */
public class Vehicle extends WorkshopEntity
{
    private static int totalInstanceCount = 0;

    private UUID ownerID;
    private String model;
    private String color;
    private String vinNumber;
    private String licensePlate;
    private int year;

    public Vehicle()
    {
        totalInstanceCount++;
    }

    /**
     * Cria uma nova instância de veículo com as informações fornecidas.
     * O status inicial do veículo é definido como RECEIVED.
     *
     * @param ownerID ID do proprietário do veículo
     * @param model Modelo do veículo (ex: Gol, Civic, etc)
     * @param color Cor do veículo
     * @param vinNumber Número do chassi do veículo
     * @param licensePlate Placa do veículo
     * @param year Ano de fabricação do veículo
     */
    public Vehicle(UUID ownerID, String model, String color, String vinNumber, String licensePlate, int year)
    {
        totalInstanceCount++;

        this.ownerID = ownerID;
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    /**
     * Obtém o modelo do veículo.
     *
     * @return modelo do veículo
     */
    public String getModel()
    {
        return model;
    }

    /**
     * Obtém a cor do veículo.
     *
     * @return cor do veículo
     */
    public String getColor()
    {
        return color;
    }

    /**
     * Obtém a placa do veículo.
     *
     * @return placa do veículo
     */
    public String getLicensePlate()
    {
        return licensePlate;
    }

    /**
     * Obtém o número do chassi do veículo.
     *
     * @return número do chassi
     */
    public String getVinNumber()
    {
        return vinNumber;
    }

    /**
     * Obtém o ano de fabricação do veículo.
     *
     * @return ano do veículo
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Obtém o ID do proprietário do veículo.
     *
     * @return ID do proprietário
     */
    public UUID getOwnerID()
    {
        return ownerID;
    }

    /**
     * Obtém o número total de instâncias de Vehicle que foram criadas.
     * Questão 02 - Item 11
     *
     * @return o número total de instâncias de Vehicle criadas
     */
    public static int getTotalInstanceCount()
    {
        return totalInstanceCount;
    }


    /**
     * Retorna uma representação em string do veículo.
     * A string contém o modelo, cor e ano do veículo no formato "modelo cor (ano)".
     *
     * @return string representando o veículo
     */
    @Override
    public String toString()
    {
        return String.format("%s %s (%s)", model, color, year);
    }
}