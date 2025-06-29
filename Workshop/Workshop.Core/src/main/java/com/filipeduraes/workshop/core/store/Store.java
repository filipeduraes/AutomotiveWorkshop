// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.store;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.catalog.ProductCatalog;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gerencia as operações de vendas da loja, permitindo o registro
 * e consulta de vendas realizadas, além de manter o catálogo de produtos.
 *
 * @author Filipe Durães
 */
public class Store
{
    private final ProductCatalog catalog = new ProductCatalog();
    private final CrudRepository<Sale> currentMonthPurchasesRepository;

    /**
     * Cria uma nova instância da loja, inicializando o repositório
     * de vendas do mês atual.
     */
    public Store()
    {
        currentMonthPurchasesRepository = new CrudRepository<>(WorkshopPaths.getPurchasesCurrentMonthPath(), Sale.class);
    }

    /**
     * Obtém a instância do catálogo de produtos da loja.
     *
     * @return o catálogo de produtos da loja
     */
    public ProductCatalog getCatalog()
    {
        return catalog;
    }

    /**
     * Registra uma nova venda de um item do catálogo.
     *
     * @param storeItemID identificador único do item vendido
     * @param quantity quantidade vendida do item
     * @return Sale contendo os dados da venda registrada, ou null se a venda não puder ser realizada
     */
    public Sale registerPurchase(UUID storeItemID, int quantity)
    {
        StoreItem storeItem = catalog.getStoreItemsRepository().getEntityWithID(storeItemID);

        if (storeItem == null || quantity <= 0 || storeItem.getStockAmount() < quantity)
        {
            return null;
        }

        Sale newSale = new Sale(storeItem, quantity);

        StoreItem coppiedStoreItem = new StoreItem(storeItem);
        coppiedStoreItem.setStockAmount(coppiedStoreItem.getStockAmount() - quantity);
        boolean stockUpdateWasSuccessful = catalog.getStoreItemsRepository().updateEntity(coppiedStoreItem);

        if (!stockUpdateWasSuccessful)
        {
            return null;
        }

        currentMonthPurchasesRepository.registerEntity(newSale);
        return newSale;
    }

    /**
     * Recupera todas as vendas realizadas no mês atual.
     *
     * @return lista com todas as vendas do mês atual
     */
    public List<Sale> getCurrentMonthSales()
    {
        List<Sale> allSales = currentMonthPurchasesRepository.getAllEntities();
        allSales.sort(new SaleComparator());
        return allSales;
    }

    /**
     * Recupera todas as vendas realizadas no mês especificado.
     *
     * @param date data de referência para buscar as vendas do mês
     * @return lista com todas as vendas do mês especificado, ou uma lista vazia se não houver vendas
     */
    public List<Sale> getMonthSales(LocalDateTime date)
    {
        String salesMonthPath = WorkshopPaths.getPurchasesMonthPath(date);

        if (!Persistence.hasFile(salesMonthPath))
        {
            return new ArrayList<>();
        }

        CrudRepository<Sale> monthPurchasesRepository = new CrudRepository<>(salesMonthPath, Sale.class);
        return monthPurchasesRepository.getAllEntities();
    }

    private static boolean saleIsWithinSameMonth(Sale sale, LocalDateTime date)
    {
        return sale.getDate().getMonth() == date.getMonth() && sale.getDate().getYear() == date.getYear();
    }
}
