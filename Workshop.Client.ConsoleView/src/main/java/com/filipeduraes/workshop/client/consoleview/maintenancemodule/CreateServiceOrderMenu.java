// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientModuleMenu;

/**
 *
 * @author Filipe Durães
 */
public class CreateServiceOrderMenu implements IWorkshopMenu 
{
    String[] clientSelectionOptions = new String[] { "Selecionar Cliente", "X Cancelar" };

    @Override
    public String getMenuDisplayName() 
    {
        return "Criar ordem de serviço";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        final ClientViewModel clientViewModel = menuManager.getClientViewModel();

        if(!clientViewModel.hasSelectedClient())
        {
            System.out.println("- CLIENTE");
            int selectedOption = ConsoleInput.readOptionFromList("Selecione o cliente", clientSelectionOptions);

            if(selectedOption == 0)
            {
                return MenuResult.push(new ClientModuleMenu());
            }

            return MenuResult.pop();
        }

        return MenuResult.none();
    }
}
