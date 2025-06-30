// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.client;

import java.util.Comparator;

public class ClientEmailComparator implements Comparator<Client>
{
    @Override
    public int compare(Client firstClient, Client secondClient)
    {
        String firstClientEmail = firstClient.getEmail();
        String secondClientEmail = secondClient.getEmail();

        return firstClientEmail.compareToIgnoreCase(secondClientEmail); // Compara duas strings por ordem alfabética
    }
}
