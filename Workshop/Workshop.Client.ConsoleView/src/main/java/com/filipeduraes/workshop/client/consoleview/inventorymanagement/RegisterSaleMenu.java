// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;

/**
 * Menu de registro de vendas de itens.
 * Permite ao usuário registrar vendas de itens do inventário, coletando a quantidade
 * vendida e processando a transação. O menu verifica a disponibilidade em estoque
 * antes de permitir a venda.
 *
 * Este menu centraliza o processo de controle de vendas e atualização de estoque.
 *
 * @author Filipe Durães
 */
public class RegisterSaleMenu implements IWorkshopMenu
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
        inventoryViewModel.setSaleID(null);

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

        inventoryViewModel.setSaleQuantity(quantity);
        inventoryViewModel.OnRegisterPurchaseRequest.broadcast();

        if(inventoryViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Venda registrada com sucesso!%n > ID: %s%n > Preco total: %s", inventoryViewModel.getSaleID(), inventoryViewModel.getSaleTotalPrice());
        }
        else
        {
            System.out.println("Nao foi possivel registrar a venda, tente novamente.");
        }

        inventoryViewModel.resetSelectedDTO();
        return MenuResult.pop();
    }
}
