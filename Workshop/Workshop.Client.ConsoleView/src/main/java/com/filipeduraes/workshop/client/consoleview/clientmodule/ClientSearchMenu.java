// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.consoleview.input.CPFInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.PhoneInputValidator;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu responsável por pesquisar e exibir clientes cadastrados no sistema.
 * Permite que o usuário busque clientes pelo nome, visualize os resultados
 * da pesquisa e selecione um cliente específico para ver seus detalhes.
 *
 * @author Filipe Durães
 */
public class ClientSearchMenu extends EntitySearchMenu<ClientViewModel, ClientDTO>
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return o nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Pesquisar Cliente";
    }

    @Override
    protected Map<FieldType, SearchInputStrategy> getSearchInputStrategies()
    {
        Map<FieldType, SearchInputStrategy> strategies = new LinkedHashMap<>();

        strategies.put(FieldType.NAME, new SearchInputStrategy("Digite o nome do cliente"));
        strategies.put(FieldType.CPF, new SearchInputStrategy("Digite o CPF do cliente", new CPFInputValidator()));
        strategies.put(FieldType.EMAIL, new SearchInputStrategy("Digite o email do cliente", new EmailInputValidator()));
        strategies.put(FieldType.PHONE, new SearchInputStrategy("Digite o celular do cliente", new PhoneInputValidator()));

        return strategies;
    }

    @Override
    protected ClientViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getClientViewModel();
    }
}
