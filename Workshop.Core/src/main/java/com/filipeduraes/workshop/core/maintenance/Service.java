// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.maintenance;

/**
 *
 * @author Filipe Durães
 */
public class Service
{
    private String name;
    private double price;
    
    public Service(String name, double price)
    {
        this.name = name;
        this.price = price;
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getPrice()
    {
        return price;
    }
}