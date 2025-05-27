// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientRequest;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import java.util.ArrayList;
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

    @Override
    public String getMenuDisplayName() 
    {
        return "Pesquisar Cliente";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        System.out.println("Digite o nome do cliente: ");
        
        final String searchPattern = ConsoleInput.readLine();
        final ClientViewModel clientViewModel = menuManager.getClientViewModel();
        
        clientViewModel.setSearchPattern(searchPattern);
        clientViewModel.setCurrentRequest(ClientRequest.SEARCH_CLIENTS);
        
        if(clientViewModel.getCurrentRequest() == ClientRequest.NONE)
        {
            showFoundClients(clientViewModel);
            
            int selectedClient = ConsoleInput.readLineInteger("Escolha o usuario: ");
            
            if(selectedClientSuccessfuly(selectedClient, clientViewModel))
            {
                if(clientViewModel.getSelectedFoundClientIndex() >= 0)
                {
                    ClientDTO client = clientViewModel.getClient();

                    System.out.println("Cliente selecionado: ");
                    System.out.printf(" - Nome: %s\n", client.getName());
                    System.out.printf(" - Email: %s\n", client.getEmail());
                    System.out.printf(" - CPF: %s\n", client.getCPF());
                    System.out.printf(" - Telefone: %s\n", client.getPhoneNumber());
                }
                
                return MenuResult.pop();
            }
        }
        
        return MenuResult.none();
    }

    private void showFoundClients(final ClientViewModel clientViewModel) 
    {
        final List<String> foundClientNames = clientViewModel.getFoundClientNames();
        
        for(int i = 0; i < foundClientNames.size(); i++)
        {
            System.out.printf(" [%d] %s\n", i, foundClientNames.get(i));
        }
        
        System.out.printf(" [%d] Pesquisar novamente\n", foundClientNames.size());
        System.out.printf(" [%d] Cancelar busca\n", foundClientNames.size() + 1);
    }

    private boolean selectedClientSuccessfuly(int selectedClient, final ClientViewModel clientViewModel) 
    {
        final List<String> foundClientNames = clientViewModel.getFoundClientNames();
        
        if (selectedClient >= 0 && selectedClient < foundClientNames.size()) 
        {
            clientViewModel.setSelectedFoundClientIndex(selectedClient);
            clientViewModel.setCurrentRequest(ClientRequest.LOAD_CLIENT_DATA);
            return true;
        }
        
        if (selectedClient == foundClientNames.size() + 1) 
        {
            clientViewModel.resetSelectedFoundClientIndex();
            return true;
        }
        
        return false;
    }
}
