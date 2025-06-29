// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.services.order;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.client.ClientSearchMenu;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceFilterType;
import com.filipeduraes.workshop.utils.TextUtils;

/**
 * Menu de consulta de serviços da oficina.
 * Permite buscar e visualizar serviços utilizando diferentes tipos de filtros.
 *
 * @author Filipe Durães
 */
public class QueryServicesMenu implements IWorkshopMenu
{
    private ServiceFilterType lastSelectedFilter = ServiceFilterType.NONE;

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Consultar";
    }

    /**
     * Exibe o menu de consulta de serviços e processa a interação do usuário.
     * Permite selecionar filtros de busca e visualizar os serviços encontrados.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceOrderViewModel serviceOrderViewModel = viewModelRegistry.getServiceViewModel();

        if (lastSelectedFilter == ServiceFilterType.NONE)
        {
            ServiceFilterType filterType = selectFilterOption();
            lastSelectedFilter = filterType;
            serviceOrderViewModel.setFilterType(filterType);

            if (filterType == ServiceFilterType.CLIENT)
            {
                return MenuResult.push(new ClientSearchMenu());
            }
            else if (filterType == ServiceFilterType.DESCRIPTION_PATTERN)
            {
                String pattern = ConsoleInput.readLine("Insira o padrao procurado na descricao:");
                serviceOrderViewModel.setDescriptionQueryPattern(pattern);
            }
        }

        serviceOrderViewModel.OnSearchRequest.broadcast();

        if (!serviceOrderViewModel.getRequestWasSuccessful())
        {
            System.out.println("Nao foi possivel recuperar os servicos. Tente novamente.");
            return MenuResult.pop();
        }

        String[] servicesDescriptions = serviceOrderViewModel.getFoundEntitiesDescriptions().toArray(String[]::new);

        if (servicesDescriptions.length == 0)
        {
            lastSelectedFilter = ServiceFilterType.NONE;
            boolean tryAgain = ConsoleInput.readConfirmation("Nenhum servico encontrado, deseja tentar novamente?");
            return tryAgain ? MenuResult.none() : MenuResult.pop();
        }

        System.out.println("Servicos encontados:");

        int selectedAppointment = ConsoleInput.readOptionFromList("Selecione um servico aberto", servicesDescriptions, true);

        if (selectedAppointment < servicesDescriptions.length)
        {
            serviceOrderViewModel.setSelectedIndex(selectedAppointment);

            return MenuResult.push(new ServiceDetailsMenu());
        }

        viewModelRegistry.getClientViewModel().resetSelectedDTO();
        serviceOrderViewModel.resetQuery();

        System.out.println("Operacao cancelada, nenhum agendamento selecionado.");
        return MenuResult.pop();
    }

    private ServiceFilterType selectFilterOption()
    {
        boolean filterService = ConsoleInput.readConfirmation("Deseja filtrar os servicos?");
        int selectedFilterOption = -1;

        ServiceFilterType[] filterTypes = ServiceFilterType.values();
        String[] filterTypesDisplayNames = TextUtils.convertToStringArray(filterTypes, 1);

        if (filterService)
        {
            selectedFilterOption = ConsoleInput.readOptionFromList("Qual filtro deseja usar?", filterTypesDisplayNames, true);

            if (selectedFilterOption >= filterTypesDisplayNames.length)
            {
                selectedFilterOption = -1;
            }
        }

        return filterTypes[selectedFilterOption + 1];
    }
}