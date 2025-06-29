// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa um item de estoque da loja, contendo informações como identificador,
 * nome, descrição, preço e quantidade em estoque.
 *
 * @author Filipe Durães
 */
public class StoreItemDTO extends PricedItemDTO
{
    private final int stockAmount;

    /**
     * Cria um novo item de estoque com todas as informações especificadas.
     *
     * @param id identificador único do item
     * @param name nome do item
     * @param description descrição do item
     * @param price preço do item
     * @param stockAmount quantidade em estoque
     */
    public StoreItemDTO(UUID id, String name, String description, BigDecimal price, int stockAmount)
    {
        super(id, name, description, price);
        this.stockAmount = stockAmount;
    }

    /**
     * Cria um novo item de estoque sem um identificador específico.
     *
     * @param name nome do item
     * @param description descrição do item
     * @param price preço do item
     * @param stockAmount quantidade em estoque
     */
    public StoreItemDTO(String name, String description, BigDecimal price, int stockAmount)
    {
        super(name, description, price);
        this.stockAmount = stockAmount;
    }

    /**
     * Obtém a quantidade disponível em estoque.
     *
     * @return quantidade em estoque
     */
    public int getStockAmount()
    {
        return stockAmount;
    }

    /**
     * Retorna uma representação textual do item de estoque, incluindo todas as suas informações.
     *
     * @return string formatada com as informações do item
     */
    @Override
    public String toString()
    {
        return String.format
        (
            " > ID: %s%n%s%n > Quantidade no estoque: %s",
            getId(),
            super.toString(),
            getStockAmount()
        );
    }
}