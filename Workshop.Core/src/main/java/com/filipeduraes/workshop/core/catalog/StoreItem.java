// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class StoreItem extends Product
{
    private int stockAmount;

    public StoreItem(UUID id, String name, String description, String price, int stockAmount)
    {
        super(id, name, description, price);
        
        this.stockAmount = stockAmount;
    }
}
