// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.client;

import java.util.Comparator;

/**
 * Comparador que ordena clientes por nome em ordem alfabética.
 * Realiza comparação case-insensitive (ignora maiúsculas e minúsculas)
 * dos nomes dos clientes para ordenação.
 *
 * @author Filipe Durães
 */
public class ClientNameComparator implements Comparator<Client>
{
    /**
     * Compara dois clientes pelo nome em ordem alfabética.
     * A comparação é case-insensitive, ou seja, ignora diferenças
     * entre maiúsculas e minúsculas.
     *
     * @param firstClient primeiro cliente a ser comparado
     * @param secondClient segundo cliente a ser comparado
     * @return valor negativo se o primeiro nome vier antes alfabeticamente,
     *         valor positivo se o segundo nome vier antes,
     *         zero se os nomes forem iguais
     */
    @Override
    public int compare(Client firstClient, Client secondClient)
    {
        String firstClientName = firstClient.getName();
        String secondClientName = secondClient.getName();

        return firstClientName.compareToIgnoreCase(secondClientName); // Compara duas strings por ordem alfabética
    }
}