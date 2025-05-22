// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.console;

import com.filipeduraes.workshop.client.model.LoginController;
import com.filipeduraes.workshop.client.model.ClientController;

import com.filipeduraes.workshop.client.consoleview.login.EnterUserMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;

import com.filipeduraes.workshop.client.model.VehicleController;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;

import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
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
    private static MenuManager menuManager;

    /**
     * Ponto de entrada da aplicação console da oficina.
     * Inicializa todos os componentes do sistema, injeta dependências e inicia a interface baseada em menus.
     * Cria e gerencia o ciclo de vida dos controladores e view models.
     *
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args)
    {
        String title = " ___________ _____ _____ _____ _   _   ___  \n"
                + "|  _  |  ___|_   _/  __ \\_   _| \\ | | / _ \\ \n"
                + "| | | | |_    | | | /  \\/ | | |  \\| |/ /_\\ \\\n"
                + "| | | |  _|   | | | |     | | | . ` ||  _  |\n"
                + "\\ \\_/ / |    _| |_| \\__/\\_| |_| |\\  || | | |\n"
                + " \\___/\\_|    \\___/ \\____/\\___/\\_| \\_/\\_| |_/";

        System.out.println(title);
        Workshop workshop = new Workshop(false);

        UserInfoViewModel userInfoViewModel = new UserInfoViewModel();
        ClientViewModel clientViewModel = new ClientViewModel();
        VehicleViewModel vehicleViewModel = new VehicleViewModel();

        LoginController loginController = new LoginController(userInfoViewModel, workshop.getAuthModule());
        ClientController clientController = new ClientController(clientViewModel, workshop.getClientModule());
        VehicleController vehicleController = new VehicleController(vehicleViewModel, clientViewModel, workshop.getVehicleModule(), workshop.getClientModule());

        menuManager = new MenuManager(new EnterUserMenu(), userInfoViewModel, clientViewModel, vehicleViewModel);
        menuManager.run();

        loginController.dispose();
        clientController.dispose();
        vehicleController.dispose();

        workshop.dispose();
    }
}