// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.viewmodel.ClientRequest;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.client.ClientModule;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class ClientController 
{
    private final ClientViewModel clientViewModel;
    private final ClientModule clientModule;

    private ArrayList<Client> foundClients;
    private Runnable processClientRequest;
    
    public ClientController(ClientViewModel clientViewModel, ClientModule clientModule)
    {
        this.clientViewModel = clientViewModel;
        this.clientModule = clientModule;
        
        processClientRequest = () -> processClientRequest();
        clientViewModel.OnClientRequest.addListener(processClientRequest);
    }
    
    public void dispose()
    {
        clientViewModel.OnClientRequest.removeListener(processClientRequest);
    }
    
    private void processClientRequest()
    {
        switch(clientViewModel.getCurrentRequest())
        {
            case REGISTER_CLIENT:
            {
                final String name = clientViewModel.getName();
                final String email = clientViewModel.getEmail();
                final String phoneNumber = clientViewModel.getPhoneNumber();
                final String cpf = clientViewModel.getCPF();
                final String address = clientViewModel.getAddress();
                
                Client newClient = new Client(name, email, phoneNumber, address, cpf);
                clientModule.registerNewClient(newClient);
                break;
            }
            case SEARCH_CLIENTS:
            {
                final String searchPattern = clientViewModel.getSearchPattern();
                foundClients = clientModule.searchClientsWithPattern(searchPattern);
                
                ArrayList<String> clientNames = convertClientsToClientNames(foundClients);
                clientViewModel.setFoundClientNames(clientNames);
                break;
            }                
            case LOAD_CLIENT_DATA:
            {
                final int selectedFoundClientIndex = clientViewModel.getSelectedFoundClientIndex();
                
                if(selectedFoundClientIndex >= 0 && selectedFoundClientIndex < foundClients.size())
                {
                    Client selectedFoundClient = foundClients.get(selectedFoundClientIndex);
                    
                    clientViewModel.setName(selectedFoundClient.getName());
                    clientViewModel.setEmail(selectedFoundClient.getEmail());
                    clientViewModel.setPhoneNumber(selectedFoundClient.getPhoneNumber());
                    clientViewModel.setAddress(selectedFoundClient.getAddress());
                    clientViewModel.setCPF(selectedFoundClient.getMaskedCPF());
                }
                break;
            }
        }
            
        clientViewModel.setCurrentRequest(ClientRequest.NONE);
    }

    private ArrayList<String> convertClientsToClientNames(ArrayList<Client> clients)
    {
        ArrayList<String> clientNames = new ArrayList<>();
        
        for(Client client : clients)
        {
            clientNames.add(client.getName());
        }
        
        return clientNames;
    }
}