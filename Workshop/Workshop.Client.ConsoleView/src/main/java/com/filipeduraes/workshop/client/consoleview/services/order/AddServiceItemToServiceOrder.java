// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.services.item.SelectServiceItem;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

public class AddServiceItemToServiceOrder implements IWorkshopMenu
{
    PopupMenuRedirector selectServiceItemRedirector = new PopupMenuRedirector(new SelectServiceItem());

    @Override
    public String getMenuDisplayName()
    {
        return "Adicionar item de servico a ordem de servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();

        if(!viewModelRegistry.getServiceItemsViewModel().hasLoadedDTO())
        {
            return selectServiceItemRedirector.redirect();
        }

        selectServiceItemRedirector.reset();

        ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        serviceOrderViewModel.OnAddServiceItemRequested.broadcast();

        if(serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.println("Item de servico adicionado com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel adicionar o item de servico, tente novamente.");
        }

        return MenuResult.pop();
    }
}
