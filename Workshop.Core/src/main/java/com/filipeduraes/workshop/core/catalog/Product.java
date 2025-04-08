// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class Product 
{
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    
    public Product(UUID id, String name, String description, String price)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = new BigDecimal(price);
    }
}
