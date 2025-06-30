// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.console;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.administrator.FirstAccessMenu;
import com.filipeduraes.workshop.client.consoleview.employee.LogInMenu;
import com.filipeduraes.workshop.client.model.*;

import com.filipeduraes.workshop.client.consoleview.MenuManager;

import com.filipeduraes.workshop.client.model.serviceorder.ServiceOrderController;
import com.filipeduraes.workshop.client.viewmodel.*;

import com.filipeduraes.workshop.core.Workshop;

/**
 * Aplicação cliente de console para o sistema de oficina mecânica.
 * Esta classe serve como ponto de entrada principal e gerencia a injeção de dependências
 * para todos os componentes do sistema de gerenciamento da oficina.
 *
 * @author Filipe Durães
 */
public class WorkshopClient
{
    /**
     * Ponto de entrada da aplicação console da oficina.
     * Inicializa todos os componentes do sistema, injeta dependências e inicia a interface baseada em menus.
     * Cria e gerencia o ciclo de vida dos controladores e view models.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args)
    {
        String title = """
         ___________ _____ _____ _____ _   _   ___
        |  _  |  ___|_   _/  __ \\_   _| \\ | | / _ \\
        | | | | |_    | | | /  \\/ | | |  \\| |/ /_\\ \\
        | | | |  _|   | | | |     | | | . ` ||  _  |
        \\ \\_/ / |    _| |_| \\__/\\_| |_| |\\  || | | |
         \\___/\\_|    \\___/ \\____/\\___/\\_| \\_/\\_| |_/
        """;

        System.out.print(title);
        Workshop workshop = new Workshop(false);

        ViewModelRegistry viewModelRegistry = new ViewModelRegistry();

        EmployeeController employeeController = new EmployeeController(viewModelRegistry.getEmployeeViewModel(), workshop.getAuthModule());
        ClientController clientController = new ClientController(viewModelRegistry.getClientViewModel(), workshop.getClientRepository());
        VehicleController vehicleController = new VehicleController(viewModelRegistry, workshop);
        ServiceOrderController serviceOrderController = new ServiceOrderController(viewModelRegistry, workshop);
        InventoryController inventoryController = new InventoryController(viewModelRegistry, workshop);
        ServiceItemController serviceItemController = new ServiceItemController(viewModelRegistry, workshop);

        boolean isFirstAccess = workshop.getAuthModule().isFirstAccess();
        IWorkshopMenu initialMenu = isFirstAccess ? new FirstAccessMenu() : new LogInMenu();

        MenuManager menuManager = new MenuManager(initialMenu, viewModelRegistry);
        menuManager.run();

        employeeController.dispose();
        clientController.dispose();
        vehicleController.dispose();
        serviceOrderController.dispose();
        inventoryController.dispose();
        serviceItemController.dispose();

        workshop.dispose();
    }
}