// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

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
