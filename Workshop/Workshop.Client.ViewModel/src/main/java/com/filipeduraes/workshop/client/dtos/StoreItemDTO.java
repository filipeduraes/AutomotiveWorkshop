// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.util.UUID;

public class StoreItemDTO
{
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private final int stockAmount;

    public StoreItemDTO(UUID id, String name, String description, BigDecimal price, int stockAmount)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    public StoreItemDTO(String name, String description, BigDecimal price, int stockAmount)
    {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockAmount = stockAmount;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public int getStockAmount()
    {
        return stockAmount;
    }

    @Override
    public String toString()
    {
        return String.format
        (
            "ID: %s%n > Nome: %s%n > Descricao: %s%n > Preco: %s%n > Quantidade no estoque: %s",
            getId(),
            getName(),
            getDescription(),
            TextUtils.formatPrice(getPrice()),
            getStockAmount()
        );
    }
}