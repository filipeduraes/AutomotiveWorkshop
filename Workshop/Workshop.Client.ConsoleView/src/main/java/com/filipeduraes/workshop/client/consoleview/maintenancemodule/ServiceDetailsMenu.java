package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.general.EntityDetailsMenu;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.dtos.ServiceStepTypeDTO;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;
import com.filipeduraes.workshop.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class ServiceDetailsMenu extends EntityDetailsMenu<ServiceViewModel, ServiceOrderDTO>
{
    public static final String ASSESSMENT_TEXT = "inspecao";
    public static final String MAINTENANCE_TEXT = "manutencao";

    @Override
    public String getMenuDisplayName()
    {
        return "Detalhes";
    }

    @Override
    protected ServiceViewModel findViewModel(MenuManager menuManager)
    {
        return menuManager.getViewModelRegistry().getServiceViewModel();
    }

    @Override
    protected List<MenuOption> buildOptions()
    {
        ServiceViewModel viewModel = getViewModel();

        List<MenuOption> options = super.buildOptions();
        List<MenuOption> newOptions = new ArrayList<>();

        if(canStartNextStep(viewModel.getSelectedDTO()))
        {
            String optionDisplayName = String.format("Iniciar %s", getDesiredStepName(viewModel.getSelectedDTO()));
            newOptions.add(new MenuOption(optionDisplayName, this::startStep));
        }
        else
        {
            String optionDisplayName = String.format("Finalizar %s", getDesiredStepName(viewModel.getSelectedDTO()));
            newOptions.add(new MenuOption(optionDisplayName, this::finishStep));
        }

        newOptions.add(new MenuOption("Editar ordem de servico", this::editService));

        options.addAll(0, newOptions);
        return options;
    }

    private MenuResult startStep(MenuManager menuManager)
    {
        ServiceViewModel viewModel = getViewModel();
        ServiceOrderDTO selectedService = viewModel.getSelectedDTO();

        if (canStartNextStep(selectedService))
        {
            viewModel.OnStartStepRequest.broadcast();

            if (viewModel.getWasRequestSuccessful())
            {
                System.out.printf("%n%s iniciada com sucesso%n", TextUtils.capitalizeFirstLetter(getDesiredStepName(selectedService)));
                return MenuResult.none();
            }
        }

        System.out.println("Nao e possivel iniciar a proxima etapa. Finalize a atual.");
        return MenuResult.none();
    }

    private MenuResult finishStep(MenuManager menuManager)
    {
        ServiceOrderDTO selectedDTO = getViewModel().getSelectedDTO();

        if (selectedDTO.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT)
        {
            return MenuResult.push(new FinishAssessmentMenu());
        }
        else if (selectedDTO.getServiceStep() == ServiceStepTypeDTO.MAINTENANCE)
        {
            return MenuResult.push(new FinishMaintenanceMenu());
        }

        System.out.println("Nao e possivel finalizar a etapa atual.");
        return MenuResult.none();
    }

    private MenuResult editService(MenuManager menuManager)
    {
        menuManager.getViewModelRegistry().getClientViewModel().resetSelectedDTO();
        menuManager.getViewModelRegistry().getVehicleViewModel().resetSelectedDTO();
        return MenuResult.push(new EditServiceMenu());
    }

    private boolean canStartNextStep(ServiceOrderDTO service)
    {
        ServiceStepTypeDTO serviceStep = service.getServiceStep();
        boolean serviceHasNotFinished = serviceStep != ServiceStepTypeDTO.CREATED && serviceStep != ServiceStepTypeDTO.MAINTENANCE;

        return service.getCurrentStepWasFinished() && serviceHasNotFinished;
    }

    private String getDesiredStepName(ServiceOrderDTO service)
    {
        if(canStartNextStep(service))
        {
            return service.getServiceStep() == ServiceStepTypeDTO.APPOINTMENT ? ASSESSMENT_TEXT : MAINTENANCE_TEXT;
        }

        return service.getServiceStep() == ServiceStepTypeDTO.ASSESSMENT ? ASSESSMENT_TEXT : MAINTENANCE_TEXT;
    }
}