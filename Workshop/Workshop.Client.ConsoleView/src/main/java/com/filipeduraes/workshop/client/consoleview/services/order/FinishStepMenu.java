package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.PopupMenuRedirector;
import com.filipeduraes.workshop.client.consoleview.employee.EmployeeSearchMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.MaxCharactersValidator;
import com.filipeduraes.workshop.client.dtos.service.ServiceStepTypeDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;

public class FinishStepMenu implements IWorkshopMenu
{
    private final PopupMenuRedirector redirector = new PopupMenuRedirector(new EmployeeSearchMenu());

    @Override
    public String getMenuDisplayName()
    {
        return "Finalizar Etapa";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();

        if(serviceOrderViewModel.getSelectedDTO().getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
        {
            MenuResult menuResult = selectEmployeeForAssessmentFinish(menuManager);

            if(menuResult != null)
            {
                return menuResult;
            }
        }

        String shortDescription = ConsoleInput.readValidatedLine("Escreva uma descricao curta do que foi encontrado", new MaxCharactersValidator(30));
        String detailedDescription = ConsoleInput.readLine("Escreva uma descricao detalhada do que foi encontrado");

        serviceOrderViewModel.setCurrentStepShortDescription(shortDescription);
        serviceOrderViewModel.setCurrentStepDetailedDescription(detailedDescription);
        serviceOrderViewModel.OnFinishStepRequest.broadcast();

        if(serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.printf("Inspecao finalizada com sucesso! ID: %s%n", serviceOrderViewModel.getSelectedDTO().getID());
        }
        else
        {
            System.out.println("Nao foi possivel finalizar a inspecao, tente novamente.");
        }

        return MenuResult.pop();
    }

    private MenuResult selectEmployeeForAssessmentFinish(MenuManager menuManager)
    {
        EmployeeViewModel employeeViewModel = menuManager.getViewModelRegistry().getEmployeeViewModel();

        if(!employeeViewModel.hasValidSelectedIndex())
        {
            return redirector.redirect();
        }

        redirector.reset();
        employeeViewModel.OnLoadDataRequest.broadcast();

        if(!employeeViewModel.getRequestWasSuccessful())
        {
            System.out.println("Nao foi possivel finalizar a inspecao, tente novamente.");
            return MenuResult.pop();
        }

        return null;
    }
}
