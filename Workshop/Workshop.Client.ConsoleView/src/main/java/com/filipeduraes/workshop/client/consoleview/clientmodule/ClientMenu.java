package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

public class ClientMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] submenus =
    {
        new ClientRegistrationMenu(),
        new ClientDetailsMenu(),
    };

    @Override
    public String getMenuDisplayName()
    {
        return "Cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ClientViewModel clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();
        clientViewModel.resetSelectedDTO();

        IWorkshopMenu selectedMenu = menuManager.showSubmenuOptions("O que deseja fazer?", submenus);

        if(selectedMenu == null)
        {
            return MenuResult.pop();
        }

        return MenuResult.push(selectedMenu);
    }
}
