// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.util.UUID;

/**
 * Representa um item disponível para venda na loja, estendendo a classe Product.
 * Esta classe adiciona funcionalidade para gerenciamento de estoque ao produto base.
 *
 * @author Filipe Durães
 */
public class StoreItem extends Product
{
    private int stockAmount;

    /**
     * Cria uma nova instância de StoreItem.
     *
     * @param id Identificador único do item
     * @param name Nome do item
     * @param description Descrição detalhada do item
     * @param price Preço do item
     * @param stockAmount Quantidade disponível em estoque
     */
    public StoreItem(UUID id, String name, String description, String price, int stockAmount)
    {
        super(id, name, description, price);

        this.stockAmount = stockAmount;
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
}