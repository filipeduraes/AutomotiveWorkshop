package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientSearchMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceRequest;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceFilterType;

import java.util.Arrays;

public class QueryServicesMenu implements IWorkshopMenu
{
    private ServiceFilterType lastSelectedFilter = ServiceFilterType.NONE;

    @Override
    public String getMenuDisplayName()
    {
        return "Consultar";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        MaintenanceViewModel maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();

        if(lastSelectedFilter == ServiceFilterType.NONE)
        {
            ServiceFilterType filterType = selectFilterOption();
            lastSelectedFilter = filterType;
            maintenanceViewModel.setFilterType(filterType);

            if (filterType == ServiceFilterType.CLIENT)
            {
                return MenuResult.push(new ClientSearchMenu());
            }
            else if (filterType == ServiceFilterType.DESCRIPTION_PATTERN)
            {
                String pattern = ConsoleInput.readLine("Insira o padrao procurado na descricao:");
                maintenanceViewModel.setDescriptionQueryPattern(pattern);
            }
        }

        maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_SERVICES);

        if(maintenanceViewModel.getMaintenanceRequest() == MaintenanceRequest.REQUEST_FAILED)
        {
            System.out.println("Nao foi possivel recuperar os servicos. Tente novamente.");
            return MenuResult.pop();
        }

        String[] servicesDescriptions = maintenanceViewModel.getServicesDescriptions();

        if(servicesDescriptions.length == 0)
        {
            lastSelectedFilter = ServiceFilterType.NONE;
            boolean tryAgain = ConsoleInput.readConfirmation("Nenhum servico encontrado, deseja tentar novamente?");
            return tryAgain ? MenuResult.none() : MenuResult.pop();
        }

        System.out.println("Servicos encontados:");

        int selectedAppointment = ConsoleInput.readOptionFromList("Selecione um servico aberto", servicesDescriptions, true);

        if (selectedAppointment < servicesDescriptions.length)
        {
            maintenanceViewModel.setSelectedMaintenanceIndex(selectedAppointment);

            return MenuResult.push(new ServiceDetailsMenu());
        }

        viewModelRegistry.getClientViewModel().resetSelectedClient();
        maintenanceViewModel.resetQuery();

        System.out.println("Operacao cancelada, nenhum agendamento selecionado.");
        return MenuResult.pop();
    }

    private ServiceFilterType selectFilterOption()
    {
        boolean filterService = ConsoleInput.readConfirmation("Deseja filtrar os servicos?");
        int selectedFilterOption = -1;

        ServiceFilterType[] filterTypes = ServiceFilterType.values();
        String[] filterTypesDisplayNames = Arrays.stream(filterTypes)
                                                 .skip(1)
                                                 .map(ServiceFilterType::toString)
                                                 .toArray(String[]::new);

        if(filterService)
        {
            selectedFilterOption = ConsoleInput.readOptionFromList("Qual filtro deseja usar?", filterTypesDisplayNames, true);

            if(selectedFilterOption >= filterTypesDisplayNames.length)
            {
                selectedFilterOption = -1;
            }
        }

        return filterTypes[selectedFilterOption + 1];
    }
}