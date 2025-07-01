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
 * Menu para criação de itens de serviço temporários.
 * Permite criar um item de serviço temporário sem persistência no catálogo,
 * útil para serviços pontuais ou não catalogados.
 *
 * @author Filipe Durães
 */
public class CreateTemporaryServiceItemMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Criar servico temporario";
    }

    /**
     * Exibe o menu de criação de serviço temporário.
     * Coleta informações do usuário (nome, descrição e preço) e cria
     * um item de serviço temporário no ViewModel.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
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
