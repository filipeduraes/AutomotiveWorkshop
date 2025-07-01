// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;

import com.filipeduraes.workshop.client.consoleview.employee.ClockInMenu;
import com.filipeduraes.workshop.client.consoleview.employee.EmployeeDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.employee.RegisterEmployeeMenu;
import com.filipeduraes.workshop.client.consoleview.client.ClientDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.client.ClientRegistrationMenu;
import com.filipeduraes.workshop.client.consoleview.employee.ShowMonthClockInMenu;
import com.filipeduraes.workshop.client.consoleview.financial.RegisterExpenseMenu;
import com.filipeduraes.workshop.client.consoleview.financial.ShowMonthBalanceMenu;
import com.filipeduraes.workshop.client.consoleview.financial.ShowMonthExpensesMenu;
import com.filipeduraes.workshop.client.consoleview.financial.ShowMonthSalesMenu;
import com.filipeduraes.workshop.client.consoleview.general.RedirectMenu;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.RegisterStoreItemMenu;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.StoreItemDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.services.item.RegisterServiceItemMenu;
import com.filipeduraes.workshop.client.consoleview.services.item.ServiceItemDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.services.order.*;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.RegisterVehicleMenu;
import com.filipeduraes.workshop.client.consoleview.vehiclemodule.VehicleDetailsMenu;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Menu principal do sistema da oficina.
 * Este menu é o ponto de entrada para a navegação entre os diferentes módulos do sistema,
 * como ordens de serviço, clientes, veículos, inventário, colaboradores e financeiro.
 * As opções apresentadas são adaptadas conforme o perfil do usuário logado (administrador ou não).
 *
 * Permite ao usuário acessar rapidamente as principais funcionalidades do sistema,
 * centralizando a navegação e o fluxo de trabalho.
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
            new QueryServicesMenu(),
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
                new RegisterStoreItemMenu(),
                new StoreItemDetailsMenu()
            }),
            new RedirectMenu("Gerenciar servicos do catalogo", new IWorkshopMenu[]
            {
                new RegisterServiceItemMenu(),
                new ServiceItemDetailsMenu()
            })
        }
    );

    private static final RedirectMenu employeeMenu = new RedirectMenu
    (
        "Colaborador", new IWorkshopMenu[]
        {
            new RegisterEmployeeMenu(),
            new EmployeeDetailsMenu(),
            new ShowMonthClockInMenu()
        }
    );

    private static final RedirectMenu financialMenu = new RedirectMenu
    (
        "Financeiro", new IWorkshopMenu[]
        {
            new ShowMonthSalesMenu(),
            new ShowMonthExpensesMenu(),
            new ShowMonthBalanceMenu()
        }
    );

    private final static IWorkshopMenu[] regularMenus =
    {
        new ClockInMenu(),
        serviceMenu,
        clientMenu,
        vehicleMenu,
        inventoryManagementMenu,
        new RegisterExpenseMenu()
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