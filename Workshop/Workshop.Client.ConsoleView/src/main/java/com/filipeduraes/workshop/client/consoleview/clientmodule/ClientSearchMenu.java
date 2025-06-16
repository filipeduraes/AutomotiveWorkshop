// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.SearchByOption;

import java.util.Arrays;
import java.util.List;

/**
 * Menu responsável por pesquisar e exibir clientes cadastrados no sistema.
 * Permite que o usuário busque clientes pelo nome, visualize os resultados
 * da pesquisa e selecione um cliente específico para ver seus detalhes.
 *
 * @author Filipe Durães
 */
public class ClientSearchMenu implements IWorkshopMenu
{
    private final SearchByOption[] options = { SearchByOption.NAME, SearchByOption.CPF, SearchByOption.EMAIL, SearchByOption.PHONE };
    private final String[] optionsDisplayNames;

    public ClientSearchMenu()
    {
        optionsDisplayNames = Arrays.stream(options)
                                    .map(SearchByOption::toString)
                                    .toArray(String[]::new);
    }

    @Override
    public String getMenuDisplayName()
    {
        return "Pesquisar Cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        final ClientViewModel clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();

        int searchOptionIndex = ConsoleInput.readOptionFromList("Escolha o campo para pesquisar:", optionsDisplayNames, true);

        if(searchOptionIndex >= optionsDisplayNames.length)
        {
            System.out.println("Busca cancelada.");
            return MenuResult.pop();
        }

        System.out.printf("Digite o %s do cliente: %n", optionsDisplayNames[searchOptionIndex]);

        final String searchPattern = ConsoleInput.readLine();

        clientViewModel.setSearchByOption(options[searchOptionIndex]);
        clientViewModel.setSearchPattern(searchPattern);

        clientViewModel.OnClientsSearchRequest.broadcast();
        
        showFoundClients(clientViewModel);

        int selectedClient = ConsoleInput.readLineInteger("Escolha o usuario: ");

        if(selectedClientSuccessfully(selectedClient, clientViewModel))
        {
            if(clientViewModel.getSelectedFoundClientIndex() >= 0)
            {
                ClientDTO client = clientViewModel.getClient();
                System.out.println(client);
            }

            return MenuResult.pop();
        }

        return MenuResult.none();
    }

    private void showFoundClients(final ClientViewModel clientViewModel) 
    {
        final List<String> foundClientNames = clientViewModel.getFoundClientDescriptions();
        
        for(int i = 0; i < foundClientNames.size(); i++)
        {
            System.out.printf(" [%d] %s%n", i, foundClientNames.get(i));
        }
        
        System.out.printf(" [%d] Pesquisar novamente%n", foundClientNames.size());
        System.out.printf(" [%d] Cancelar busca%n", foundClientNames.size() + 1);
    }

    private boolean selectedClientSuccessfully(int selectedClient, final ClientViewModel clientViewModel)
    {
        final List<String> foundClientNames = clientViewModel.getFoundClientDescriptions();
        
        if (selectedClient >= 0 && selectedClient < foundClientNames.size()) 
        {
            clientViewModel.setSelectedFoundClientIndex(selectedClient);
            clientViewModel.OnClientLoadDataRequest.broadcast();
            return true;
        }
        
        if (selectedClient == foundClientNames.size() + 1) 
        {
            clientViewModel.resetSelectedClient();
            return true;
        }
        
        return false;
    }
}
