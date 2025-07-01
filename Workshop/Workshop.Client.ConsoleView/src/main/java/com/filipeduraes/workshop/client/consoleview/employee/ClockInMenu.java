// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.employee;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.ClockInTypeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;

/**
 * Menu para registro de ponto do colaborador.
 * Permite ao usuário registrar entrada, saída ou pausas, apresentando apenas as opções
 * válidas de acordo com o último status registrado do colaborador.
 *
 * Este menu é utilizado para controle de jornada e pausas dos funcionários da oficina.
 *
 * @author Filipe Durães
 */
public class ClockInMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Bater ponto";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        EmployeeViewModel employeeViewModel = menuManager.getViewModelRegistry().getEmployeeViewModel();

        EmployeeDTO loggedUser = employeeViewModel.getLoggedUser();
        ClockInTypeDTO lastClockIn = loggedUser.getLastClockIn();

        ClockInTypeDTO[] options = switch (lastClockIn)
        {
            case IN -> new ClockInTypeDTO[] { ClockInTypeDTO.START_BREAK, ClockInTypeDTO.OUT };
            case START_BREAK -> new ClockInTypeDTO[] { ClockInTypeDTO.END_BREAK };
            case END_BREAK -> new ClockInTypeDTO[] { ClockInTypeDTO.START_BREAK, ClockInTypeDTO.OUT };
            case OUT -> new ClockInTypeDTO[]{ ClockInTypeDTO.IN };
        };

        int selectedClockInTypeIndex = ConsoleInput.readOptionFromList("Escolha a opcao desejada", options, true);

        if(selectedClockInTypeIndex >= options.length)
        {
            System.out.println("Operacao cancelada. Voltando...");
            return MenuResult.pop();
        }

        ClockInTypeDTO selectedClockInType = options[selectedClockInTypeIndex];
        loggedUser.setLastClockIn(selectedClockInType);
        employeeViewModel.OnClockInRequested.broadcast();

        if(employeeViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Ponto %s registrado com sucesso!%n", selectedClockInType.toString());
        }
        else
        {
            System.out.println("Nao foi possivel registrar o ponto. Tente novamente!");
        }

        return MenuResult.pop();
    }
}
