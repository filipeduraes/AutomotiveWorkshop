package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

public class ClientDTO
{
    private UUID id;
    private final String name;
    private final String phoneNumber;
    private final String email;
    private final String address;
    private final String cpf;

    public ClientDTO(String name, String phoneNumber, String email, String address, String cpf)
    {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cpf = cpf;
    }

    public ClientDTO(UUID id, String name, String phoneNumber, String email, String address, String cpf)
    {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cpf = cpf;
    }

    public UUID getID()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public String getCPF()
    {
        return cpf;
    }

    public void setID(UUID id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return String.format
        (
            "%n - ID: %s%n - Nome: %s%n - Email: %s%n - CPF: %s%n - Telefone: %s",
            getID(),
            getName(),
            getEmail(),
            getCPF(),
            getPhoneNumber()
        );
    }
}