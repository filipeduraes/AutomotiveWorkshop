// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.client;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.util.List;

/**
 * Menu para exibição e gerenciamento dos detalhes de um cliente.
 * Permite visualizar informações detalhadas e editar dados do cliente selecionado.
 *
 * @author Filipe Durães
 */
public class ClientDetailsMenu extends EntityDetailsMenu<EntityViewModel<ClientDTO>, ClientDTO>
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new ClientSearchMenu());

    /**
     * Obtém o nome de exibição deste menu.
     *
     * @return o nome de exibição do menu
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Buscar cliente";
    }

    /**
     * Exibe o menu de detalhes do cliente e processa a interação do usuário.
     *
     * @param menuManager o gerenciador de menus da aplicação
     * @return o resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(!menuManager.getViewModelRegistry().getClientViewModel().hasValidSelectedIndex())
        {
            return redirector.redirect();
        }

        redirector.reset();
        return super.showMenu(menuManager);
    }

    @Override
    protected EntityViewModel<ClientDTO> findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getClientViewModel();
    }

    @Override
    protected List<MenuOption> buildOptions()
    {
        List<MenuOption> options = super.buildOptions();

        List<MenuOption> newOptions = List.of
        (
            new MenuOption("Editar cliente", this::showEditClientScreen)
        );

        options.addAll(0, newOptions);
        return options;
    }

    private MenuResult showEditClientScreen(MenuManager menuManager)
    {
        return MenuResult.push(new EditClientMenu());
    }
}