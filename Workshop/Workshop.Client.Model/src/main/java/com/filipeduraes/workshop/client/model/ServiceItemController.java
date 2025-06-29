// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceItemMapper;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.catalog.ProductCatalog;
import com.filipeduraes.workshop.core.catalog.StoreItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceItemController
{
    private final EntityViewModel<PricedItemDTO> serviceItemsViewModel;
    private final ProductCatalog catalog;
    private final CrudRepository<PricedItem> catalogRepository;
    private List<PricedItem> queriedServiceItems;

    public ServiceItemController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        serviceItemsViewModel = viewModelRegistry.getServiceItemsViewModel();
        catalog = workshop.getStore().getCatalog();
        catalogRepository = catalog.getServicesRepository();

        serviceItemsViewModel.OnRegisterRequest.addListener(this::registerServiceItem);
        serviceItemsViewModel.OnSearchRequest.addListener(this::searchServiceItems);
        serviceItemsViewModel.OnLoadDataRequest.addListener(this::loadServiceItem);
        serviceItemsViewModel.OnEditRequest.addListener(this::editServiceItem);
        serviceItemsViewModel.OnDeleteRequest.addListener(this::deleteServiceItem);
    }

    public void dispose()
    {
        serviceItemsViewModel.OnRegisterRequest.removeListener(this::registerServiceItem);
        serviceItemsViewModel.OnSearchRequest.removeListener(this::searchServiceItems);
        serviceItemsViewModel.OnLoadDataRequest.removeListener(this::loadServiceItem);
        serviceItemsViewModel.OnEditRequest.removeListener(this::editServiceItem);
        serviceItemsViewModel.OnDeleteRequest.removeListener(this::deleteServiceItem);
    }

    private void registerServiceItem()
    {
        PricedItemDTO serviceItemDTO = serviceItemsViewModel.getSelectedDTO();
        PricedItem serviceItem = ServiceItemMapper.fromDTO(serviceItemDTO);

        UUID id = catalogRepository.registerEntity(serviceItem);
        serviceItemDTO.setId(id);
        serviceItemsViewModel.setRequestWasSuccessful(true);
    }

    private void searchServiceItems()
    {
        queriedServiceItems = new ArrayList<>();

        switch (serviceItemsViewModel.getFieldType())
        {
            case NAME -> queriedServiceItems = catalogRepository.searchEntitiesWithPattern(serviceItemsViewModel.getSearchPattern(), PricedItem::getName);
            case DESCRIPTION -> queriedServiceItems = catalogRepository.searchEntitiesWithPattern(serviceItemsViewModel.getSearchPattern(), PricedItem::getDescription);
        }

        List<String> descriptions = queriedServiceItems.stream().map(PricedItem::getListDescription).toList();
        serviceItemsViewModel.setFoundEntitiesDescriptions(descriptions);
        serviceItemsViewModel.setRequestWasSuccessful(true);
    }

    private void loadServiceItem()
    {
        if(!serviceItemsViewModel.hasValidSelectedIndex())
        {
            serviceItemsViewModel.setRequestWasSuccessful(false);
            return;
        }

        int selectedIndex = serviceItemsViewModel.getSelectedIndex();
        PricedItem queriedPricedItem = queriedServiceItems.get(selectedIndex);
        PricedItem catalogPricedItem = catalog.getServicesRepository().getEntityWithID(queriedPricedItem.getID());

        PricedItemDTO serviceItemDTO = ServiceItemMapper.toDTO(catalogPricedItem);
        serviceItemsViewModel.setSelectedDTO(serviceItemDTO);
        serviceItemsViewModel.setRequestWasSuccessful(true);
    }

    private void editServiceItem()
    {
        PricedItem itemWithEditions = getItemWithEditions();
        boolean updateWasSuccessful = catalogRepository.updateEntity(itemWithEditions);

        serviceItemsViewModel.setRequestWasSuccessful(updateWasSuccessful);

        loadServiceItem();
    }

    private void deleteServiceItem()
    {
        PricedItemDTO selectedDTO = serviceItemsViewModel.getSelectedDTO();
        PricedItem deletedItem = catalogRepository.deleteEntityWithID(selectedDTO.getId());

        serviceItemsViewModel.setRequestWasSuccessful(deletedItem != null);
    }

    private PricedItem getItemWithEditions()
    {
        int selectedIndex = serviceItemsViewModel.getSelectedIndex();
        PricedItem copiedServiceItem = new PricedItem(queriedServiceItems.get(selectedIndex));
        final PricedItemDTO selectedStoreItemDTO = serviceItemsViewModel.getSelectedDTO();

        switch (serviceItemsViewModel.getFieldType())
        {
            case NAME -> copiedServiceItem.setName(selectedStoreItemDTO.getName());
            case DESCRIPTION -> copiedServiceItem.setDescription(selectedStoreItemDTO.getDescription());
            case PRICE -> copiedServiceItem.setPrice(selectedStoreItemDTO.getPrice());
        }

        return copiedServiceItem;
    }
}