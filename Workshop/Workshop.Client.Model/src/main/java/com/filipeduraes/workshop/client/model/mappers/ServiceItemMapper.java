// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.core.catalog.PricedItem;

public class ServiceItemMapper
{
    public static PricedItemDTO toDTO(PricedItem pricedItem)
    {
        return new PricedItemDTO
        (
            pricedItem.getID(),
            pricedItem.getName(),
            pricedItem.getDescription(),
            pricedItem.getPrice()
        );
    }

    public static PricedItem fromDTO(PricedItemDTO pricedItemDTO)
    {
        return new PricedItem
        (
            pricedItemDTO.getName(),
            pricedItemDTO.getDescription(),
            pricedItemDTO.getPrice()
        );
    }
}
