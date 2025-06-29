// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.dtos;

import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa um item com preço no catálogo, contendo suas informações básicas
 * como nome, descrição e valor.
 *
 * @author Filipe Durães
 */
public class PricedItemDTO
{
    private UUID id = null;
    private String name;
    private String description;
    private BigDecimal price;

    /**
     * Cria um novo item de catálogo com as informações especificadas.
     *
     * @param id ID do item
     * @param name nome do item
     * @param description descrição do item
     * @param price preço do item
     */
    public PricedItemDTO(UUID id, String name, String description, BigDecimal price)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Cria um novo item de catálogo com as informações especificadas.
     *
     * @param name nome do item
     * @param description descrição do item
     * @param price preço do item
     */
    public PricedItemDTO(String name, String description, BigDecimal price)
    {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Obtém o ID do item
     *
     * @return ID do item
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * Define o novo ID do item
     *
     * @param id novo ID do item
     */
    public void setId(UUID id)
    {
        this.id = id;
    }

    /**
     * Obtém o nome do item.
     *
     * @return nome do item
     */
    public String getName()
    {
        return name;
    }

    /**
     * Define o nome do item.
     *
     * @param name novo nome do item
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Obtém a descrição do item.
     *
     * @return descrição do item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Define a descrição do item.
     *
     * @param description nova descrição do item
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Obtém o preço do item.
     *
     * @return preço do item
     */
    public BigDecimal getPrice()
    {
        return price;
    }

    /**
     * Define o preço do item.
     *
     * @param price novo preço do item
     */
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    /**
     * Retorna uma representação textual do item, incluindo todas as suas informações.
     *
     * @return string formatada com as informações do item
     */
    @Override
    public String toString()
    {
        return String.format
        (
            " > Nome: %s%n > Descricao: %s%n > Preco: %s",
            getName(),
            getDescription(),
            TextUtils.formatPrice(price)
        );
    }
}
