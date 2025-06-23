// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.model.mappers.ClientMapper;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.core.CrudRepository;
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
    private final Map<FieldType, Function<Client, String>> clientSearchOptions = Map.of
    (
        FieldType.NAME, Client::getName,
        FieldType.CPF, Client::getMaskedCPF,
        FieldType.EMAIL, Client::getEmail,
        FieldType.PHONE, Client::getPhoneNumber
    );

    private final ClientViewModel clientViewModel;
    private final CrudRepository<Client> clientModule;

    private List<Client> foundClients;

    /**
     * Cria uma nova instância do controlador de clientes.
     *
     * @param clientViewModel Camada intermediária que gerencia o estado e os dados entre a view e o modelo
     * @param clientModule    Módulo de negócios que implementa as operações com clientes
     */
    public ClientController(ClientViewModel clientViewModel, CrudRepository<Client> clientModule)
    {
        this.clientViewModel = clientViewModel;
        this.clientModule = clientModule;

        clientViewModel.OnClientRegisterRequest.addListener(this::registerClient);
        clientViewModel.OnLoadDataRequest.addListener(this::loadClientData);
        clientViewModel.OnSearchRequest.addListener(this::searchClients);
        clientViewModel.OnClientEditRequest.addListener(this::editClient);
        clientViewModel.OnDeleteRequest.addListener(this::deleteClient);
    }

    /**
     * Libera os recursos utilizados pelo controlador,
     * removendo os listeners de eventos registrados.
     */
    public void dispose()
    {
        clientViewModel.OnClientRegisterRequest.removeListener(this::registerClient);
        clientViewModel.OnLoadDataRequest.removeListener(this::loadClientData);
        clientViewModel.OnSearchRequest.removeListener(this::searchClients);
        clientViewModel.OnClientEditRequest.removeListener(this::editClient);
        clientViewModel.OnDeleteRequest.removeListener(this::deleteClient);
    }

    private void registerClient()
    {
        ClientDTO client = clientViewModel.getSelectedDTO();
        Client newClient = ClientMapper.fromDTO(client);
        UUID clientID = clientModule.registerEntity(newClient);
        client.setID(clientID);
    }

    private void searchClients()
    {
        final String searchPattern = clientViewModel.getSearchPattern();
        Function<Client, String> clientStringExtractor = clientSearchOptions.get(clientViewModel.getFieldType());
        foundClients = clientModule.searchEntitiesWithPattern(searchPattern, clientStringExtractor);

        List<String> clientDescriptions = convertClientsToClientDescriptions(foundClients);
        clientViewModel.setFoundEntitiesDescriptions(clientDescriptions);
    }

    private void editClient()
    {
        if(!clientViewModel.hasValidSelectedIndex() || !clientViewModel.hasLoadedDTO())
        {
            clientViewModel.setRequestWasSuccessful(false);
            return;
        }

        ClientDTO clientWithEditing = clientViewModel.getSelectedDTO();
        Client clientWithChanges = applyChangesToClient(clientWithEditing);

        boolean updateWasSuccessful = clientModule.updateEntity(clientWithChanges);
        clientViewModel.setRequestWasSuccessful(updateWasSuccessful);

        if(!updateWasSuccessful)
        {
            clientWithChanges = foundClients.get(clientViewModel.getSelectedIndex());
        }

        ClientDTO newSelectedDTO = ClientMapper.toDTO(clientWithChanges);
        foundClients.set(clientViewModel.getSelectedIndex(), clientWithChanges);
        clientViewModel.setSelectedDTO(newSelectedDTO);
    }

    private Client applyChangesToClient(ClientDTO clientWithEditing)
    {
        Client selectedOriginalClient = foundClients.get(clientViewModel.getSelectedIndex());
        Client coppiedClient = new Client(selectedOriginalClient);

        switch (clientViewModel.getFieldType())
        {
            case NAME -> coppiedClient.setName(clientWithEditing.getName());
            case EMAIL -> coppiedClient.setEmail(clientWithEditing.getEmail());
            case CPF -> coppiedClient.setMaskedCPF(clientWithEditing.getCPF());
            case ADDRESS -> coppiedClient.setAddress(clientWithEditing.getAddress());
            case PHONE -> coppiedClient.setPhoneNumber(clientWithEditing.getPhoneNumber());
        }
        return coppiedClient;
    }

    private void loadClientData()
    {
        final int selectedFoundClientIndex = clientViewModel.getSelectedIndex();

        if (selectedFoundClientIndex >= 0 && selectedFoundClientIndex < foundClients.size())
        {
            Client selectedFoundClient = foundClients.get(selectedFoundClientIndex);
            clientViewModel.setSelectedDTO(ClientMapper.toDTO(selectedFoundClient));
        }
    }

    private void deleteClient()
    {
        boolean canDeleteClient = clientViewModel.hasValidSelectedIndex();

        if(canDeleteClient)
        {
            Client client = foundClients.get(clientViewModel.getSelectedIndex());
            Client deletedClient = clientModule.deleteEntityWithID(client.getID());
            canDeleteClient = deletedClient != null;
        }

        clientViewModel.setRequestWasSuccessful(canDeleteClient);
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