// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.client;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Representação do cliente da oficina.
 *
 * @author Filipe Durães
 */
public class Client
{
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String maskedCPF;

    private ArrayList<UUID> ownedVehiclesIDs = new ArrayList<>();

    /**
     * Construtor para criar um novo cliente com suas informações básicas.
     *
     * @param name O nome completo do cliente
     * @param email O endereço de email do cliente
     * @param phoneNumber O número de telefone do cliente
     * @param address O endereço completo do cliente
     * @param maskedCPF O CPF do cliente pseudo-anonimizado
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
     * Atribui um novo identificador único ao cliente.
     *
     * @param id Identificador único
     */
    public void setID(UUID id)
    {
        this.id = id;
    }

    /**
     * Retorna o identificador único do cliente.
     *
     * @return Identificador único
     */
    public UUID getID()
    {
        return id;
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
     * Retorna o nome do cliente.
     *
     * @return O nome completo do cliente
     */
    public String getName()
    {
        return name;
    }

    /**
     * Retorna o email do cliente.
     *
     * @return O endereço de email do cliente
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Retorna o número de telefone do cliente.
     *
     * @return O número de telefone do cliente
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Retorna o endereço do cliente.
     *
     * @return O endereço completo do cliente
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Retorna o CPF mascarado do cliente.
     *
     * @return O CPF do cliente em formato mascarado
     */
    public String getMaskedCPF()
    {
        return maskedCPF;
    }

    /**
     * Retorna uma representação em string do cliente contendo suas informações principais.
     *
     * @return Uma string formatada com as informações do cliente
     */
    @Override
    public String toString()
    {
        return String.format("Cliente{ID: %s, Nome: '%s', Email: '%s', Telefone: '%s', Endereco: '%s', CPF: '%s'}", id, name, email, phoneNumber, address, maskedCPF);
    }
}