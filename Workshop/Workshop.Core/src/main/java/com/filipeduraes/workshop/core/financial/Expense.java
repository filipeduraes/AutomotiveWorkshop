// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.financial;

import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Expense
{
    private final String description;
    private final BigDecimal amount;
    private final LocalDateTime registerTime;

    public Expense(String description, BigDecimal amount)
    {
        this.description = description;
        this.amount = amount;
        registerTime = LocalDateTime.now();
    }

    public String getDescription()
    {
        return description;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public LocalDateTime getRegisterTime()
    {
        return registerTime;
    }

    @Override
    public String toString()
    {
        return String.format(" > %s: %s - %s", description, TextUtils.formatPrice(amount), TextUtils.formatDate(registerTime));
    }
}
