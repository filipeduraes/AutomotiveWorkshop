// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.client;

import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.consoleview.input.CPFInputValidator;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.PhoneInputValidator;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Menu de pesquisa de clientes.
 * Permite ao usuário buscar clientes cadastrados no sistema por diferentes campos,
 * como nome, CPF, email e telefone, facilitando a localização e seleção de clientes
 * para operações futuras.
 *
 * @author Filipe Durães
 */
public class ClientSearchMenu extends EntitySearchMenu<EntityViewModel<ClientDTO>, ClientDTO>
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
    protected EntityViewModel<ClientDTO> getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getClientViewModel();
    }
}
