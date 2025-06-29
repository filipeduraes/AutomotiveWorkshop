// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.store;

import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa uma transação de venda.
 * Esta classe mantém um registro das informações da venda, incluindo o item vendido,
 * quantidade e data da transação.
 *
 * @author Filipe Durães
 */
public class Sale extends WorkshopEntity
{
    private final LocalDateTime date;
    private final StoreItem storeItemSnapshot;
    private final int quantity;

    /**
     * Cria uma nova venda com o item e quantidade especificados.
     *
     * @param storeItemSnapshot o item da loja no momento da venda
     * @param quantity a quantidade vendida do item
     */
    public Sale(StoreItem storeItemSnapshot, int quantity)
    {
        this.storeItemSnapshot = new StoreItem(storeItemSnapshot);
        this.quantity = quantity;
        this.date = LocalDateTime.now();
    }

    /**
     * Obtém a data em que esta venda foi realizada.
     *
     * @return a data da venda
     */
    public LocalDateTime getDate()
    {
        return date;
    }

    /**
     * Obtém uma cópia do item da loja no momento da venda.
     *
     * @return o item da loja no momento da venda
     */
    public StoreItem getStoreItemSnapshot()
    {
        return storeItemSnapshot;
    }

    /**
     * Obtém a quantidade vendida do item.
     *
     * @return a quantidade vendida
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * Calcula o preço total da venda multiplicando o preço unitário pela quantidade.
     *
     * @return o valor total da venda
     */
    public BigDecimal getTotalPrice()
    {
        BigDecimal unitPrice = storeItemSnapshot.getPrice();
        return unitPrice.multiply(new BigDecimal(quantity));
    }

    @Override
    public String toString()
    {
        return String.format
        (
            " > ID: %s%n > Nome: %s%n > Data: %s%n > Quantidade: %d%n > Preco: %s",
            getID(),
            storeItemSnapshot.getName(),
            TextUtils.formatDate(date),
            quantity,
            TextUtils.formatPrice(getTotalPrice())
        );
    }
}