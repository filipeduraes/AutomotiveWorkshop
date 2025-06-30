// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.RegisterSaleMenu;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

public class AddSaleToServiceOrder implements IWorkshopMenu
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new RegisterSaleMenu());

    @Override
    public String getMenuDisplayName()
    {
        return "Adicionar venda a ordem de servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();

        if(inventoryViewModel.getSaleID() == null)
        {
            return redirector.redirect();
        }
        redirector.reset();

        ServiceOrderViewModel serviceOrderViewModel = menuManager.getViewModelRegistry().getServiceOrderViewModel();
        serviceOrderViewModel.OnAddSaleRequested.broadcast();

        if(serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.println("Venda adicionada com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel adicionar a venda, tente novamente.");
        }

        return MenuResult.pop();
    }
}
