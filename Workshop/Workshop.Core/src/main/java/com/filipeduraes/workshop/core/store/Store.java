// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.store;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.catalog.ProductCatalog;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Gerencia as operações de vendas da loja, permitindo o registro
 * e consulta de compras realizadas.
 *
 * @author Filipe Durães
 */
public class Store
{
    private final ProductCatalog catalog = new ProductCatalog();

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
     * Registra uma nova compra de um item do catálogo.
     *
     * @param storeItemID identificador único do item comprado
     * @param quantity quantidade comprada do item
     * @return Optional contendo o ID da compra se foi registrada com sucesso, ou empty caso contrário
     */
    public Purchase registerPurchase(UUID storeItemID, int quantity)
    {
        StoreItem storeItem = catalog.getStoreItemsRepository().getEntityWithID(storeItemID);

        if (storeItem == null || quantity <= 0 || storeItem.getStockAmount() < quantity)
        {
            return null;
        }

        CrudRepository<Purchase> purchasesRepository = new CrudRepository<>(WorkshopPaths.PURCHASES_PATH, Purchase.class);
        Purchase newPurchase = new Purchase(storeItem, quantity);

        StoreItem coppiedStoreItem = new StoreItem(storeItem);
        coppiedStoreItem.setStockAmount(coppiedStoreItem.getStockAmount() - quantity);
        boolean stockUpdateWasSuccessful = catalog.getStoreItemsRepository().updateEntity(coppiedStoreItem);

        if(!stockUpdateWasSuccessful)
        {
            return null;
        }

        purchasesRepository.registerEntity(newPurchase);
        return newPurchase;
    }

    /**
     * Recupera todas as compras realizadas no mês atual.
     *
     * @return lista com todas as compras do mês atual
     */
    public List<Purchase> fetchMonthPurchases()
    {
        CrudRepository<Purchase> purchasesRepository = new CrudRepository<>(WorkshopPaths.PURCHASES_PATH, Purchase.class);
        LocalDateTime currentDate = LocalDateTime.now();

        return purchasesRepository.findEntitiesWithPredicate(purchase -> purchaseIsWithinSameMonth(purchase, currentDate));
    }

    private static boolean purchaseIsWithinSameMonth(Purchase purchase, LocalDateTime date)
    {
        return purchase.getDate().getMonth() == date.getMonth() && purchase.getDate().getYear() == date.getYear();
    }
}
