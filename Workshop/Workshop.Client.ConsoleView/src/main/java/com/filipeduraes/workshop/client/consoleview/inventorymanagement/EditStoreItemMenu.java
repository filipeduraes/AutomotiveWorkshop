// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;

/**
 * Menu para edição dos dados de um item do inventário.
 * Permite ao usuário alterar informações de um item já registrado no inventário,
 * como nome, descrição e preço.
 *
 * O menu apresenta as opções de campos editáveis e processa a alteração conforme a escolha do usuário.
 *
 * @author Filipe Durães
 */
public class EditStoreItemMenu implements IWorkshopMenu
{
    private final FieldType[] editableFields = { FieldType.NAME, FieldType.DESCRIPTION, FieldType.PRICE };

    @Override
    public String getMenuDisplayName()
    {
        return "Editar Item do Inventario";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();

        int selectedFieldIndex = ConsoleInput.readOptionFromList("Qual campo deseja editar?", editableFields, true);

        if(selectedFieldIndex >= editableFields.length)
        {
            System.out.println("Operacao cancelada. Voltando...");
            return MenuResult.pop();
        }

        StoreItemDTO selectedDTO = inventoryViewModel.getSelectedDTO();

        switch (editableFields[selectedFieldIndex])
        {
            case NAME -> selectedDTO.setName(ConsoleInput.readLine("Insira o novo nome do item"));
            case DESCRIPTION -> selectedDTO.setDescription(ConsoleInput.readLine("Insira a nova descricao do item"));
            case PRICE -> selectedDTO.setPrice(ConsoleInput.readLinePositiveBigDecimal("Insira o novo preco do item"));
        }

        inventoryViewModel.setFieldType(editableFields[selectedFieldIndex]);
        inventoryViewModel.OnEditRequest.broadcast();

        if(inventoryViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Item editado com sucesso. Novos dados:%n%s%n", inventoryViewModel.getSelectedDTO());
        }
        else
        {
            System.out.println("Nao foi possivel editar o item. Tente novamente!");
        }

        return MenuResult.pop();
    }
}