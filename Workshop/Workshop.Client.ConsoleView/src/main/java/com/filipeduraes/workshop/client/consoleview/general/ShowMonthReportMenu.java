// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.IMonthReportViewModel;

/**
 * Menu abstrato para exibição de relatórios mensais.
 * Fornece funcionalidades comuns para solicitar período de relatório e exibir
 * os dados formatados para o usuário, permitindo seleção de mês e ano específicos.
 *
 * Este menu serve como base para diferentes tipos de relatórios mensais do sistema.
 *
 * @author Filipe Durães
 */
public abstract class ShowMonthReportMenu implements IWorkshopMenu
{
    /**
     * Exibe o menu de relatório mensal, permitindo ao usuário selecionar
     * o período desejado e exibindo o relatório correspondente.
     *
     * @param menuManager gerenciador de menus que controla a navegação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        boolean useCurrentMonth = ConsoleInput.readConfirmation("Deseja mostrar o relatorio do mes atual?");
        int month = -1;
        int year = -1;

        if(!useCurrentMonth)
        {
            month = ConsoleInput.readLineInteger("Insira o mes desejado (1-12)", 1, 12);

            boolean useCurrentYear = ConsoleInput.readConfirmation("Deseja mostrar o relatorio do ano atual?");

            if(!useCurrentYear)
            {
                year = ConsoleInput.readLineInteger("Insira o ano desejado", 2000, 2100);
            }
        }

        IMonthReportViewModel employeeViewModel = getViewModel(menuManager);
        employeeViewModel.setSelectedMonth(month);
        employeeViewModel.setSelectedYear(year);

        employeeViewModel.broadcastReportRequest();

        String report = employeeViewModel.getReport();

        if(employeeViewModel.getRequestWasSuccessful() && report != null && !report.isBlank())
        {
            System.out.println(report);
        }
        else
        {
            System.out.println("Nao foi possivel obter o relatorio do mes selecionado.");
        }

        if(canShowConfirmation())
        {
            System.out.println();
            ConsoleInput.readOptionFromList("O que deseja fazer?", new String[]{"X Voltar"});
        }

        return MenuResult.pop();
    }

    /**
     * Obtém o ViewModel responsável por gerar o relatório mensal.
     * Deve ser implementado pelas classes filhas para fornecer o ViewModel
     * específico do tipo de relatório.
     *
     * @param menuManager gerenciador de menus atual
     * @return ViewModel do relatório mensal
     */
    protected abstract IMonthReportViewModel getViewModel(MenuManager menuManager);

    /**
     * Verifica se deve exibir a confirmação de saída após mostrar o relatório.
     * Pode ser sobrescrito pelas classes filhas para personalizar o comportamento.
     *
     * @return true se deve exibir confirmação, false caso contrário
     */
    protected boolean canShowConfirmation()
    {
        return true;
    }
}
