// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.financial;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.general.ShowMonthReportMenu;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;

/**
 * Menu de exibição do relatório mensal de despesas.
 * Permite ao usuário visualizar todas as despesas registradas no mês atual,
 * fornecendo uma visão geral dos gastos da oficina.
 *
 * Este menu é utilizado para controle financeiro e análise de custos.
 *
 * @author Filipe Durães
 */
public class ShowMonthExpensesMenu extends ShowMonthReportMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Mostrar despesas do mes";
    }

    @Override
    protected IMonthReportViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getExpenseViewModel();
    }
}
