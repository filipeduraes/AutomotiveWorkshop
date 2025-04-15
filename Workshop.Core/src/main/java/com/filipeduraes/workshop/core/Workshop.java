// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.client.ClientModule;

/**
 *
 * @author Filipe Durães
 */
public class Workshop 
{
    private AuthModule authModule = new AuthModule(false);
    private ClientModule clientModule = new ClientModule();
    
    public AuthModule getAuthModule()
    {
        return authModule;
    }
    
    public ClientModule getClientModule()
    {
        return clientModule;
    }
}
