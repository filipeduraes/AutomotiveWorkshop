// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.model.mappers.ClientMapper;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.SearchByOption;
import com.filipeduraes.workshop.core.CrudModule;
import com.filipeduraes.workshop.core.client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos clientes,
 * fazendo a intermediação entre o ViewModel e o módulo de negócios.
 * Processa requisições como registro de novos clientes, busca e carregamento de dados.
 *
 * @author Filipe Durães
 */
public class ClientController
{
    private final Map<SearchByOption, Function<Client, String>> clientSearchOptions = Map.of
    (
        SearchByOption.NAME, Client::getName,
        SearchByOption.CPF, Client::getMaskedCPF,
        SearchByOption.EMAIL, Client::getEmail,
        SearchByOption.PHONE, Client::getPhoneNumber
    );

    private final ClientViewModel clientViewModel;
    private final CrudModule<Client> clientModule;

    private List<Client> foundClients;

    /**
     * Cria uma nova instância do controlador de clientes.
     *
     * @param clientViewModel Camada intermediária que gerencia o estado e os dados entre a view e o modelo
     * @param clientModule    Módulo de negócios que implementa as operações com clientes
     */
    public ClientController(ClientViewModel clientViewModel, CrudModule<Client> clientModule)
    {
        this.clientViewModel = clientViewModel;
        this.clientModule = clientModule;

        clientViewModel.OnClientRegisterRequest.addListener(this::registerClient);
        clientViewModel.OnClientLoadDataRequest.addListener(this::loadClientData);
        clientViewModel.OnClientsSearchRequest.addListener(this::searchClients);
    }

    /**
     * Libera os recursos utilizados pelo controlador,
     * removendo os listeners de eventos registrados.
     */
    public void dispose()
    {
        clientViewModel.OnClientRegisterRequest.removeListener(this::registerClient);
        clientViewModel.OnClientLoadDataRequest.removeListener(this::loadClientData);
        clientViewModel.OnClientsSearchRequest.removeListener(this::searchClients);
    }

    private void registerClient()
    {
        ClientDTO client = clientViewModel.getClient();
        Client newClient = ClientMapper.fromDTO(client);
        UUID clientID = clientModule.registerEntity(newClient);
        client.setID(clientID);
    }

    private void searchClients()
    {
        final String searchPattern = clientViewModel.getSearchPattern();
        Function<Client, String> clientStringExtractor = clientSearchOptions.get(clientViewModel.getSearchByOption());
        foundClients = clientModule.searchEntitiesWithPattern(searchPattern, clientStringExtractor);

        List<String> clientDescriptions = convertClientsToClientDescriptions(foundClients);
        clientViewModel.setFoundClientDescriptions(clientDescriptions);
    }

    private void loadClientData()
    {
        final int selectedFoundClientIndex = clientViewModel.getSelectedFoundClientIndex();

        if (selectedFoundClientIndex >= 0 && selectedFoundClientIndex < foundClients.size())
        {
            Client selectedFoundClient = foundClients.get(selectedFoundClientIndex);
            clientViewModel.setClient(ClientMapper.toDTO(selectedFoundClient));
        }
    }

    private List<String> convertClientsToClientDescriptions(List<Client> clients)
    {
        ArrayList<String> clientNames = new ArrayList<>();

        for (Client client : clients)
        {
            String clientDescription = String.format("%s - %s", client.getName(), client.getMaskedCPF());
            clientNames.add(clientDescription);
        }

        return clientNames;
    }
}