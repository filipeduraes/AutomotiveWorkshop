// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;

/**
 * Representa um produto ou serviço que pode ser vendido na oficina.
 * Esta classe é utilizada tanto para itens físicos da loja quanto para serviços prestados.
 * Os produtos são listados nas notas fiscais dos clientes e no balanço patrimonial do administrador.
 *
 * @author Filipe Durães
 */
public class PricedItem extends WorkshopEntity
{
    private String name;
    private String description;
    private BigDecimal price;

    /**
     * Cria uma nova instância de produto ou serviço.
     *
     * @param name Nome do produto ou serviço
     * @param description Descrição detalhada do produto ou serviço
     * @param price Preço do produto ou serviço em formato string
     */
    public PricedItem(String name, String description, BigDecimal price)
    {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Cria uma nova instância de Product com base em outro objeto Product.
     *
     * @param pricedItem Objeto Product a partir do qual as propriedades serão copiadas
     */
    public PricedItem(PricedItem pricedItem)
    {
        super(pricedItem);

        name = pricedItem.name;
        description = pricedItem.description;
        price = pricedItem.price;
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
     * Define o nome do produto ou serviço.
     *
     * @param name o novo nome do produto ou serviço
     */
    public void setName(String name)
    {
        this.name = name;
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
     * Define a descrição detalhada do produto ou serviço.
     *
     * @param description a nova descrição do produto ou serviço
     */
    public void setDescription(String description)
    {
        this.description = description;
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

    /**
     * Define o preço do produto ou serviço.
     *
     * @param price o novo preço do produto ou serviço em formato BigDecimal
     */
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    /**
     * Obtém uma descrição formatada do item para exibição em lista,
     * combinando o seu preço.
     *
     * @return descrição formatada do item no formato "nome - preço"
     */
    public String getListDescription()
    {
        return String.format("%s - %s", getName(), TextUtils.formatPrice(getPrice()));
    }
}
