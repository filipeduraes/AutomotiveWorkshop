// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientModuleMenu;

/**
 *
 * @author Filipe Durães
 */
public class CreateServiceOrderMenu implements IWorkshopMenu 
{

    @Override
    public String getMenuDisplayName() 
    {
        return "Criar ordem de serviço";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        final ClientViewModel clientViewModel = menuManager.getClientViewModel();

        if(!clientViewModel.hasSelectedClient())
        {
            System.out.println("- CLIENTE");
            System.out.println("[0] Selecionar cliente");
            System.out.println("[1] Cancelar");

            int selectedOption = ConsoleInput.readLineInteger();

            if(selectedOption == 0)
            {
                menuManager.pushMenu(new ClientModuleMenu());
            }

            return selectedOption == 1;
        }

        return true;
    }
}
