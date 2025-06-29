// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;

public class RegisterPurchaseMenu implements IWorkshopMenu
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new SearchStoreItemMenu());

    @Override
    public String getMenuDisplayName()
    {
        return "Registrar Venda";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();

        if(!inventoryViewModel.hasValidSelectedIndex())
        {
            return redirector.redirect();
        }

        redirector.reset();

        int stockAmount = inventoryViewModel.getSelectedDTO().getStockAmount();

        if(stockAmount == 0)
        {
            System.out.println("Item selecionado esta em falta no estoque, reponha e tente novamente.");
            return MenuResult.pop();
        }

        String message = String.format("Insira a quantidade do item vendido. Maximo: %d", stockAmount);
        int quantity = ConsoleInput.readLineInteger(message, 1, stockAmount);

        inventoryViewModel.setPurchaseQuantity(quantity);
        inventoryViewModel.OnRegisterPurchaseRequest.broadcast();

        if(inventoryViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Venda registrada com sucesso!%n > ID: %s%n > Preco total: %s", inventoryViewModel.getPurchaseID(), inventoryViewModel.getPurchaseTotalPrice());
        }
        else
        {
            System.out.println("Nao foi possivel registrar a venda, tente novamente.");
        }

        inventoryViewModel.resetSelectedDTO();
        return MenuResult.pop();
    }
}
