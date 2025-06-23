package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;

public class EmployeeDetailsMenu extends EntityDetailsMenu<EmployeeViewModel, EmployeeDTO>
{
    @Override
    protected EmployeeViewModel findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getEmployeeViewModel();
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        if(!findViewModel(menuManager).hasValidSelectedIndex())
        {
            boolean searchEmployee = ConsoleInput.readConfirmation("Deseja pesquisar um funcionario?");

            if(!searchEmployee)
            {
                System.out.println("Operacao cancelada. Voltando...");
                return MenuResult.pop();
            }

            return MenuResult.push(new EmployeeSearchMenu());
        }

        return super.showMenu(menuManager);
    }
}