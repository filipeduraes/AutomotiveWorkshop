package com.filipeduraes.workshop.client.viewmodel;

public enum FieldType
{
    NAME("Nome"),
    EMAIL("Email"),
    CPF("CPF"),
    PHONE("Telefone"),
    ROLE("Cargo"),
    MODEL("Modelo"),
    COLOR("Cor"),
    VIN_NUMBER("Numero do chassi"),
    LICENSE_PLATE("Numero de placa"),
    YEAR("Ano"),
    CLIENT("Cliente"),
    VEHICLE("Veiculo"),
    SHORT_DESCRIPTION("Descricao Curta"),
    DETAILED_DESCRIPTION("Descricao Detalhada");

    private final String displayName;

    private FieldType(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
