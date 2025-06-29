// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.financial;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;

public class ShowMonthSalesMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Mostrar vendas do mes";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();
        boolean showCurrentMonth = ConsoleInput.readConfirmation("Deseja mostrar as vendas do mes atual?");
        inventoryViewModel.resetSelectedDate();
        inventoryViewModel.setMonthSaleReport("");

        if(!showCurrentMonth)
        {
            int month = ConsoleInput.readLineInteger("Insira o mes desejado (1-12): ", 1, 12);
            inventoryViewModel.setSelectedMonth(month);

            boolean showCurrentYear = ConsoleInput.readConfirmation("Deseja mostrar as vendas do ano atual?");

            if(!showCurrentYear)
            {
                int year = ConsoleInput.readLineInteger("Insira o ano desejado: ", 2000, 2100);
                inventoryViewModel.setSelectedYear(year);
            }
        }

        inventoryViewModel.OnMonthSalesReportRequest.broadcast();

        if(inventoryViewModel.getMonthSaleReport().isBlank())
        {
            System.out.println("Nenhuma venda foi registrada esse mes. Voltando...");
            return MenuResult.pop();
        }

        System.out.println(inventoryViewModel.getMonthSaleReport());
        System.out.printf("Valor Total = %s%n", inventoryViewModel.getSaleTotalPrice());

        ConsoleInput.readOptionFromList("O que deseja fazer?", new String[]{"X Voltar"});

        return MenuResult.pop();
    }
}