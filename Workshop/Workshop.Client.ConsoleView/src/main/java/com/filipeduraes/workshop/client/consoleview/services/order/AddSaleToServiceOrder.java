// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.inventorymanagement.RegisterSaleMenu;
import com.filipeduraes.workshop.client.viewmodel.InventoryViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

/**
 * Menu para adicionar vendas a uma ordem de serviço.
 * Permite registrar uma nova venda e associá-la à ordem de serviço atual,
 * redirecionando para o menu de registro de venda se necessário.
 *
 * @author Filipe Durães
 */
public class AddSaleToServiceOrder implements IWorkshopMenu
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new RegisterSaleMenu());

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Adicionar venda a ordem de servico";
    }

    /**
     * Exibe o menu de adição de venda à ordem de serviço.
     * Se não houver uma venda selecionada, redireciona para o menu de registro.
     * Caso contrário, adiciona a venda à ordem de serviço atual.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        InventoryViewModel inventoryViewModel = menuManager.getViewModelRegistry().getInventoryViewModel();

        if(inventoryViewModel.getSaleID() == null)
        {
            return redirector.redirect();
        }
        redirector.reset();

        ServiceOrderViewModel serviceOrderViewModel = menuManager.getViewModelRegistry().getServiceOrderViewModel();
        serviceOrderViewModel.OnAddSaleRequested.broadcast();

        if(serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.println("Venda adicionada com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel adicionar a venda, tente novamente.");
        }

        return MenuResult.pop();
    }
}
