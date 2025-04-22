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
 *
 * @author Filipe Durães
 */
public class ClientModule 
{
    private Map<UUID, Client> loadedClients = new HashMap<UUID, Client>();
    
    public ClientModule()
    {
        final ParameterizedType type = Persistence.createParameterizedType(HashMap.class, UUID.class, Client.class);
        loadedClients = Persistence.loadFile(WorkshopPaths.RegisteredClientsPath, type, loadedClients);
    }
    
    public void registerNewClient(Client client)
    {
        UUID uniqueID = Persistence.generateUniqueID(loadedClients);
        client.setID(uniqueID);
        
        loadedClients.put(uniqueID, client);
        Persistence.saveFile(loadedClients, WorkshopPaths.RegisteredClientsPath);
    }
    
    public ArrayList<Client> searchClientsWithPattern(String pattern)
    {
        //TODO: optimize
        ArrayList<Client> foundClients = new ArrayList<>();
        pattern = pattern.trim();
        
        for(Client loadedClient : loadedClients.values())
        {
            if(loadedClient.hasMatchingName(pattern))
            {
                foundClients.add(loadedClient);
            }
        }
        
        return foundClients;
    }
}
