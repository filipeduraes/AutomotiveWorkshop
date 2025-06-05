package com.filipeduraes.workshop.client.viewmodel;

public enum SearchByOption
{
    NAME("Nome"),
    EMAIL("Email"),
    CPF("CPF"),
    PHONE("Telefone"),
    MODEL("Modelo"),
    COLOR("Cor"),
    VIN_NUMBER("Numero do chassi"),
    LICENSE_PLATE("Numero de placa"),
    YEAR("Ano");

    private final String displayName;

    private SearchByOption(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
