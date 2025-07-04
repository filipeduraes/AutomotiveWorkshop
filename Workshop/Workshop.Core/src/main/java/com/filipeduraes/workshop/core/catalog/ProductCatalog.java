// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.util.UUID;

/**
 * Classe responsável por gerenciar o catálogo de itens da loja e serviços da oficina.
 *
 * @author Filipe Durães
 */
public class ProductCatalog
{
    private final CrudRepository<PricedItem> servicesRepository;
    private final CrudRepository<StoreItem> storeItemsRepository;

    /**
     * Cria uma nova instância do catálogo de produtos e serviços,
     * inicializando os repositórios necessários para o gerenciamento
     * dos itens da loja e serviços da oficina.
     */
    public ProductCatalog()
    {
        servicesRepository = new CrudRepository<>(WorkshopPaths.SERVICE_CATALOG_PATH, PricedItem.class);
        storeItemsRepository = new CrudRepository<>(WorkshopPaths.STORE_ITEMS_CATALOG_PATH, StoreItem.class);
    }

    /**
     * Retorna o repositório responsável por gerenciar as operações de CRUD
     * relacionadas aos serviços da oficina.
     *
     * @return um {@code CrudRepository<Product>} que gerencia os serviços.
     */
    public CrudRepository<PricedItem> getServicesRepository()
    {
        return servicesRepository;
    }

    /**
     * Retorna o repositório responsável por gerenciar as operações de CRUD
     * relacionadas aos itens da loja.
     *
     * @return um {@code CrudRepository<StoreItem>} que gerencia os itens da loja.
     */
    public CrudRepository<StoreItem> getStoreItemsRepository()
    {
        return storeItemsRepository;
    }

    /**
     * Adiciona estoque a um item da loja.
     * Verifica se o item existe e se a quantidade a ser adicionada é válida.
     *
     * @param itemID identificador único do item a ser reabastecido
     * @param addedStockAmount quantidade de estoque a ser adicionada
     * @return true se o reabastecimento foi bem-sucedido, false caso contrário
     */
    public boolean restockStoreItem(UUID itemID, int addedStockAmount)
    {
        StoreItem originalStoreItem = storeItemsRepository.getEntityWithID(itemID);

        if(originalStoreItem == null || addedStockAmount <= 0)
        {
            return false;
        }

        StoreItem coppiedStoreItem = new StoreItem(originalStoreItem);
        int newStockAmount = coppiedStoreItem.getStockAmount() + addedStockAmount;
        coppiedStoreItem.setStockAmount(newStockAmount);
        return storeItemsRepository.updateEntity(coppiedStoreItem);
    }
}