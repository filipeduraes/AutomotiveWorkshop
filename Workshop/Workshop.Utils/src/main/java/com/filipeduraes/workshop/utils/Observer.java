// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementa o padrão Observer pattern.
 * Mantém uma lista de observadores que são alertados quando algum evento acontece.
 * @author Filipe Durães
 */
public class Observer 
{
    private List<Runnable> listeners = new ArrayList<>();
    
    /**
     * Adiciona um novo observador ao evento.
     * 
     * @param listener Observador que será chamado quando o evento ocorrer.
     */
    public void addListener(Runnable listener)
    {
        listeners.add(listener);
    }
    
    /**
     * Remove um observador do evento.
     * 
     * @param listener Observador a ser removido.
     */
    public void removeListener(Runnable listener)
    {
        listeners.remove(listener);
    }
    
    /**
     * Chama todos os observadores, informando que o evento aconteceu.
     */
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
