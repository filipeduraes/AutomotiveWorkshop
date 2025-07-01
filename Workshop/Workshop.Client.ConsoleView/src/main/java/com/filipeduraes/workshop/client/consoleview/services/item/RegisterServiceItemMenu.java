// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.math.BigDecimal;

/**
 * Menu de registro de novos itens de serviço.
 * Permite ao usuário registrar novos itens de serviço no catálogo da oficina,
 * coletando informações como nome, descrição e preço do serviço.
 *
 * Este menu centraliza o processo de inclusão de novos serviços no catálogo.
 *
 * @author Filipe Durães
 */
public class RegisterServiceItemMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Registrar item de servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        String serviceName = ConsoleInput.readLine("Insira o nome do servico");
        String serviceDescription = ConsoleInput.readLine("Insira uma descricao para o servico");
        BigDecimal servicePrice = ConsoleInput.readLinePositiveBigDecimal("Insira o preco do servico");

        EntityViewModel<PricedItemDTO> serviceItemsViewModel = menuManager.getViewModelRegistry().getServiceItemsViewModel();
        serviceItemsViewModel.setSelectedDTO(new PricedItemDTO(serviceName, serviceDescription, servicePrice));

        serviceItemsViewModel.OnRegisterRequest.broadcast();

        if(serviceItemsViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Servico cadastrado com sucesso! ID: %s", serviceItemsViewModel.getSelectedDTO().getId());
        }
        else
        {
            System.out.println("Nao foi possivel cadastrar o servico, tente novamente.");
        }

        return MenuResult.pop();
    }
}
