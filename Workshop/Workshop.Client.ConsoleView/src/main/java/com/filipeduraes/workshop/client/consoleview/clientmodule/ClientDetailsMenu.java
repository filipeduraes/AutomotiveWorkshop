package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

public class ClientDetailsMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Buscar cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ClientViewModel clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();

        if(!clientViewModel.hasSelectedClient())
        {
            boolean confirmation = ConsoleInput.readConfirmation("Deseja pesquisar um cliente existente?");

            if(confirmation)
            {
                return MenuResult.push(new ClientSearchMenu());
            }

            System.out.println("Busca cancelada. Voltando...");
            return MenuResult.pop();
        }

        System.out.printf("Cliente selecionado:%n%s%n%n", clientViewModel.getClient());
        return MenuResult.pop();
    }
}