// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

/**
 * DTO que representa um veículo no sistema da oficina.
 * Contém todas as informações básicas de um veículo, incluindo
 * identificação, características físicas e dados de registro.
 *
 * @author Filipe Durães
 */
public class VehicleDTO
{
    private UUID id;
    private UUID ownerID;
    private final String model;
    private final String color;
    private final String vinNumber;
    private final String licensePlate;
    private final int year;

    /**
     * Cria uma nova instância de VehicleDTO com ID especificado.
     *
     * @param id Identificador único do veículo
     * @param model Modelo do veículo
     * @param color Cor do veículo
     * @param vinNumber Número do chassi do veículo
     * @param licensePlate Placa do veículo
     * @param year Ano de fabricação do veículo
     */
    public VehicleDTO(UUID id, String model, String color, String vinNumber, String licensePlate, int year)
    {
        this.id = id;
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    /**
     * Cria uma nova instância de VehicleDTO sem ID especificado.
     *
     * @param model Modelo do veículo
     * @param color Cor do veículo
     * @param vinNumber Número do chassi do veículo
     * @param licensePlate Placa do veículo
     * @param year Ano de fabricação do veículo
     */
    public VehicleDTO(String model, String color, String vinNumber, String licensePlate, int year)
    {
        this.model = model;
        this.color = color;
        this.vinNumber = vinNumber;
        this.licensePlate = licensePlate;
        this.year = year;
    }

    /**
     * Obtém o identificador único do veículo.
     *
     * @return identificador do veículo
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Define o identificador único do veículo.
     *
     * @param id novo identificador do veículo
     */
    public void setID(UUID id)
    {
        this.id = id;
    }

    /**
     * Obtém o identificador do proprietário do veículo.
     *
     * @return identificador do proprietário
     */
    public UUID getOwnerID()
    {
        return ownerID;
    }

    /**
     * Define o identificador do proprietário do veículo.
     *
     * @param ownerID novo identificador do proprietário
     */
    public void setOwnerID(UUID ownerID)
    {
        this.ownerID = ownerID;
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
     * Obtém o número do chassi do veículo.
     *
     * @return número do chassi
     */
    public String getVinNumber()
    {
        return vinNumber;
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
     * Obtém o ano de fabricação do veículo.
     *
     * @return ano do veículo
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Retorna uma representação textual do veículo, incluindo todas suas informações principais.
     *
     * @return string formatada com as informações do veículo
     */
    @Override
    public String toString()
    {
        return String.format
        (
            " - ID: %s%n - Modelo: %s%n - Cor: %s%n - Chassi: %s%n - Placa: %s%n - Ano: %d",
            getID(),
            getModel(),
            getColor(),
            getVinNumber(),
            getLicensePlate(),
            getYear()
        );
    }
}
