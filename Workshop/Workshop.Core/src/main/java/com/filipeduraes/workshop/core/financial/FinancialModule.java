// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.financial;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Módulo responsável pelo gerenciamento financeiro da oficina.
 * Controla o registro e consulta de despesas mensais, fornecendo
 * funcionalidades para controle financeiro do negócio.
 *
 * @author Filipe Durães
 */
public class FinancialModule
{
    private final List<Expense> currentMonthExpenses;
    private final String expensesCurrentMonthPath;
    private final ParameterizedType expensesArrayType;

    /**
     * Cria uma nova instância do módulo financeiro.
     * Carrega automaticamente as despesas do mês atual do arquivo de persistência.
     */
    public FinancialModule()
    {
        expensesCurrentMonthPath = WorkshopPaths.getExpensesCurrentMonthPath();
        expensesArrayType = Persistence.createParameterizedType(List.class, Expense.class);
        currentMonthExpenses = Persistence.loadFile(expensesCurrentMonthPath, expensesArrayType, new ArrayList<>());
    }

    /**
     * Registra uma nova despesa no mês atual.
     * A despesa é adicionada à lista em memória e persistida no arquivo.
     *
     * @param expense despesa a ser registrada
     */
    public void registerExpense(Expense expense)
    {
        currentMonthExpenses.add(expense);
        Persistence.saveFile(currentMonthExpenses, expensesCurrentMonthPath);
    }

    /**
     * Obtém todas as despesas de um mês específico.
     * Carrega as despesas do arquivo de persistência correspondente ao mês.
     *
     * @param date data de referência para o mês desejado
     * @return lista de despesas do mês especificado
     */
    public List<Expense> getMonthExpenses(LocalDateTime date)
    {
        String expensesMonthPath = WorkshopPaths.getExpensesMonthPath(date);
        return Persistence.loadFile(expensesMonthPath, expensesArrayType, new ArrayList<>());
    }
}
