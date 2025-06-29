// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.core.catalog.StoreItem;

public class InventoryMapper
{
    public static StoreItemDTO toDTO(StoreItem storeItem)
    {
        return new StoreItemDTO
        (
            storeItem.getID(),
            storeItem.getName(),
            storeItem.getDescription(),
            storeItem.getPrice(),
            storeItem.getStockAmount()
        );
    }

    public static StoreItem fromDTO(StoreItemDTO storeItemDTO)
    {
        return new StoreItem
        (
            storeItemDTO.getName(),
            storeItemDTO.getDescription(),
            storeItemDTO.getPrice(),
            storeItemDTO.getStockAmount()
        );
    }
}
