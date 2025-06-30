// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.client;

import java.util.Comparator;

public class ClientNameComparator implements Comparator<Client>
{
    @Override
    public int compare(Client firstClient, Client secondClient)
    {
        String firstClientName = firstClient.getName();
        String secondClientName = secondClient.getName();

        return firstClientName.compareToIgnoreCase(secondClientName); // Compara duas strings por ordem alfabética
    }
}