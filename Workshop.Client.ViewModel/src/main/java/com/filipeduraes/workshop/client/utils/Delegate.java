// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.utils;

import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class Delegate 
{
    private ArrayList<Runnable> listeners = new ArrayList<>();
    
    public void addListener(Runnable listener)
    {
        listeners.add(listener);
    }
    
    public void removeListener(Runnable listener)
    {
        listeners.remove(listener);
    }
    
    public void broadcast()
    {
        for(Runnable listener : listeners)
        {
            if(listener != null)
            {
                listener.run();
            }
        }
    }
}
