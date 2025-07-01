// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import java.math.BigDecimal;

/**
 * Representa um objeto de transferência de dados (DTO) para vendas de itens da oficina.
 * Esta classe é utilizada para transportar informações de vendas entre diferentes
 * camadas do sistema, contendo o item vendido e o subtotal da venda.
 *
 * @author Filipe Durães
 */
public class SaleDTO
{
    private final StoreItemDTO item;
    private final BigDecimal subtotal;

    /**
     * Cria uma nova instância de SaleDTO com as informações fornecidas.
     *
     * @param item item da loja que foi vendido
     * @param subtotal subtotal da venda
     */
    public SaleDTO(StoreItemDTO item, BigDecimal subtotal)
    {
        this.item = item;
        this.subtotal = subtotal;
    }

    /**
     * Obtém o item da loja que foi vendido.
     *
     * @return DTO do item vendido
     */
    public StoreItemDTO getItem()
    {
        return item;
    }

    /**
     * Obtém o subtotal da venda.
     *
     * @return subtotal da venda
     */
    public BigDecimal getSubtotal()
    {
        return subtotal;
    }
}
