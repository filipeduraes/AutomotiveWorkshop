// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientRequest;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.client.ClientModule;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos clientes,
 * fazendo a intermediação entre o ViewModel e o módulo de negócios.
 * Processa requisições como registro de novos clientes, busca e carregamento de dados.
 *
 * @author Filipe Durães
 */
public class ClientController
{
    private final ClientViewModel clientViewModel;
    private final ClientModule clientModule;

    private List<Client> foundClients;

    /**
     * Cria uma nova instância do controlador de clientes.
     *
     * @param clientViewModel Camada intermediária que gerencia o estado e os dados entre a view e o modelo
     * @param clientModule    Módulo de negócios que implementa as operações com clientes
     */
    public ClientController(ClientViewModel clientViewModel, ClientModule clientModule)
    {
        this.clientViewModel = clientViewModel;
        this.clientModule = clientModule;

        clientViewModel.OnClientRequest.addListener(this::processClientRequest);
    }

    /**
     * Libera os recursos utilizados pelo controlador,
     * removendo os listeners de eventos registrados.
     */
    public void dispose()
    {
        clientViewModel.OnClientRequest.removeListener(this::processClientRequest);
    }

    private void processClientRequest()
    {
        switch (clientViewModel.getCurrentRequest())
        {
            case REGISTER_CLIENT:
            {

                ClientDTO client = clientViewModel.getClient();
                Client newClient = convertDTOToClient(client);
                UUID clientID = clientModule.registerNewClient(newClient);
                client.setID(clientID);
                break;
            }
            case SEARCH_CLIENTS:
            {
                final String searchPattern = clientViewModel.getSearchPattern();
                foundClients = clientModule.searchClientsWithPattern(searchPattern);

                List<String> clientNames = convertClientsToClientNames(foundClients);
                clientViewModel.setFoundClientNames(clientNames);
                break;
            }
            case LOAD_CLIENT_DATA:
            {
                final int selectedFoundClientIndex = clientViewModel.getSelectedFoundClientIndex();

                if (selectedFoundClientIndex >= 0 && selectedFoundClientIndex < foundClients.size())
                {
                    Client selectedFoundClient = foundClients.get(selectedFoundClientIndex);
                    clientViewModel.setClient(convertClientToDTO(selectedFoundClient));
                }
                break;
            }
        }

        clientViewModel.setCurrentRequest(ClientRequest.NONE);
    }

    private List<String> convertClientsToClientNames(List<Client> clients)
    {
        ArrayList<String> clientNames = new ArrayList<>();

        for (Client client : clients)
        {
            clientNames.add(client.getName());
        }

        return clientNames;
    }

    private ClientDTO convertClientToDTO(Client client)
    {
        return new ClientDTO
        (
            client.getID(),
            client.getName(),
            client.getEmail(),
            client.getPhoneNumber(),
            client.getAddress(),
            client.getMaskedCPF()
        );
    }

    private Client convertDTOToClient(ClientDTO clientDTO)
    {
        return new Client
        (
            clientDTO.getName(),
            clientDTO.getEmail(),
            clientDTO.getPhoneNumber(),
            clientDTO.getAddress(),
            maskCPF(clientDTO.getCPF())
        );
    }

    private String maskCPF(String cpf)
    {
        String numberOnlyCPF = cpf.replaceAll("\\D", ""); // Substitui qualquer caractere não numérico por uma string vazia

        if(numberOnlyCPF.length() != 11)
        {
            return "";
        }

        String thirdPart = numberOnlyCPF.substring(6, 9);
        String fourthPart = numberOnlyCPF.substring(9);
        return String.format("XXX.XXX.%s-%s", thirdPart, fourthPart);
    }
}