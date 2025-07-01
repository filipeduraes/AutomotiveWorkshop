// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.math.BigDecimal;

/**
 * Representa um objeto de transferência de dados (DTO) para despesas da oficina.
 * Esta classe é utilizada para transportar informações de despesas entre diferentes
 * camadas do sistema, contendo descrição e valor da despesa.
 *
 * @author Filipe Durães
 */
public class ExpenseDTO
{
    private String description;
    private BigDecimal amount;

    /**
     * Cria uma nova instância de ExpenseDTO com as informações fornecidas.
     *
     * @param description descrição da despesa
     * @param amount valor da despesa
     */
    public ExpenseDTO(String description, BigDecimal amount)
    {
        this.description = description;
        this.amount = amount;
    }

    /**
     * Obtém a descrição da despesa.
     *
     * @return descrição da despesa
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Define a descrição da despesa.
     *
     * @param description nova descrição da despesa
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Obtém o valor da despesa.
     *
     * @return valor da despesa
     */
    public BigDecimal getAmount()
    {
        return amount;
    }

    /**
     * Define o valor da despesa.
     *
     * @param amount novo valor da despesa
     */
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}