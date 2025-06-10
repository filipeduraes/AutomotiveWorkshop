// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.console;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.administrator.FirstAccessMenu;
import com.filipeduraes.workshop.client.consoleview.login.LogInMenu;
import com.filipeduraes.workshop.client.model.LoginController;
import com.filipeduraes.workshop.client.model.ClientController;

import com.filipeduraes.workshop.client.consoleview.MenuManager;

import com.filipeduraes.workshop.client.model.MaintenanceController;
import com.filipeduraes.workshop.client.model.VehicleController;
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

        LoginController loginController = new LoginController(viewModelRegistry.getUserInfoViewModel(), workshop.getAuthModule());
        ClientController clientController = new ClientController(viewModelRegistry.getClientViewModel(), workshop.getClientModule());
        VehicleController vehicleController = new VehicleController(viewModelRegistry, workshop);
        MaintenanceController maintenanceController = new MaintenanceController(viewModelRegistry, workshop);

        boolean isFirstAccess = workshop.getAuthModule().isFirstAccess();
        IWorkshopMenu initialMenu = isFirstAccess ? new FirstAccessMenu() : new LogInMenu();

        MenuManager menuManager = new MenuManager(initialMenu, viewModelRegistry);
        menuManager.run();

        loginController.dispose();
        clientController.dispose();
        vehicleController.dispose();
        maintenanceController.dispose();

        workshop.dispose();
    }
}