// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.client.ClientModule;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.SerializationAdapterGroup;
import com.filipeduraes.workshop.core.persistence.serializers.DateTimeSerializer;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class Workshop 
{
    private AuthModule authModule = new AuthModule(false);
    private ClientModule clientModule = new ClientModule();
    
    public Workshop()
    {
        DateTimeSerializer dateTimeSerializer = new DateTimeSerializer();
        
        ArrayList<SerializationAdapterGroup> adapters = new ArrayList<>();
        adapters.add(new SerializationAdapterGroup(LocalDate.class, dateTimeSerializer, dateTimeSerializer));
        
        Persistence.registerCustomSerializationAdapters(adapters);
    }
    
    public AuthModule getAuthModule()
    {
        return authModule;
    }
    
    public ClientModule getClientModule()
    {
        return clientModule;
    }
}
