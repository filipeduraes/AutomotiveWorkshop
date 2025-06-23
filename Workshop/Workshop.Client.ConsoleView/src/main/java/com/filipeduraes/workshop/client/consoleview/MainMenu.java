// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.client.consoleview.auth.EmployeeDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.auth.RegisterEmployeeMenu;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientRegistrationMenu;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.consoleview.maintenancemodule.*;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.RegisterVehicleMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleDetailsMenu;
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
public class MainMenu extends RedirectMenu
{
    public MainMenu(MenuManager menuManager)
    {
        super("Menu Principal", getMenuOptions(menuManager));
    }

    private static IWorkshopMenu[] getMenuOptions(MenuManager menuManager)
    {
        final IWorkshopMenu[] regularMenus =
        {
            new RedirectMenu
            (
                "Servico", new IWorkshopMenu[]
                {
                    new CreateServiceOrderMenu(),
                    new QueryOpenedServicesMenu(),
                    new QueryUserServicesMenu(),
                    new QueryGeneralServicesMenu()
                }
            ),
            new RedirectMenu
            (
                "Cliente", new IWorkshopMenu[]
                {
                    new ClientRegistrationMenu(),
                    new ClientDetailsMenu()
                }
            ),
            new RedirectMenu
            (
                "Veiculo", new IWorkshopMenu[]
                {
                    new RegisterVehicleMenu(),
                    new VehicleDetailsMenu()
                }
            ),
        };

        final IWorkshopMenu[] administratorMenus =
        {
            new RedirectMenu
            (
                "Colaborador", new IWorkshopMenu[]
                {
                    new RegisterEmployeeMenu(),
                    new EmployeeDetailsMenu()
                }
            )
        };

        IWorkshopMenu[] selectableMenus = regularMenus;
        EmployeeDTO loggedUser = menuManager.getViewModelRegistry().getEmployeeViewModel().getLoggedUser();

        if (loggedUser.getRole() == EmployeeRoleDTO.ADMINISTRATOR)
        {
            selectableMenus = Stream.concat(Arrays.stream(regularMenus), Arrays.stream(administratorMenus))
                                    .toArray(IWorkshopMenu[]::new);
        }

        return selectableMenus;
    }
}
