// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.financial;

import com.filipeduraes.workshop.utils.TextUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa uma despesa registrada no sistema da oficina.
 * Mantém informações sobre a descrição, valor e momento do registro
 * da despesa para controle financeiro.
 *
 * @author Filipe Durães
 */
public class Expense
{
    private final String description;
    private final BigDecimal amount;
    private final LocalDateTime registerTime;

    /**
     * Cria uma nova despesa com descrição e valor especificados.
     * O momento do registro é automaticamente definido como o momento atual.
     *
     * @param description descrição da despesa
     * @param amount valor da despesa
     */
    public Expense(String description, BigDecimal amount)
    {
        this.description = description;
        this.amount = amount;
        registerTime = LocalDateTime.now();
    }

    /**
     * Obtém a descrição da despesa.
     *
     * @return descrição da despesa
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Obtém o valor da despesa.
     *
     * @return valor da despesa
     */
    public BigDecimal getAmount()
    {
        return amount;
    }

    /**
     * Obtém o momento em que a despesa foi registrada.
     *
     * @return momento do registro da despesa
     */
    public LocalDateTime getRegisterTime()
    {
        return registerTime;
    }

    /**
     * Retorna uma representação formatada da despesa.
     * Inclui descrição, valor formatado em reais e data de registro.
     *
     * @return string formatada com as informações da despesa
     */
    @Override
    public String toString()
    {
        return String.format(" > %s: %s - %s", description, TextUtils.formatPrice(amount), TextUtils.formatDate(registerTime));
    }
}
