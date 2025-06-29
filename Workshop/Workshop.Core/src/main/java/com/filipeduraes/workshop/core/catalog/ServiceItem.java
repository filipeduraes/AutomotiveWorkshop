// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.math.BigDecimal;
import java.util.UUID;

public class ServiceItem extends PricedItem
{
    private final UUID employeeID;

    public ServiceItem(UUID employeeID, String name, String description, BigDecimal price)
    {
        super(name, description, price);
        this.employeeID = employeeID;
    }

    public UUID getEmployeeID()
    {
        return employeeID;
    }
}
