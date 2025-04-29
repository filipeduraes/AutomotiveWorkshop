// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

/**
 *
 * @author Filipe Durães
 */
public class MaintenanceModuleMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] submenus =
    {
        new CreateServiceOrderMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Modulo de Manutenção";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        /*
        [0] Gerar ordem de serviço
        [1] Listar agendamentos preliminares
        [2] Listar ordens de serviço ativos
        */
        IWorkshopMenu selectedSubmenu = menuManager.showSubmenuOptions("Escolha a opção: ", submenus);

        if(selectedSubmenu == null)
        {
            return false;
        }

        menuManager.pushMenu(selectedSubmenu);
        return false;
    }
}
