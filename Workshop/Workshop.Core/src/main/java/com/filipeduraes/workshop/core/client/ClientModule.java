// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.client;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Módulo responsável pelo gerenciamento de clientes no sistema.
 * Fornece funcionalidades para registro e busca de clientes.
 *
 * @author Filipe Durães
 */
public class ClientModule
{
    private Map<UUID, Client> loadedClients = new HashMap<UUID, Client>();

    /**
     * Inicializa um novo módulo de clientes.
     * Carrega os clientes registrados do arquivo para a memória.
     */
    public ClientModule()
    {
        final ParameterizedType type = Persistence.createParameterizedType(HashMap.class, UUID.class, Client.class);
        loadedClients = Persistence.loadFile(WorkshopPaths.REGISTERED_CLIENTS_PATH, type, loadedClients);
    }

    /**
     * Registra um novo cliente no sistema.
     * Gera um ID único para o cliente e salva as suas informações.
     *
     * @param client O cliente a ser registrado
     */
    public UUID registerNewClient(Client client)
    {
        UUID uniqueID = Persistence.generateUniqueID(loadedClients);
        client.setID(uniqueID);

        loadedClients.put(uniqueID, client);
        saveCurrentClients();
        return uniqueID;
    }

    public void registerVehicleToOwner(UUID ownerID, UUID vehicleID)
    {
        Client ownerClient = findClientByID(ownerID);
        ownerClient.addOwnedVehicle(vehicleID);
        saveCurrentClients();
    }

    public void saveCurrentClients()
    {
        Persistence.saveFile(loadedClients, WorkshopPaths.REGISTERED_CLIENTS_PATH);
    }

    /**
     * Busca um cliente específico pelo seu ID único.
     *
     * @param clientID O identificador único do cliente a ser buscado
     * @return O cliente encontrado ou null caso não exista cliente com o ID especificado
     */
    public Client findClientByID(UUID clientID)
    {
        if (!loadedClients.containsKey(clientID))
        {
            return null;
        }

        return loadedClients.get(clientID);
    }

    /**
     * Busca clientes cujos nomes correspondam ao padrão especificado.
     *
     * @param pattern O padrão de texto para busca no nome dos clientes
     * @return Uma lista com os clientes encontrados que correspondem ao padrão
     */
    public ArrayList<Client> searchClientsWithPattern(String pattern)
    {
        //TODO: optimize
        ArrayList<Client> foundClients = new ArrayList<>();
        pattern = pattern.trim();

        for (Client loadedClient : loadedClients.values())
        {
            if (loadedClient.hasMatchingName(pattern))
            {
                foundClients.add(loadedClient);
            }
        }

        return foundClients;
    }
}
