// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.client;

import java.util.Comparator;

/**
 * Comparador que ordena clientes por email em ordem alfabética.
 * Realiza comparação case-insensitive (ignora maiúsculas e minúsculas)
 * dos emails dos clientes para ordenação.
 *
 * @author Filipe Durães
 */
public class ClientEmailComparator implements Comparator<Client>
{
    /**
     * Compara dois clientes pelo email em ordem alfabética.
     * A comparação é case-insensitive, ou seja, ignora diferenças
     * entre maiúsculas e minúsculas.
     *
     * @param firstClient primeiro cliente a ser comparado
     * @param secondClient segundo cliente a ser comparado
     * @return valor negativo se o primeiro email vier antes alfabeticamente,
     *         valor positivo se o segundo email vier antes,
     *         zero se os emails forem iguais
     */
    @Override
    public int compare(Client firstClient, Client secondClient)
    {
        String firstClientEmail = firstClient.getEmail();
        String secondClientEmail = secondClient.getEmail();

        return firstClientEmail.compareToIgnoreCase(secondClientEmail); // Compara duas strings por ordem alfabética
    }
}
