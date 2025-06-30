// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.financial;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.ExpenseDTO;
import com.filipeduraes.workshop.client.viewmodel.ExpenseViewModel;

import java.math.BigDecimal;

public class RegisterExpenseMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Registrar despesa";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        String expenseDescription = ConsoleInput.readLine("Insira a descricao da despesa");
        BigDecimal expenseValue = ConsoleInput.readLinePositiveBigDecimal("Insira o valor da despesa");

        ExpenseViewModel expenseViewModel = menuManager.getViewModelRegistry().getExpenseViewModel();
        ExpenseDTO expenseDTO = new ExpenseDTO(expenseDescription, expenseValue);
        expenseViewModel.setSelectedExpense(expenseDTO);

        expenseViewModel.OnExpenseRegisterRequested.broadcast();

        if(expenseViewModel.getRequestWasSuccessful())
        {
            System.out.println("Despesa registrada com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel registrar a despesa, tente novamente.");
        }

        return MenuResult.pop();
    }
}
