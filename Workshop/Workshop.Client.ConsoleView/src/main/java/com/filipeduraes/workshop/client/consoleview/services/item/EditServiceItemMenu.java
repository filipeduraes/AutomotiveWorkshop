// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

/**
 * Menu para edição dos dados de um item de serviço.
 * Permite ao usuário alterar informações de um item de serviço já registrado,
 * como nome, descrição e preço.
 *
 * O menu apresenta as opções de campos editáveis e processa a alteração conforme a escolha do usuário.
 *
 * @author Filipe Durães
 */
public class EditServiceItemMenu implements IWorkshopMenu
{
    private final FieldType[] editableFields = { FieldType.NAME, FieldType.DESCRIPTION, FieldType.PRICE };

    @Override
    public String getMenuDisplayName()
    {
        return "Editar item de servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        EntityViewModel<PricedItemDTO> serviceItemsViewModel = menuManager.getViewModelRegistry().getServiceItemsViewModel();

        int selectedFieldIndex = ConsoleInput.readOptionFromList("Qual campo deseja editar?", editableFields, true);

        if(selectedFieldIndex >= editableFields.length)
        {
            System.out.println("Operacao cancelada. Voltando...");
            return MenuResult.pop();
        }

        PricedItemDTO selectedDTO = serviceItemsViewModel.getSelectedDTO();

        switch (editableFields[selectedFieldIndex])
        {
            case NAME -> selectedDTO.setName(ConsoleInput.readLine("Insira o novo nome do item"));
            case DESCRIPTION -> selectedDTO.setDescription(ConsoleInput.readLine("Insira a nova descricao do item"));
            case PRICE -> selectedDTO.setPrice(ConsoleInput.readLinePositiveBigDecimal("Insira o novo preco do item"));
        }

        serviceItemsViewModel.setFieldType(editableFields[selectedFieldIndex]);
        serviceItemsViewModel.OnEditRequest.broadcast();

        if(serviceItemsViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Item editado com sucesso. Novos dados:%n%s%n", serviceItemsViewModel.getSelectedDTO());
        }
        else
        {
            System.out.println("Nao foi possivel editar o item. Tente novamente!");
        }

        return MenuResult.pop();
    }
}
