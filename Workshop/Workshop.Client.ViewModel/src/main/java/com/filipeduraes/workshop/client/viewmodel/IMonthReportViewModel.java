// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

/**
 * Interface que define o contrato para ViewModels que suportam relatórios mensais.
 * Esta interface fornece métodos para gerenciar filtros de data e solicitar
 * relatórios baseados em mês e ano específicos.
 *
 * @author Filipe Durães
 */
public interface IMonthReportViewModel
{
    /**
     * Define o mês selecionado para filtro de relatório.
     *
     * @param month mês a ser selecionado (1-12)
     */
    void setSelectedMonth(int month);
    
    /**
     * Define o ano selecionado para filtro de relatório.
     *
     * @param year ano a ser selecionado
     */
    void setSelectedYear(int year);
    
    /**
     * Dispara o evento de solicitação de relatório mensal.
     * Este método deve ser implementado para notificar quando um relatório
     * deve ser gerado com base nos filtros de data configurados.
     */
    void broadcastReportRequest();
    
    /**
     * Obtém o relatório atual.
     *
     * @return relatório em formato string
     */
    String getReport();
    
    /**
     * Verifica se a última requisição foi bem-sucedida.
     *
     * @return true se a requisição foi bem-sucedida, false caso contrário
     */
    boolean getRequestWasSuccessful();
}
