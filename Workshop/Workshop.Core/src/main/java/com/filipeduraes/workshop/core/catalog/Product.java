// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa um produto ou serviço que pode ser vendido na oficina.
 * Esta classe é utilizada tanto para itens físicos da loja quanto para serviços prestados.
 * Os produtos são listados nas notas fiscais dos clientes e no balanço patrimonial do administrador.
 *
 * @author Filipe Durães
 */
public class Product
{
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;

    /**
     * Cria uma nova instância de produto ou serviço.
     *
     * @param id Identificador único do produto
     * @param name Nome do produto ou serviço
     * @param description Descrição detalhada do produto ou serviço
     * @param price Preço do produto ou serviço em formato string
     */
    public Product(UUID id, String name, String description, String price)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = new BigDecimal(price);
    }

    /**
     * Obtém o identificador único do produto.
     *
     * @return identificador UUID do produto
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * Obtém o nome do produto ou serviço.
     *
     * @return nome do produto
     */
    public String getName()
    {
        return name;
    }

    /**
     * Obtém a descrição detalhada do produto ou serviço.
     *
     * @return descrição do produto
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Obtém o preço do produto ou serviço.
     *
     * @return preço do produto em BigDecimal
     */
    public BigDecimal getPrice()
    {
        return price;
    }
}
