// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

/**
 * Representa um objeto de transferência de dados (DTO) para clientes da oficina.
 * Esta classe é utilizada para transportar informações de clientes entre diferentes
 * camadas do sistema, contendo todos os dados pessoais relevantes.
 *
 * @author Filipe Durães
 */

public class ClientDTO
{
    private UUID id = null;
    private String name = "";
    private String phoneNumber = "";
    private String email = "";
    private String address = "";
    private String cpf = "";

    /**
     * Cria uma nova instância vazia de ClientDTO.
     */
    public ClientDTO() { }

    /**
     * Cria uma nova instância de ClientDTO com as informações fornecidas.
     *
     * @param name Nome do cliente
     * @param phoneNumber Número de telefone do cliente
     * @param email Endereço de email do cliente
     * @param address Endereço físico do cliente
     * @param cpf CPF do cliente
     */
    public ClientDTO(String name, String phoneNumber, String email, String address, String cpf)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cpf = cpf;
    }

    /**
     * Cria uma nova instância de ClientDTO com ID e demais informações fornecidas.
     *
     * @param id Identificador único do cliente
     * @param name Nome do cliente
     * @param phoneNumber Número de telefone do cliente
     * @param email Endereço de email do cliente
     * @param address Endereço físico do cliente
     * @param cpf CPF do cliente
     */
    public ClientDTO(UUID id, String name, String phoneNumber, String email, String address, String cpf)
    {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cpf = cpf;
    }

    /**
     * Obtém o identificador único do cliente.
     *
     * @return identificador único do cliente
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Define o identificador único do cliente.
     *
     * @param id novo identificador único do cliente
     */
    public void setID(UUID id)
    {
        this.id = id;
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return nome do cliente
     */
    public String getName()
    {
        return name;
    }

    /**
     * Define o nome do cliente.
     *
     * @param name o nome do cliente a ser definido
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Obtém o número de telefone do cliente.
     *
     * @return número de telefone do cliente
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Define o número de telefone do cliente.
     *
     * @param phoneNumber o número de telefone do cliente a ser definido
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Obtém o endereço de email do cliente.
     *
     * @return endereço de email do cliente
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Define o endereço de email do cliente.
     *
     * @param email o endereço de email a ser definido
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Obtém o endereço físico do cliente.
     *
     * @return endereço do cliente
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Define o endereço físico do cliente.
     *
     * @param address o endereço do cliente a ser definido
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Obtém o CPF do cliente.
     *
     * @return CPF do cliente
     */
    public String getCPF()
    {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     *
     * @param cpf o CPF a ser definido
     */
    public void setCpf(String cpf)
    {
        this.cpf = cpf;
    }

    /**
     * Retorna uma representação em string do cliente.
     * A string contém o ID, nome, email, CPF e telefone do cliente em formato legível.
     *
     * @return string representando o cliente
     */
    @Override
    public String toString()
    {
        return String.format
        (
            " - ID: %s%n - Nome: %s%n - Email: %s%n - CPF: %s%n - Endereco: %s%n - Telefone: %s",
            getID(),
            getName(),
            getEmail(),
            getCPF(),
            getAddress(),
            getPhoneNumber()
        );
    }
}