// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.SaleDTO;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.core.financial.Sale;

/**
 * Mapper responsável por converter entre objetos de domínio relacionados ao inventário
 * e seus respectivos DTOs. Esta classe fornece métodos estáticos para transformar
 * dados entre a camada de domínio e a camada de apresentação, incluindo conversões
 * para itens de estoque e vendas.
 *
 * @author Filipe Durães
 */
public class InventoryMapper
{
    /**
     * Converte um objeto de domínio StoreItem para um DTO StoreItemDTO.
     *
     * @param storeItem objeto de domínio a ser convertido
     * @return DTO correspondente ao objeto de domínio
     */
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

    /**
     * Converte um DTO StoreItemDTO para um objeto de domínio StoreItem.
     *
     * @param storeItemDTO DTO a ser convertido
     * @return objeto de domínio correspondente ao DTO
     */
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

    /**
     * Converte um objeto de domínio Sale para um DTO SaleDTO.
     * Inclui a conversão do item de estoque associado à venda.
     *
     * @param sale objeto de domínio da venda a ser convertido
     * @return DTO correspondente ao objeto de domínio
     */
    public static SaleDTO toSaleDTO(Sale sale)
    {
        return new SaleDTO
        (
            toDTO(sale.getStoreItemSnapshot()),
            sale.getTotalPrice()
        );
    }
}
