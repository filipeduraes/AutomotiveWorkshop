// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.financial;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.general.ShowMonthReportMenu;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;

/**
 * Menu de exibição do balanço financeiro mensal.
 * Permite ao usuário visualizar o balanço financeiro da oficina no mês atual,
 * mostrando a diferença entre receitas e despesas.
 *
 * Este menu é utilizado para análise da performance financeira da oficina.
 *
 * @author Filipe Durães
 */
public class ShowMonthBalanceMenu extends ShowMonthReportMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Mostrar balanco do mes";
    }

    @Override
    protected IMonthReportViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getFinancialViewModel();
    }
}
