// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.auth.AuthModule;

/**
 *
 * @author Filipe Durães
 */
public class Workshop 
{
    private AuthModule authModule = new AuthModule();
    
    public AuthModule getAuthModule()
    {
        return authModule;
    }
}
