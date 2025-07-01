// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.math.BigDecimal;

/**
 * Menu de registro de novos itens no inventário.
 * Permite ao usuário registrar novos itens no inventário da oficina, coletando
 * informações como nome, descrição, preço e quantidade inicial em estoque.
 *
 * Este menu centraliza o processo de inclusão de novos produtos no catálogo.
 *
 * @author Filipe Durães
 */
public class RegisterStoreItemMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Registrar Novo Item no Inventario";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        EntityViewModel<StoreItemDTO> inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();

        String name = ConsoleInput.readLine("Insira o nome do item");
        String description = ConsoleInput.readLine("Insira uma descricao para o item");
        BigDecimal price = ConsoleInput.readLinePositiveBigDecimal("Insira o preco do item");
        int stockAmount = ConsoleInput.readLineInteger("Insira a quantidade de itens no estoque", 0);

        StoreItemDTO storeItemDTO = new StoreItemDTO(name, description, price, stockAmount);
        inventoryViewModel.setSelectedDTO(storeItemDTO);
        inventoryViewModel.OnRegisterRequest.broadcast();

        String resultMessage = inventoryViewModel.getRequestWasSuccessful()
                               ? String.format("Produto cadastrado com sucesso! Item: %s", inventoryViewModel.getSelectedDTO())
                               : "Nao foi possivel cadastrar o produto, tente novamente.";

        System.out.println(resultMessage);

        inventoryViewModel.resetSelectedDTO();
        return MenuResult.pop();
    }
}