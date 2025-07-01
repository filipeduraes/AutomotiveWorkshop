// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.finance;

import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceItemMapper;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.catalog.ProductCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Serviço responsável por gerenciar os itens de serviço do catálogo da oficina.
 * Esta classe coordena as operações de registro, busca, edição e exclusão de
 * itens de serviço, atuando como intermediária entre a interface do usuário
 * e o catálogo de produtos da oficina.
 *
 * @author Filipe Durães
 */
public class ServiceItemService
{
    private final EntityViewModel<PricedItemDTO> serviceItemsViewModel;
    private final ProductCatalog catalog;
    private final CrudRepository<PricedItem> catalogRepository;
    private List<PricedItem> queriedServiceItems;

    /**
     * Constrói um novo serviço de itens de serviço.
     * Inicializa as referências necessárias e registra os listeners para
     * responder aos eventos da interface do usuário.
     *
     * @param viewModelRegistry registro contendo as referências para os ViewModels
     * @param workshop instância principal da oficina contendo os módulos do sistema
     */
    public ServiceItemService(ViewModelRegistry viewModelRegistry, Workshop workshop)
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

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o serviço não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        serviceItemsViewModel.OnRegisterRequest.removeListener(this::registerServiceItem);
        serviceItemsViewModel.OnSearchRequest.removeListener(this::searchServiceItems);
        serviceItemsViewModel.OnLoadDataRequest.removeListener(this::loadServiceItem);
        serviceItemsViewModel.OnEditRequest.removeListener(this::editServiceItem);
        serviceItemsViewModel.OnDeleteRequest.removeListener(this::deleteServiceItem);
    }

    /**
     * Registra um novo item de serviço no catálogo.
     * Cria e adiciona um novo item de serviço com base nos dados fornecidos.
     */
    private void registerServiceItem()
    {
        PricedItemDTO serviceItemDTO = serviceItemsViewModel.getSelectedDTO();
        PricedItem serviceItem = ServiceItemMapper.fromDTO(serviceItemDTO);

        UUID id = catalogRepository.registerEntity(serviceItem);
        serviceItemDTO.setId(id);
        serviceItemsViewModel.setRequestWasSuccessful(true);
    }

    /**
     * Busca itens de serviço com base nos critérios especificados.
     * Executa a busca por nome ou descrição conforme o tipo de campo selecionado.
     */
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

    /**
     * Carrega os dados detalhados do item de serviço selecionado.
     * Recupera e atualiza as informações completas do item escolhido.
     */
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

    /**
     * Edita o item de serviço selecionado.
     * Atualiza as informações do item com base no tipo de campo modificado.
     */
    private void editServiceItem()
    {
        PricedItem itemWithEditions = getItemWithEditions();
        boolean updateWasSuccessful = catalogRepository.updateEntity(itemWithEditions);

        serviceItemsViewModel.setRequestWasSuccessful(updateWasSuccessful);

        loadServiceItem();
    }

    /**
     * Exclui o item de serviço selecionado.
     * Remove permanentemente o item do catálogo de serviços.
     */
    private void deleteServiceItem()
    {
        PricedItemDTO selectedDTO = serviceItemsViewModel.getSelectedDTO();
        PricedItem deletedItem = catalogRepository.deleteEntityWithID(selectedDTO.getId());

        serviceItemsViewModel.setRequestWasSuccessful(deletedItem != null);
    }

    /**
     * Aplica as edições ao item de serviço.
     * Cria uma cópia do item original e aplica as modificações baseadas no tipo de campo.
     *
     * @return item de serviço com as edições aplicadas
     */
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