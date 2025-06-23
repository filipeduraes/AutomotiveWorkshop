// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.vehiclemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

/**
 * Menu para gerenciamento de operações relacionadas a veículos.
 * Fornece opções para registro e seleção de veículos no sistema.
 *
 * @author Filipe Durães
 */
public class VehicleMenu implements IWorkshopMenu
{
    private final IWorkshopMenu[] menuOptions =
            {
                    new RegisterVehicleMenu(),
                    new VehicleSelectionMenu()
            };

    /**
     * Obtém o nome de exibição deste menu.
     *
     * @return o nome de exibição do menu
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Veiculo";
    }

    /**
     * Exibe o menu de veículos e processa a interação do usuário.
     *
     * @param menuManager o gerenciador de menus da aplicação
     * @return o resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        VehicleViewModel vehicleViewModel = menuManager.getViewModelRegistry().getVehicleViewModel();

        if (!vehicleViewModel.hasLoadedDTO())
        {
            IWorkshopMenu submenu = menuManager.showSubmenuOptions("Selecione um veiculo. O que deseja fazer?", menuOptions);

            if (submenu != null)
            {
                return MenuResult.push(submenu);
            }
        }

        return MenuResult.pop();
    }
}
