// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class ProductCatalog 
{
    private Map<UUID, Product> services = new HashMap<>();
    private Map<UUID, StoreItem> storeItems = new HashMap<>();
}
