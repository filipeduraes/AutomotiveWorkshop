package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.auth.EmployeeSearchMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.MaxCharactersValidator;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;

public class FinishAssessmentMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Finalizar inspecao";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();
        EmployeeViewModel employeeViewModel = viewModelRegistry.getEmployeeViewModel();

        if(!employeeViewModel.hasValidSelectedIndex())
        {
            boolean canSelectEmployee = ConsoleInput.readConfirmation("E preciso selecionar um colaborador para transferir a ordem de servico.\nDeseja selecionar?");

            if(canSelectEmployee)
            {
                return MenuResult.push(new EmployeeSearchMenu());
            }

            System.out.println("Operacao cancelada. Voltando...");
            return MenuResult.pop();
        }

        employeeViewModel.OnLoadDataRequest.broadcast();

        if(!employeeViewModel.getRequestWasSuccessful())
        {
            System.out.println("Nao foi possivel finalizar a inspecao, tente novamente.");
            return MenuResult.pop();
        }

        String shortDescription = ConsoleInput.readValidatedLine("Escreva uma descricao curta do que foi encontrado", new MaxCharactersValidator(30));
        String detailedDescription = ConsoleInput.readLine("Escreva uma descricao detalhada do que foi encontrado");

        serviceViewModel.setCurrentStepShortDescription(shortDescription);
        serviceViewModel.setCurrentStepDetailedDescription(detailedDescription);
        serviceViewModel.OnFinishStepRequest.broadcast();

        if(serviceViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Inspecao finalizada com sucesso! ID: %s%n", serviceViewModel.getSelectedDTO().getID());
        }
        else
        {
            System.out.println("Nao foi possivel finalizar a inspecao, tente novamente.");
        }

        return MenuResult.pop();
    }
}
