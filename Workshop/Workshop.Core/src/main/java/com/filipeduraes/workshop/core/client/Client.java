// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.client;

import com.filipeduraes.workshop.core.WorkshopEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representação do cliente da oficina.
 *
 * @author Filipe Durães
 */
public class Client extends WorkshopEntity
{
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String maskedCPF;

    private List<UUID> ownedVehiclesIDs = new ArrayList<>();
    private List<UUID> serviceOrdersIDs = new ArrayList<>();

    /**
     * Construtor para criar um novo cliente com suas informações básicas.
     *
     * @param name        O nome completo do cliente
     * @param email       O endereço de email do cliente
     * @param phoneNumber O número de telefone do cliente
     * @param address     O endereço completo do cliente
     * @param maskedCPF   O CPF do cliente pseudo-anonimizado
     */
    public Client(String name, String email, String phoneNumber, String address, String maskedCPF)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.maskedCPF = maskedCPF;
    }

    /**
     * Checa se o nome do cliente possui o padrão passado, ignorando a capitalização.
     *
     * @param pattern o padrão que será comparado com o nome do cliente
     * @return true se o nome do cliente casar com o padrão, false caso contrário
     */
    public boolean hasMatchingName(String pattern)
    {
        return name.toLowerCase().contains(pattern.toLowerCase());
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return O nome completo do cliente
     */
    public String getName()
    {
        return name;
    }

    /**
     * Obtém o email do cliente.
     *
     * @return O endereço de email do cliente
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Obtém o número de telefone do cliente.
     *
     * @return O número de telefone do cliente
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Obtém o endereço do cliente.
     *
     * @return O endereço completo do cliente
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Obtém o CPF mascarado do cliente.
     *
     * @return O CPF do cliente em formato mascarado
     */
    public String getMaskedCPF()
    {
        return maskedCPF;
    }

    /**
     * Obtém os identificadores dos veículos que o cliente possui.
     *
     * @return Os identificadores dos veículos
     */
    public List<UUID> getOwnedVehiclesIDs()
    {
        return ownedVehiclesIDs;
    }

    /**
     * Obtém os identificadores das ordens de serviço relacionadas ao cliente.
     *
     * @return Os identificadores das ordens de serviço
     */
    public List<UUID> getServiceOrdersIDs()
    {
        return serviceOrdersIDs;
    }

    /**
     * Adiciona um veículo à lista de veículos possuídos pelo cliente.
     *
     * @param vehicleID O identificador único do veículo a ser adicionado
     */
    public void addOwnedVehicle(UUID vehicleID)
    {
        ownedVehiclesIDs.add(vehicleID);
    }


    /**
     * Adiciona uma ordem de serviço à lista de ordens de serviço associadas ao cliente.
     *
     * @param serviceOrderID O identificador único da ordem de serviço a ser adicionada
     */
    public void addServiceOrder(UUID serviceOrderID)
    {
        serviceOrdersIDs.add(serviceOrderID);
    }

    /**
     * Obtém uma representação em string do cliente contendo suas informações principais.
     *
     * @return Uma string formatada com as informações do cliente
     */
    @Override
    public String toString()
    {
        return String.format("Cliente{ID: %s, Nome: '%s', Email: '%s', Telefone: '%s', Endereco: '%s', CPF: '%s'}", getID(), name, email, phoneNumber, address, maskedCPF);
    }
}