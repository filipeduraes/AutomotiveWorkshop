// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.item;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.dtos.PricedItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.util.List;

public class ServiceItemDetailsMenu extends EntityDetailsMenu<EntityViewModel<PricedItemDTO>, PricedItemDTO>
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new SearchServiceItemMenu());

    @Override
    public String getMenuDisplayName()
    {
        return "Buscar Item de Servico";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(!menuManager.getViewModelRegistry().getServiceItemsViewModel().hasValidSelectedIndex())
        {
            return redirector.redirect();
        }

        redirector.reset();

        return super.showMenu(menuManager);
    }

    @Override
    protected List<MenuOption> buildOptions()
    {
        List<MenuOption> options = super.buildOptions();

        List<MenuOption> storeItemOptions = List.of
        (
            new MenuOption("Editar item de sevico", this::redirectToEditMenu)
        );

        options.addAll(0, storeItemOptions);
        return options;
    }

    @Override
    protected EntityViewModel<PricedItemDTO> findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getServiceItemsViewModel();
    }

    private MenuResult redirectToEditMenu(MenuManager menuManager)
    {
        return MenuResult.push(new EditServiceItemMenu());
    }
}