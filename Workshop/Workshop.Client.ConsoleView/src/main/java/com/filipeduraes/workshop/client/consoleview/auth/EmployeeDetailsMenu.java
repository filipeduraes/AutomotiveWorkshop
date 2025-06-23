// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;

import java.util.List;

/**
 * Menu para exibição e gerenciamento de detalhes de funcionários.
 * Fornece funcionalidades para visualizar informações detalhadas de um funcionário
 * e realizar operações relacionadas.
 *
 * @author Filipe Durães
 */
public class EmployeeDetailsMenu extends EntityDetailsMenu<EmployeeViewModel, EmployeeDTO>
{
    /**
     * Obtém o ViewModel de funcionários do registro de ViewModels.
     *
     * @param menuManager gerenciador de menus que contém o registro de ViewModels
     * @return ViewModel de funcionários
     */
    @Override
    protected EmployeeViewModel findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getEmployeeViewModel();
    }

    /**
     * Exibe o menu de detalhes do funcionário.
     * Se não houver funcionário selecionado, oferece a opção de pesquisa.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if (!findViewModel(menuManager).hasValidSelectedIndex())
        {
            boolean searchEmployee = ConsoleInput.readConfirmation("Deseja pesquisar um funcionario?");

            if (!searchEmployee)
            {
                System.out.println("Operacao cancelada. Voltando...");
                return MenuResult.pop();
            }

            return MenuResult.push(new EmployeeSearchMenu());
        }

        return super.showMenu(menuManager);
    }

    @Override
    protected List<MenuOption> buildOptions()
    {
        List<MenuOption> options = super.buildOptions();

        List<MenuOption> newOptions = List.of
        (
            new MenuOption("Editar colaborador", this::showEditEmployeeScreen)
        );

        options.addAll(0, newOptions);
        return options;
    }

    private MenuResult showEditEmployeeScreen(MenuManager menuManager)
    {
        return MenuResult.push(new EditEmployeeMenu());
    }
}