// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.math.BigDecimal;

public class ExpenseDTO
{
    private String description;
    private BigDecimal amount;

    public ExpenseDTO(String description, BigDecimal amount)
    {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}