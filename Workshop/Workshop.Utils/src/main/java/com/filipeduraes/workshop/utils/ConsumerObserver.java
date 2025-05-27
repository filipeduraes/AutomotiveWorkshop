// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Implementa o padrão Observer pattern.
 * Mantém uma lista de observadores que são alertados quando algum evento acontece,
 * recebendo um parâmetro do tipo especificado.
 *
 * @author Filipe Durães
 */
public class ConsumerObserver<T>
{
    private List<Consumer<T>> listeners = new ArrayList<>();

    /**
     * Adiciona um novo observador ao evento.
     *
     * @param listener Observador que será chamado quando o evento ocorrer,
     *                 recebendo um parâmetro do tipo especificado.
     */
    public void addListener(Consumer<T> listener)
    {
        listeners.add(listener);
    }

    /**
     * Remove um observador do evento.
     *
     * @param listener Observador a ser removido.
     */
    public void removeListener(Consumer<T> listener)
    {
        listeners.remove(listener);
    }

    /**
     * Chama todos os observadores, informando que o evento aconteceu
     * e passando o valor especificado como parâmetro.
     *
     * @param value Valor a ser passado para os observadores
     */
    public void broadcast(T value)
    {
        for (Consumer<T> listener : listeners)
        {
            if (listener != null)
            {
                listener.accept(value);
            }
        }
    }
}
