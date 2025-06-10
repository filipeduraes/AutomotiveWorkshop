// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientMenu;
import com.filipeduraes.workshop.client.consoleview.maintenancemodule.ServicesMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleMenu;

/**
 * Menu principal do sistema que permite a navegação entre os diferentes módulos.
 * Responsável por apresentar e gerenciar as opções de acesso aos módulos de 
 * manutenção e cliente.
 *
 * @author Filipe Durães
 */
public class MainMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menus = 
    {
        new ServicesMenu(),
        new ClientMenu(),
        new VehicleMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Menu Principal";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual menu deseja acessar?", menus);
        
        if(selectedOption != null)
        {
            return MenuResult.push(selectedOption);
        }
        
        return MenuResult.exit();
    }    
}
