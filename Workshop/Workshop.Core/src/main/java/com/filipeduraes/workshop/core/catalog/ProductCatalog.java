// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Classe responsável por gerenciar o catálogo de itens da loja e serviços da oficina.
 *
 * @author Filipe Durães
 */
public class ProductCatalog
{
    private final CrudRepository<Product> servicesRepository;
    private final CrudRepository<StoreItem> storeItemsRepository;

    /**
     * Cria uma nova instância do catálogo de produtos e serviços,
     * inicializando os repositórios necessários para o gerenciamento
     * dos itens da loja e serviços da oficina.
     */
    public ProductCatalog()
    {
        servicesRepository = new CrudRepository<>(WorkshopPaths.SERVICE_CATALOG_PATH, Product.class);
        storeItemsRepository = new CrudRepository<>(WorkshopPaths.STORE_ITEMS_CATALOG_PATH, StoreItem.class);
    }

    /**
     * Retorna o repositório responsável por gerenciar as operações de CRUD
     * relacionadas aos serviços da oficina.
     *
     * @return um {@code CrudRepository<Product>} que gerencia os serviços.
     */
    public CrudRepository<Product> getServicesRepository()
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
}