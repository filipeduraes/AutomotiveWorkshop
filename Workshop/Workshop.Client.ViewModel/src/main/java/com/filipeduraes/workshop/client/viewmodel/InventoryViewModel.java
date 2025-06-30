// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.StoreItemDTO;
import com.filipeduraes.workshop.utils.Observer;

import java.util.UUID;

/**
 * ViewModel responsável por gerenciar os dados da interface de inventário,
 * permitindo operações de reestoque e registro de vendas de itens.
 *
 * @author Filipe Durães
 */
public class InventoryViewModel extends EntityViewModel<StoreItemDTO> implements IMonthReportViewModel
{
    /**
     * Evento disparado quando é solicitado o reabastecimento de um item no estoque.
     */
    public final Observer OnItemRestockRequest = new Observer();

    /**
     * Evento disparado quando é solicitado o registro de uma nova venda.
     */
    public final Observer OnRegisterPurchaseRequest = new Observer();

    /**
     * Evento disparado quando o relatório de vendas do mês é solicitado.
     */
    public final Observer OnMonthSalesReportRequest = new Observer();

    private UUID saleID;
    private String saleTotalPrice = "";
    private String monthSaleReport = "";

    private int saleQuantity = 1;
    private int restockAmount = 1;

    private int selectedYear = -1;
    private int selectedMonth = -1;

    /**
     * Obtém a quantidade de itens para reestoque.
     *
     * @return quantidade para reestoque
     */
    public int getRestockAmount()
    {
        return restockAmount;
    }

    /**
     * Define a quantidade de itens para reestoque.
     *
     * @param restockAmount quantidade maior que zero para reestoque
     */
    public void setRestockAmount(int restockAmount)
    {
        if (restockAmount > 0)
        {
            this.restockAmount = restockAmount;
        }
    }

    /**
     * Obtém a quantidade de itens para venda.
     *
     * @return quantidade para venda
     */
    public int getSaleQuantity()
    {
        return saleQuantity;
    }

    /**
     * Define a quantidade de itens para venda.
     *
     * @param saleQuantity quantidade maior que zero para venda
     */
    public void setSaleQuantity(int saleQuantity)
    {
        if (saleQuantity > 0)
        {
            this.saleQuantity = saleQuantity;
        }
    }

    /**
     * Obtém o ID da venda registrada.
     *
     * @return ID único da venda
     */
    public UUID getSaleID()
    {
        return saleID;
    }

    /**
     * Define o ID da venda registrada.
     *
     * @param saleID ID único da venda
     */
    public void setSaleID(UUID saleID)
    {
        this.saleID = saleID;
    }

    /**
     * Obtém o preço total da venda formatado.
     *
     * @return preço total da venda em formato string
     */
    public String getSaleTotalPrice()
    {
        return saleTotalPrice;
    }

    /**
     * Define o preço total da venda.
     *
     * @param saleTotalPrice preço total da venda em formato string
     */
    public void setSaleTotalPrice(String saleTotalPrice)
    {
        this.saleTotalPrice = saleTotalPrice;
    }

    /**
     * Obtém o relatório de vendas do mês.
     *
     * @return relatório de vendas do mês em formato string
     */
    @Override
    public String getReport()
    {
        return monthSaleReport;
    }

    /**
     * Define o relatório de vendas do mês.
     *
     * @param monthSaleReport relatório de vendas do mês em formato string
     */
    public void setReport(String monthSaleReport)
    {
        this.monthSaleReport = monthSaleReport;
    }

    /**
     * Obtém o ano selecionado para filtro.
     *
     * @return ano selecionado ou −1 se nenhum ano estiver selecionado
     */
    public int getSelectedYear()
    {
        return selectedYear;
    }

    /**
     * Define o ano selecionado para filtro.
     *
     * @param selectedYear ano a ser selecionado
     */
    @Override
    public void setSelectedYear(int selectedYear)
    {
        this.selectedYear = selectedYear;
    }

    /**
     * Obtém o mês selecionado para filtro.
     *
     * @return mês selecionado ou −1 se nenhum mês estiver selecionado
     */
    public int getSelectedMonth()
    {
        return selectedMonth;
    }

    /**
     * Define o mês selecionado para filtro.
     *
     * @param selectedMonth mês a ser selecionado
     */
    @Override
    public void setSelectedMonth(int selectedMonth)
    {
        this.selectedMonth = selectedMonth;
    }

    @Override
    public void broadcastReportRequest()
    {
        OnMonthSalesReportRequest.broadcast();
    }

    /**
     * Reinicia a data selecionada para os valores padrão.
     * O ano e mês selecionados são definidos como −1.
     */
    public void resetSelectedDate()
    {
        selectedYear = -1;
        selectedMonth = -1;
    }
}