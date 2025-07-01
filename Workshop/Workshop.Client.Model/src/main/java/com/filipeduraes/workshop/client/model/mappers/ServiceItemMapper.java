// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.core.catalog.PricedItem;

/**
 * Mapper responsável por converter entre objetos de domínio PricedItem e DTOs PricedItemDTO.
 * Esta classe fornece métodos estáticos para transformar dados entre a camada de domínio
 * e a camada de apresentação, mantendo a separação de responsabilidades.
 *
 * @author Filipe Durães
 */
public class ServiceItemMapper
{
    /**
     * Converte um objeto de domínio PricedItem para um DTO PricedItemDTO.
     *
     * @param pricedItem objeto de domínio a ser convertido
     * @return DTO correspondente ao objeto de domínio
     */
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

    /**
     * Converte um DTO PricedItemDTO para um objeto de domínio PricedItem.
     *
     * @param pricedItemDTO DTO a ser convertido
     * @return objeto de domínio correspondente ao DTO
     */
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
