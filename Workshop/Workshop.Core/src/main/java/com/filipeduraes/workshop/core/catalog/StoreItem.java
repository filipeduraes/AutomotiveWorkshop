// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.math.BigDecimal;

/**
 * Representa um item disponível para venda na loja, estendendo a classe Product.
 * Esta classe adiciona funcionalidade para gerenciamento de estoque ao produto base.
 *
 * @author Filipe Durães
 */
public class StoreItem extends PricedItem
{
    private int stockAmount;

    /**
     * Cria uma nova instância de StoreItem.
     *
     * @param name Nome do item
     * @param description Descrição detalhada do item
     * @param price Preço do item
     * @param stockAmount Quantidade disponível em estoque
     */
    public StoreItem(String name, String description, BigDecimal price, int stockAmount)
    {
        super(name, description, price);

        this.stockAmount = stockAmount;
    }

    /**
     * Cria uma nova instância de StoreItem como cópia de outro StoreItem.
     *
     * @param storeItem StoreItem a ser copiado
     */
    public StoreItem(StoreItem storeItem)
    {
        super(storeItem);

        this.stockAmount = storeItem.stockAmount;
    }

    /**
     * Obtém a quantidade disponível em estoque do item.
     *
     * @return quantidade disponível em estoque
     */
    public int getStockAmount()
    {
        return stockAmount;
    }

    /**
     * Define a quantidade disponível em estoque do item.
     *
     * @param stockAmount nova quantidade em estoque
     */
    public void setStockAmount(int stockAmount)
    {
        this.stockAmount = stockAmount;
    }

    /**
     * Obtém uma descrição formatada do item para exibição em lista,
     * combinando o nome do item com sua quantidade em estoque.
     *
     * @return descrição formatada do item no formato "nome (quantidade)"
     */
    public String getListDescription()
    {
        return String.format("%s (%d)", getName(), getStockAmount());
    }
}