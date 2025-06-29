// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.employee;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.general.EntitySearchMenu;
import com.filipeduraes.workshop.client.consoleview.general.SearchInputStrategy;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Menu para realizar pesquisa e seleção de colaboradores no sistema.
 * Permite buscar colaboradores por nome, email ou cargo.
 *
 * @author Filipe Durães
 */
public class EmployeeSearchMenu extends EntitySearchMenu<EmployeeViewModel, EmployeeDTO>
{
    /**
     * Obtém o nome de exibição deste menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Pesquisar Colaborador";
    }

    @Override
    protected Map<FieldType, SearchInputStrategy> getSearchInputStrategies()
    {
        Map<FieldType, SearchInputStrategy> strategies = new LinkedHashMap<>();

        strategies.put(FieldType.NAME, new SearchInputStrategy("Digite o nome do colaborador"));
        strategies.put(FieldType.EMAIL, new SearchInputStrategy("Digite o email do colaborador"));
        strategies.put(FieldType.ROLE, new SearchInputStrategy("Insira o cargo do colaborador", EmployeeRoleDTO.values()));
        strategies.put(FieldType.LOCAL_USER, new SearchInputStrategy());

        return strategies;
    }

    @Override
    protected EmployeeViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getEmployeeViewModel();
    }
}