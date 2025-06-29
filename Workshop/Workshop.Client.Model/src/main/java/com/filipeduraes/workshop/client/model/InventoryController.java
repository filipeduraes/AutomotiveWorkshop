// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.model.mappers.InventoryMapper;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.catalog.ProductCatalog;
import com.filipeduraes.workshop.core.catalog.StoreItem;
import com.filipeduraes.workshop.core.store.Sale;
import com.filipeduraes.workshop.core.store.Store;
import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controlador responsável pelo gerenciamento do inventário da loja,
 * permitindo o registro e manutenção dos itens disponíveis para venda.
 *
 * @author Filipe Durães
 */
public class InventoryController
{
    private final InventoryViewModel inventoryViewModel;

    private List<StoreItem> queriedStoreItems;
    private final Store store;
    private final ProductCatalog catalog;

    /**
     * Cria uma nova instância do controlador de inventário.
     *
     * @param viewModelRegistry registro central dos view models da aplicação
     * @param workshop instância principal do sistema da oficina
     */
    public InventoryController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        inventoryViewModel = viewModelRegistry.getInventoryViewModel();
        store = workshop.getStore();
        catalog = store.getCatalog();

        inventoryViewModel.OnRegisterRequest.addListener(this::registerInventoryItem);
        inventoryViewModel.OnSearchRequest.addListener(this::searchInventoryItems);
        inventoryViewModel.OnLoadDataRequest.addListener(this::loadSelectedItemData);
        inventoryViewModel.OnItemRestockRequest.addListener(this::restockItem);
        inventoryViewModel.OnEditRequest.addListener(this::editItem);
        inventoryViewModel.OnRegisterPurchaseRequest.addListener(this::registerPurchase);
        inventoryViewModel.OnMonthSalesReportRequest.addListener(this::generateMonthSalesReport);
        inventoryViewModel.OnDeleteRequest.addListener(this::deleteItem);
    }

    /**
     * Libera os recursos utilizados por este controlador,
     * removendo os listeners registrados.
     */
    public void dispose()
    {
        inventoryViewModel.OnRegisterRequest.removeListener(this::registerInventoryItem);
        inventoryViewModel.OnSearchRequest.removeListener(this::searchInventoryItems);
        inventoryViewModel.OnLoadDataRequest.removeListener(this::loadSelectedItemData);
        inventoryViewModel.OnItemRestockRequest.removeListener(this::restockItem);
        inventoryViewModel.OnEditRequest.removeListener(this::editItem);
        inventoryViewModel.OnRegisterPurchaseRequest.removeListener(this::registerPurchase);
        inventoryViewModel.OnMonthSalesReportRequest.removeListener(this::generateMonthSalesReport);
        inventoryViewModel.OnDeleteRequest.removeListener(this::deleteItem);
    }

    private void registerInventoryItem()
    {
        if (inventoryViewModel.getSelectedDTO() == null)
        {
            inventoryViewModel.setRequestWasSuccessful(false);
            return;
        }

        StoreItem storeItem = InventoryMapper.fromDTO(inventoryViewModel.getSelectedDTO());
        CrudRepository<StoreItem> storeItemsRepository = catalog.getStoreItemsRepository();
        UUID entityID = storeItemsRepository.registerEntity(storeItem);

        inventoryViewModel.getSelectedDTO().setId(entityID);
        inventoryViewModel.setRequestWasSuccessful(true);
    }

    private void searchInventoryItems()
    {
        CrudRepository<StoreItem> storeItemsRepository = catalog.getStoreItemsRepository();
        queriedStoreItems = new ArrayList<>();

        switch (inventoryViewModel.getFieldType())
        {
            case NAME -> queriedStoreItems = storeItemsRepository.searchEntitiesWithPattern(inventoryViewModel.getSearchPattern(), PricedItem::getName);
            case DESCRIPTION -> queriedStoreItems = storeItemsRepository.searchEntitiesWithPattern(inventoryViewModel.getSearchPattern(), PricedItem::getName);
        }

        List<String> descriptions = queriedStoreItems.stream().map(StoreItem::getListDescription).toList();

        inventoryViewModel.setFoundEntitiesDescriptions(descriptions);
        inventoryViewModel.setRequestWasSuccessful(true);
    }

    private void loadSelectedItemData()
    {
        if(!inventoryViewModel.hasValidSelectedIndex())
        {
            inventoryViewModel.setRequestWasSuccessful(false);
            return;
        }

        StoreItem selectedStoreItem = queriedStoreItems.get(inventoryViewModel.getSelectedIndex());
        StoreItemDTO storeItemDTO = InventoryMapper.toDTO(selectedStoreItem);
        inventoryViewModel.setSelectedDTO(storeItemDTO);
        inventoryViewModel.setRequestWasSuccessful(true);
    }

    private void restockItem()
    {
        if(!inventoryViewModel.hasValidSelectedIndex())
        {
            inventoryViewModel.setRequestWasSuccessful(false);
            return;
        }

        loadSelectedItemData();

        StoreItemDTO selectedDTO = inventoryViewModel.getSelectedDTO();
        boolean restockResult = catalog.restockStoreItem(selectedDTO.getId(), inventoryViewModel.getRestockAmount());

        if(restockResult)
        {
            refreshSelectedItem();
        }

        inventoryViewModel.setRequestWasSuccessful(restockResult);
    }

    private void editItem()
    {
        StoreItem coppiedStoreItem = getItemWithEditions();

        boolean updateWasSuccessful = catalog.getStoreItemsRepository().updateEntity(coppiedStoreItem);

        if(updateWasSuccessful)
        {
            refreshSelectedItem();
        }
        else
        {
            loadSelectedItemData();
        }

        inventoryViewModel.setRequestWasSuccessful(updateWasSuccessful);
    }

    private StoreItem getItemWithEditions()
    {
        int selectedIndex = inventoryViewModel.getSelectedIndex();
        StoreItem coppiedStoreItem = new StoreItem(queriedStoreItems.get(selectedIndex));
        final StoreItemDTO selectedStoreItemDTO = inventoryViewModel.getSelectedDTO();

        switch (inventoryViewModel.getFieldType())
        {
            case NAME -> coppiedStoreItem.setName(selectedStoreItemDTO.getName());
            case DESCRIPTION -> coppiedStoreItem.setDescription(selectedStoreItemDTO.getDescription());
            case PRICE -> coppiedStoreItem.setPrice(selectedStoreItemDTO.getPrice());
        }

        return coppiedStoreItem;
    }

    private void registerPurchase()
    {
        if(!inventoryViewModel.hasValidSelectedIndex())
        {
            inventoryViewModel.setRequestWasSuccessful(false);
            return;
        }

        loadSelectedItemData();

        StoreItemDTO selectedDTO = inventoryViewModel.getSelectedDTO();
        int saleQuantity = inventoryViewModel.getSaleQuantity();

        Sale sale = store.registerPurchase(selectedDTO.getId(), saleQuantity);

        if(sale == null)
        {
            inventoryViewModel.setRequestWasSuccessful(false);
            return;
        }

        refreshSelectedItem();

        inventoryViewModel.setSaleID(sale.getID());
        inventoryViewModel.setSaleTotalPrice(TextUtils.formatPrice(sale.getTotalPrice()));

        inventoryViewModel.setRequestWasSuccessful(true);
    }

    private void refreshSelectedItem()
    {
        StoreItemDTO selectedDTO = inventoryViewModel.getSelectedDTO();
        StoreItem storeItem = catalog.getStoreItemsRepository().getEntityWithID(selectedDTO.getId());
        queriedStoreItems.set(inventoryViewModel.getSelectedIndex(), storeItem);
        loadSelectedItemData();
    }

    private void generateMonthSalesReport()
    {
        List<Sale> monthSales;
        int selectedMonth = inventoryViewModel.getSelectedMonth();

        if(selectedMonth < 0 || selectedMonth > 12)
        {
            monthSales = store.getCurrentMonthSales();
        }
        else
        {
            int selectedYear = inventoryViewModel.getSelectedYear();
            LocalDateTime date = LocalDateTime.of(selectedYear, selectedMonth, 1, 0, 0);
            monthSales = store.getMonthSales(date);
        }

        BigDecimal totalPrice = new BigDecimal("0.0");

        for(Sale sale : monthSales)
        {
            totalPrice = totalPrice.add(sale.getTotalPrice());
        }

        String report = monthSales.stream()
                                  .map(Sale::toString)
                                  .collect(Collectors.joining(String.format("%n%s%n", "_".repeat(43))));

        inventoryViewModel.setMonthSaleReport(report);
        inventoryViewModel.setSaleTotalPrice(TextUtils.formatPrice(totalPrice));
    }

    private void deleteItem()
    {
        StoreItemDTO selectedDTO = inventoryViewModel.getSelectedDTO();
        CrudRepository<StoreItem> itemsRepository = store.getCatalog().getStoreItemsRepository();
        StoreItem deletedItem = itemsRepository.deleteEntityWithID(selectedDTO.getId());

        inventoryViewModel.setRequestWasSuccessful(deletedItem != null);
    }
}