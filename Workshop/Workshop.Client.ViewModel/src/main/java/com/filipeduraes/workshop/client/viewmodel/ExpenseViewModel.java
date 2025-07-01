// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ExpenseDTO;
import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar os dados e estados relacionados às despesas
 * da oficina, incluindo registro de novas despesas e relatórios mensais.
 * Esta classe implementa a interface IMonthReportViewModel para fornecer
 * funcionalidades de relatórios mensais de despesas.
 *
 * @author Filipe Durães
 */
public class ExpenseViewModel implements IMonthReportViewModel
{
    /**
     * Evento disparado quando é solicitado o relatório de despesas do mês.
     */
    public final Observer OnExpenseReportRequested = new Observer();
    
    /**
     * Evento disparado quando é solicitado o registro de uma nova despesa.
     */
    public final Observer OnExpenseRegisterRequested = new Observer();

    private ExpenseDTO selectedExpense;
    private String report = "";
    private boolean requestWasSuccessful = false;
    private int selectedMonth = -1;
    private int selectedYear = -1;

    /**
     * Define o mês selecionado para filtro de relatório.
     *
     * @param month mês a ser selecionado (1-12)
     */
    @Override
    public void setSelectedMonth(int month)
    {
        selectedMonth = month;
    }

    /**
     * Define o ano selecionado para filtro de relatório.
     *
     * @param year ano a ser selecionado
     */
    @Override
    public void setSelectedYear(int year)
    {
        selectedYear = year;
    }

    /**
     * Dispara o evento de solicitação de relatório de despesas.
     */
    @Override
    public void broadcastReportRequest()
    {
        OnExpenseReportRequested.broadcast();
    }

    /**
     * Obtém o relatório de despesas atual.
     *
     * @return relatório de despesas em formato string
     */
    @Override
    public String getReport()
    {
        return report;
    }

    /**
     * Verifica se a última requisição foi bem-sucedida.
     *
     * @return true se a requisição foi bem-sucedida, false caso contrário
     */
    @Override
    public boolean getRequestWasSuccessful()
    {
        return requestWasSuccessful;
    }

    /**
     * Obtém o mês selecionado para filtro.
     *
     * @return mês selecionado ou -1 se nenhum mês estiver selecionado
     */
    public int getSelectedMonth()
    {
        return selectedMonth;
    }

    /**
     * Obtém o ano selecionado para filtro.
     *
     * @return ano selecionado ou -1 se nenhum ano estiver selecionado
     */
    public int getSelectedYear()
    {
        return selectedYear;
    }

    /**
     * Define o status da última requisição.
     *
     * @param requestWasSuccessful true se a requisição foi bem-sucedida, false caso contrário
     */
    public void setRequestWasSuccessful(boolean requestWasSuccessful)
    {
        this.requestWasSuccessful = requestWasSuccessful;
    }

    /**
     * Define o relatório de despesas.
     *
     * @param report relatório de despesas em formato string
     */
    public void setReport(String report)
    {
        this.report = report;
    }

    /**
     * Obtém a despesa atualmente selecionada.
     *
     * @return DTO da despesa selecionada ou null se nenhuma estiver selecionada
     */
    public ExpenseDTO getSelectedExpense()
    {
        return selectedExpense;
    }

    /**
     * Define a despesa selecionada.
     *
     * @param expense DTO da despesa a ser selecionada
     */
    public void setSelectedExpense(ExpenseDTO expense)
    {
        this.selectedExpense = expense;
    }
}
