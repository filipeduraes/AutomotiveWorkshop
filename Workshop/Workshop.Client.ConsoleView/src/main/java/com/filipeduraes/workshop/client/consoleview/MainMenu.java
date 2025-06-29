// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.client.consoleview.employee.EmployeeDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.employee.RegisterEmployeeMenu;
import com.filipeduraes.workshop.client.consoleview.client.ClientDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.client.ClientRegistrationMenu;
import com.filipeduraes.workshop.client.consoleview.financial.ShowMonthSalesMenu;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.RegisterNewStoreItemMenu;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.RegisterPurchaseMenu;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.RestockStoreItemMenu;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.StoreItemDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.services.order.CreateServiceOrderMenu;
import com.filipeduraes.workshop.client.consoleview.services.order.QueryGeneralServicesMenu;
import com.filipeduraes.workshop.client.consoleview.services.order.QueryOpenedServicesMenu;
import com.filipeduraes.workshop.client.consoleview.services.order.QueryUserServicesMenu;
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
    private static final RedirectMenu serviceMenu = new RedirectMenu
    (
        "Ordem de Servico", new IWorkshopMenu[]
        {
            new CreateServiceOrderMenu(),
            new QueryOpenedServicesMenu(),
            new QueryUserServicesMenu(),
            new QueryGeneralServicesMenu()
        }
    );

    private static final RedirectMenu clientMenu = new RedirectMenu
    (
        "Cliente", new IWorkshopMenu[]
        {
            new ClientRegistrationMenu(),
            new ClientDetailsMenu()
        }
    );

    private static final RedirectMenu vehicleMenu = new RedirectMenu
    (
        "Veiculo", new IWorkshopMenu[]
        {
            new RegisterVehicleMenu(),
            new VehicleDetailsMenu()
        }
    );

    private static final RedirectMenu inventoryManagementMenu = new RedirectMenu
    (
        "Gerenciar catalogo", new IWorkshopMenu[]
        {
            new RedirectMenu("Gerenciar itens do inventario", new IWorkshopMenu[]
            {
                new RegisterNewStoreItemMenu(),
                new RestockStoreItemMenu(),
                new StoreItemDetailsMenu(),
                new RegisterPurchaseMenu()
            }),
            new RedirectMenu("Gerenciar servicos do catalogo", new IWorkshopMenu[]
            {
            })
        }
    );

    private static final RedirectMenu employeeMenu = new RedirectMenu
    (
        "Colaborador", new IWorkshopMenu[]
        {
            new RegisterEmployeeMenu(),
            new EmployeeDetailsMenu()
        }
    );

    private static final RedirectMenu financialMenu = new RedirectMenu
    (
        "Financeiro", new IWorkshopMenu[]
        {
            new ShowMonthSalesMenu()
        }
    );

    private final static IWorkshopMenu[] regularMenus =
    {
        serviceMenu,
        clientMenu,
        vehicleMenu,
        inventoryManagementMenu
    };

    private final static IWorkshopMenu[] administratorMenus =
    {
        employeeMenu,
        financialMenu
    };

    public MainMenu(MenuManager menuManager)
    {
        super("Menu Principal", getMenuOptions(menuManager));
    }

    private static IWorkshopMenu[] getMenuOptions(MenuManager menuManager)
    {
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
