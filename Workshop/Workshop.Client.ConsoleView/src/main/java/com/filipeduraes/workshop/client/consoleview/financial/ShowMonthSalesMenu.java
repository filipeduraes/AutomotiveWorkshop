// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.financial;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.ShowMonthReportMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;

/**
 * Menu de exibição do relatório mensal de vendas.
 * Permite ao usuário visualizar todas as vendas registradas no mês atual,
 * incluindo o valor total das vendas realizadas.
 *
 * Este menu é utilizado para controle financeiro e análise de receitas.
 *
 * @author Filipe Durães
 */
public class ShowMonthSalesMenu extends ShowMonthReportMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Mostrar vendas do mes";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        super.showMenu(menuManager);

        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();
        System.out.printf("Valor Total = %s%n", inventoryViewModel.getSaleTotalPrice());

        ConsoleInput.readOptionFromList("O que deseja fazer?", new String[]{"X Voltar"});

        return MenuResult.pop();
    }

    @Override
    protected IMonthReportViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getInventoryViewModel();
    }

    @Override
    protected boolean canShowConfirmation()
    {
        return false;
    }
}