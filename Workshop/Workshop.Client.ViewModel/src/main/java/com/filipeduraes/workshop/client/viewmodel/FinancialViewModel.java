// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar os dados e estados relacionados às operações
 * financeiras da oficina, incluindo relatórios de balanço mensal.
 * Esta classe implementa a interface IMonthReportViewModel para fornecer
 * funcionalidades de relatórios mensais.
 *
 * @author Filipe Durães
 */
public class FinancialViewModel implements IMonthReportViewModel
{
    /**
     * Evento disparado quando é solicitado o relatório de balanço mensal.
     */
    public final Observer OnBalanceReportRequested = new Observer();

    private String report = "";
    private int selectedMonth = -1;
    private int selectedYear = -1;
    private boolean requestWasSuccessful = false;

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
     * Dispara o evento de solicitação de relatório de balanço.
     */
    @Override
    public void broadcastReportRequest()
    {
        OnBalanceReportRequested.broadcast();
    }

    /**
     * Obtém o relatório financeiro atual.
     *
     * @return relatório financeiro em formato string
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
     * Define o relatório financeiro.
     *
     * @param report relatório financeiro em formato string
     */
    public void setReport(String report)
    {
        this.report = report;
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
}
