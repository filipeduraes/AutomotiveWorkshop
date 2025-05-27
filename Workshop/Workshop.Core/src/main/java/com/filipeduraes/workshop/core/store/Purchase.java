// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.store;

import com.filipeduraes.workshop.core.catalog.StoreItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa uma transação de compra contendo itens da loja.
 *
 * @author Filipe Durães
 */
public class Purchase
{
    private UUID id;
    private LocalDate date;
    private List<StoreItem> items;

    /**
     * Cria uma nova compra com o ID, data e itens especificados.
     *
     * @param id identificador único para esta compra
     * @param date data em que a compra foi realizada
     * @param items lista de itens incluídos nesta compra
     */
    public Purchase(UUID id, LocalDate date, ArrayList<StoreItem> items)
    {
        this.id = id;
        this.date = date;
        this.items = items;
    }


    /**
     * Obtém o identificador único desta compra.
     *
     * @return o ID da compra
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * Obtém a data em que esta compra foi realizada.
     *
     * @return a data da compra
     */
    public LocalDate getDate()
    {
        return date;
    }

    /**
     * Obtém a lista de itens incluídos nesta compra.
     *
     * @return lista de itens comprados
     */
    public List<StoreItem> getItems()
    {
        return items;
    }

    /**
     * Calcula o valor total desta compra somando todos os preços dos itens.
     *
     * @return valor total da compra
     */
    public BigDecimal calculateTotal()
    {
        BigDecimal total = BigDecimal.ZERO;

        for (StoreItem item : items)
        {
            total = total.add(item.getPrice());
        }

        return total;
    }
}