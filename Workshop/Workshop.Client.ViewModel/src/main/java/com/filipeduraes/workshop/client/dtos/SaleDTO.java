// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.math.BigDecimal;

public class SaleDTO
{
    private final StoreItemDTO item;
    private final BigDecimal subtotal;

    public SaleDTO(StoreItemDTO item, BigDecimal subtotal)
    {
        this.item = item;
        this.subtotal = subtotal;
    }

    public StoreItemDTO getItem()
    {
        return item;
    }

    public BigDecimal getSubtotal()
    {
        return subtotal;
    }
}
