// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.store;

import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.core.catalog.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa uma transação de compra.
 * Esta classe mantém um registro das informações da compra, incluindo o item comprado,
 * quantidade e data da transação.
 *
 * @author Filipe Durães
 */
public class Purchase extends WorkshopEntity
{
    private final LocalDateTime date;
    private final StoreItem storeItemSnapshot;
    private final int quantity;

    /**
     * Cria uma nova compra com o item e quantidade especificados.
     *
     * @param storeItemSnapshot o item da loja no momento da compra
     * @param quantity a quantidade comprada do item
     */
    public Purchase(StoreItem storeItemSnapshot, int quantity)
    {
        this.storeItemSnapshot = new StoreItem(storeItemSnapshot);
        this.quantity = quantity;
        this.date = LocalDateTime.now();
    }

    /**
     * Obtém a data em que esta compra foi realizada.
     *
     * @return a data da compra
     */
    public LocalDateTime getDate()
    {
        return date;
    }

    /**
     * Obtém uma cópia do item da loja no momento da compra.
     *
     * @return o item da loja no momento da compra
     */
    public StoreItem getStoreItemSnapshot()
    {
        return storeItemSnapshot;
    }

    /**
     * Obtém a quantidade comprada do item.
     *
     * @return a quantidade comprada
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * Calcula o preço total da compra multiplicando o preço unitário pela quantidade.
     *
     * @return o valor total da compra
     */
    public BigDecimal getTotalPrice()
    {
        BigDecimal unitPrice = storeItemSnapshot.getPrice();
        return unitPrice.multiply(new BigDecimal(quantity));
    }
}