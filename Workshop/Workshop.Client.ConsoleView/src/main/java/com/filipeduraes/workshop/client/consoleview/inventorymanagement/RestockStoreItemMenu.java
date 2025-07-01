// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;

/**
 * Menu de reposição de estoque de itens.
 * Permite ao usuário repor o estoque de um item do inventário, adicionando
 * uma quantidade específica ao estoque atual do item selecionado.
 *
 * Este menu é utilizado para controle de inventário e gestão de estoque.
 *
 * @author Filipe Durães
 */
public class RestockStoreItemMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Repor Estoque do Item";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();

        int addedStockAmount = ConsoleInput.readLineInteger("Insira a quantidade que deseja adicionar no estoque", 1);
        inventoryViewModel.setRestockAmount(addedStockAmount);
        inventoryViewModel.OnItemRestockRequest.broadcast();

        if(inventoryViewModel.getRequestWasSuccessful())
        {
            StoreItemDTO selectedItemDTO = inventoryViewModel.getSelectedDTO();
            System.out.printf("Estoque do item %s reposto com sucesso! Novo estoque: %d%n", selectedItemDTO.getName(), selectedItemDTO.getStockAmount());
        }
        else
        {
            System.out.println("Nao foi possivel repor o estoque do item, tente novamente.");
        }

        inventoryViewModel.resetSelectedDTO();
        return MenuResult.pop();
    }
}
