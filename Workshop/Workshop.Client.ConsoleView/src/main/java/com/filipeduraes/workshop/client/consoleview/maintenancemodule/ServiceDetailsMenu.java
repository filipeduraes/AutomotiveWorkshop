package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepDTO;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsMenu implements IWorkshopMenu
{
    public static final String ASSESSMENT_TEXT = "inspecao";
    public static final String MAINTENANCE_TEXT = "manutencao";

    @Override
    public String getMenuDisplayName()
    {
        return "Detalhes";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        MaintenanceViewModel maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();

        maintenanceViewModel.OnDetailedServiceInfoRequest.broadcast();

        if(maintenanceViewModel.getWasRequestSuccessful())
        {
            ServiceOrderDTO selectedService = maintenanceViewModel.getSelectedService();
            System.out.printf("Servico selecionado:%n%s%n%n", selectedService);

            String[] options = buildEditingOptions(selectedService);
            int selectedOption = ConsoleInput.readOptionFromList("O que deseja fazer?", options, true);

            if(selectedOption == 0)
            {
                if(canStartNextStep(selectedService))
                {
                    maintenanceViewModel.OnStartStepRequest.broadcast();

                    if(maintenanceViewModel.getWasRequestSuccessful())
                    {
                        System.out.printf("%n%s iniciada com sucesso%n", capitalizeFirstLetter(getDesiredStepName(selectedService)));
                        return MenuResult.none();
                    }
                }
                else if(selectedService.getServiceStep() == ServiceStepDTO.ASSESSMENT)
                {
                    return MenuResult.push(new FinishAssessmentMenu());
                }
                else if(selectedService.getServiceStep() == ServiceStepDTO.MAINTENANCE)
                {
                    return MenuResult.push(new FinishMaintenanceMenu());
                }
            }
            else if(selectedOption == 1)
            {
                return MenuResult.push(new EditServiceMenu());
            }
            else if(selectedOption == 2)
            {
                return MenuResult.push(new EditServiceStepMenu());
            }

            return MenuResult.pop();
        }

        System.out.println("Nao foi possivel selecionar o servico. Tente novamente.");
        return MenuResult.pop();
    }

    private String[] buildEditingOptions(ServiceOrderDTO service)
    {
        List<String> options = new ArrayList<>();

        if(service.getCurrentStepWasFinished())
        {
            options.add(String.format("Iniciar %s", getDesiredStepName(service)));
        }
        else
        {
            options.add(String.format("Finalizar %s", getDesiredStepName(service)));
        }

        options.add("Editar dados gerais do veiculo");
        options.add("Editar etapa do veiculo");
        return options.toArray(String[]::new);
    }

    private boolean canStartNextStep(ServiceOrderDTO service)
    {
        ServiceStepDTO serviceStep = service.getServiceStep();
        boolean serviceHasNotFinished = serviceStep != ServiceStepDTO.CREATED && serviceStep != ServiceStepDTO.MAINTENANCE;

        return service.getCurrentStepWasFinished() && serviceHasNotFinished;
    }

    private String getDesiredStepName(ServiceOrderDTO service)
    {
        if(canStartNextStep(service))
        {
            return service.getServiceStep() == ServiceStepDTO.APPOINTMENT ? ASSESSMENT_TEXT : MAINTENANCE_TEXT;
        }

        return service.getServiceStep() == ServiceStepDTO.ASSESSMENT ? ASSESSMENT_TEXT : MAINTENANCE_TEXT;
    }

    private String capitalizeFirstLetter(String input)
    {
        if(input.isEmpty())
        {
            return "";
        }

        char firstLetter = input.charAt(0);
        char upperCaseFirstLetter = Character.toUpperCase(firstLetter);
        return upperCaseFirstLetter + input.substring(1);
    }
}