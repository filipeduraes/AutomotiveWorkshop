// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.client.consoleview.auth.EmployeeMenu;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientMenu;
import com.filipeduraes.workshop.client.consoleview.maintenancemodule.ServicesMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleMenu;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;

import java.util.Arrays;
import java.util.stream.Stream;

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

    private final IWorkshopMenu[] administratorMenus =
    {
        new EmployeeMenu()
    };

    @Override
    public String getMenuDisplayName() 
    {
        return "Menu Principal";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        IWorkshopMenu[] selectableMenus = menus;
        EmployeeDTO loggedUser = menuManager.getViewModelRegistry().getEmployeeViewModel().getLoggedUser();

        if(loggedUser.getRole() == EmployeeRoleDTO.ADMINISTRATOR)
        {
            selectableMenus = Stream.concat(Arrays.stream(menus), Arrays.stream(administratorMenus))
                                    .toArray(IWorkshopMenu[]::new);
        }

        IWorkshopMenu selectedOption = menuManager.showSubmenuOptions("Qual menu deseja acessar?", selectableMenus);
        
        if(selectedOption != null)
        {
            return MenuResult.push(selectedOption);
        }
        
        return MenuResult.exit();
    }    
}
