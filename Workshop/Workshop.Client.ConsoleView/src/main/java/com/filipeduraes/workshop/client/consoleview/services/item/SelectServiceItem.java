// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;

public class SelectServiceItem extends RedirectMenu
{
    /**
     * Constrói um novo menu de seleção de item de serviço
     */
    public SelectServiceItem()
    {
        super("Selecionar item de sevico", new IWorkshopMenu[]
        {
            new SearchServiceItemMenu(),
            new CreateTemporaryServiceItemMenu(),
            new RegisterServiceItemMenu()
        });
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(menuManager.getViewModelRegistry().getServiceItemsViewModel().hasLoadedDTO())
        {
            return MenuResult.pop();
        }

        return super.showMenu(menuManager);
    }
}
