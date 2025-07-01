// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.employee;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.general.ShowMonthReportMenu;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;

/**
 * Menu de exibição do relatório mensal de pontos dos colaboradores.
 * Permite ao usuário visualizar todos os registros de entrada, saída e pausas
 * dos funcionários no mês selecionado, fornecendo controle de jornada de trabalho.
 *
 * Este menu é utilizado para gestão de recursos humanos e controle de presença.
 *
 * @author Filipe Durães
 */
public class ShowMonthClockInMenu extends ShowMonthReportMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Mostrar pontos do mes";
    }

    @Override
    protected IMonthReportViewModel getViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getEmployeeViewModel();
    }
}