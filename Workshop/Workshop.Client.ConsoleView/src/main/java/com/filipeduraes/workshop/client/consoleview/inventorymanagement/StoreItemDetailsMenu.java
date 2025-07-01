// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.inventorymanagement;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.util.List;

/**
 * Menu para exibição e gerenciamento dos detalhes de um item do inventário.
 * Permite visualizar informações detalhadas do item selecionado e acessar opções
 * para edição, reposição de estoque e registro de vendas. Caso nenhum item esteja
 * selecionado, redireciona para o menu de busca.
 *
 * Este menu centraliza as operações de gestão de itens do inventário.
 *
 * @author Filipe Durães
 */
public class StoreItemDetailsMenu extends EntityDetailsMenu<EntityViewModel<StoreItemDTO>, StoreItemDTO>
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new SearchStoreItemMenu());

    @Override
    public String getMenuDisplayName()
    {
        return "Buscar Item no Inventario";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(!menuManager.getViewModelRegistry().getInventoryViewModel().hasValidSelectedIndex())
        {
            return redirector.redirect();
        }

        redirector.reset();

        return super.showMenu(menuManager);
    }

    @Override
    protected List<MenuOption> buildOptions()
    {
        List<MenuOption> options = super.buildOptions();

        List<MenuOption> storeItemOptions = List.of
        (
            new MenuOption("Editar item do inventario", this::redirectToEditMenu),
            new MenuOption("Repor Estoque", this::redirectToRestockMenu),
            new MenuOption("Registrar Venda", this::redirectToPurchaseMenu)
        );

        options.addAll(0, storeItemOptions);
        return options;
    }

    @Override
    protected EntityViewModel<StoreItemDTO> findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getInventoryViewModel();
    }

    private MenuResult redirectToEditMenu(MenuManager menuManager)
    {
        return MenuResult.push(new EditStoreItemMenu());
    }

    private MenuResult redirectToRestockMenu(MenuManager menuManager)
    {
        return MenuResult.replace(new RestockStoreItemMenu());
    }

    private MenuResult redirectToPurchaseMenu(MenuManager menuManager)
    {
        return MenuResult.replace(new RegisterSaleMenu());
    }
}