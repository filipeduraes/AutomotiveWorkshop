// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.math.BigDecimal;

public class CreateTemporaryServiceItemMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Criar servico temporario";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        String serviceName = ConsoleInput.readLine("Insira o nome do servico");
        String serviceDescription = ConsoleInput.readLine("Insira uma descricao para o servico");
        BigDecimal servicePrice = ConsoleInput.readLinePositiveBigDecimal("Insira o preco do servico");

        EntityViewModel<PricedItemDTO> serviceItemsViewModel = menuManager.getViewModelRegistry().getServiceItemsViewModel();
        serviceItemsViewModel.setSelectedDTO(new PricedItemDTO(serviceName, serviceDescription, servicePrice));

        return MenuResult.pop();
    }
}
