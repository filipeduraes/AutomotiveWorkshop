// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.store;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * Classe responsável por comparar duas vendas com base em suas datas.
 * Implementa a interface Comparator para permitir a ordenação de listas de vendas.
 *
 * @author Filipe Durães
 */
public class SaleComparator implements Comparator<Sale>
{
    /**
     * Compara duas vendas com base em suas datas.
     *
     * @param firstSale primeira venda a ser comparada
     * @param secondSale segunda venda a ser comparada
     * @return -1 se a primeira venda for anterior à segunda,
     * 1 se a primeira venda for posterior à segunda,
     * 0 se as vendas ocorreram na mesma data e hora
     */
    @Override
    public int compare(Sale firstSale, Sale secondSale)
    {
        LocalDateTime fistDate = firstSale.getDate();
        LocalDateTime secondDate = secondSale.getDate();

        if (fistDate.isBefore(secondDate))
        {
            return -1;
        }

        if (fistDate.isAfter(secondDate))
        {
            return 1;
        }

        return 0;
    }
}
