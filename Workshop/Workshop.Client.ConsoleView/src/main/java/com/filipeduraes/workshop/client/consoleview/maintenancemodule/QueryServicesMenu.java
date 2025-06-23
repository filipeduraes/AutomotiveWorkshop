package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.clientmodule.ClientSearchMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceFilterType;
import com.filipeduraes.workshop.utils.TextUtils;

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
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();

        if(lastSelectedFilter == ServiceFilterType.NONE)
        {
            ServiceFilterType filterType = selectFilterOption();
            lastSelectedFilter = filterType;
            serviceViewModel.setFilterType(filterType);

            if (filterType == ServiceFilterType.CLIENT)
            {
                return MenuResult.push(new ClientSearchMenu());
            }
            else if (filterType == ServiceFilterType.DESCRIPTION_PATTERN)
            {
                String pattern = ConsoleInput.readLine("Insira o padrao procurado na descricao:");
                serviceViewModel.setDescriptionQueryPattern(pattern);
            }
        }

        serviceViewModel.OnSearchRequest.broadcast();

        if(!serviceViewModel.getWasRequestSuccessful())
        {
            System.out.println("Nao foi possivel recuperar os servicos. Tente novamente.");
            return MenuResult.pop();
        }

        String[] servicesDescriptions = serviceViewModel.getFoundEntitiesDescriptions().toArray(String[]::new);

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
            serviceViewModel.setSelectedIndex(selectedAppointment);

            return MenuResult.push(new ServiceDetailsMenu());
        }

        viewModelRegistry.getClientViewModel().resetSelectedDTO();
        serviceViewModel.resetQuery();

        System.out.println("Operacao cancelada, nenhum agendamento selecionado.");
        return MenuResult.pop();
    }

    private ServiceFilterType selectFilterOption()
    {
        boolean filterService = ConsoleInput.readConfirmation("Deseja filtrar os servicos?");
        int selectedFilterOption = -1;

        ServiceFilterType[] filterTypes = ServiceFilterType.values();
        String[] filterTypesDisplayNames = TextUtils.convertToStringArray(filterTypes, 1);

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