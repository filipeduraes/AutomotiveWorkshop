package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

import java.util.List;

public class ClientDetailsMenu extends EntityDetailsMenu<ClientViewModel, ClientDTO>
{
    @Override
    public String getMenuDisplayName()
    {
        return "Buscar cliente";
    }

    @Override
    protected ClientViewModel findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getClientViewModel();
    }

    @Override
    protected List<MenuOption> buildOptions()
    {
        List<MenuOption> options = super.buildOptions();

        List<MenuOption> newOptions = List.of
        (
            new MenuOption("Editar cliente", this::showEditClientScreen)
        );

        options.addAll(0, newOptions);
        return options;
    }

    private MenuResult showEditClientScreen(MenuManager menuManager)
    {
        return MenuResult.push(new EditClientMenu());
    }
}