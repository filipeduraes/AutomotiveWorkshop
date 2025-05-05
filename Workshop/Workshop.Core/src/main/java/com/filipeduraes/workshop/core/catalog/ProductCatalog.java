// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

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
    private Map<UUID, Product> services = new HashMap<>();
    private Map<UUID, StoreItem> storeItems = new HashMap<>();
}
