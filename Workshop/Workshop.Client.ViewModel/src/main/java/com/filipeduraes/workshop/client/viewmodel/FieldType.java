// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

/**
 * Representa os diferentes tipos de campos utilizados na interface do usuário.
 * Cada tipo de campo possui um nome de exibição associado.
 *
 * @author Filipe Durães
 */
public enum FieldType
{
    /**
     * Nome do campo
     */
    NAME("Nome"),

    /**
     * Email do usuário
     */
    EMAIL("Email"),

    /**
     * CPF do cliente
     */
    CPF("CPF"),

    /**
     * Endereço do cliente
     */
    ADDRESS("Endereco"),

    /**
     * Número de telefone
     */
    PHONE("Telefone"),

    /**
     * Cargo do funcionário
     */
    ROLE("Cargo"),

    /**
     * Modelo do veículo
     */
    MODEL("Modelo"),

    /**
     * Senha do usuário
     */
    PASSWORD("Senha"),

    /**
     * Cor do veículo
     */
    COLOR("Cor"),

    /**
     * Número do chassi do veículo
     */
    VIN_NUMBER("Numero do chassi"),

    /**
     * Número da placa do veículo
     */
    LICENSE_PLATE("Numero de placa"),

    /**
     * Ano do veículo
     */
    YEAR("Ano"),

    /**
     * Cliente
     */
    CLIENT("Cliente"),

    /**
     * Veículo
     */
    VEHICLE("Veiculo"),

    /**
     * Descrição curta
     */
    SHORT_DESCRIPTION("Descricao Curta"),

    /**
     * Descrição detalhada
     */
    DETAILED_DESCRIPTION("Descricao Detalhada"),

    LOCAL_USER("Usuario Local");

    /**
     * O nome de exibição do campo
     */
    private final String displayName;

    /**
     * Constrói um FieldType com o nome de exibição especificado.
     *
     * @param displayName O nome de exibição do campo
     */
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
