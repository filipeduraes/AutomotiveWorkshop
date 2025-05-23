// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import java.util.UUID;

/**
 * Armazena todos os caminhos relativos que estão sendo usados no projeto.
 *
 * @author Filipe Durães
 */
public final class WorkshopPaths 
{
    private static final String DataDirectoryPath = "./Data/";
    public static final String RegisteredEmployeesPath = DataDirectoryPath + "Users.workshop";
    public static final String OpenedServicesPath = DataDirectoryPath + "OpenedServices.workshop";
    public static final String OngoingServicesPath = DataDirectoryPath + "Services.workshop";
    public static final String RegisteredClientsPath = DataDirectoryPath + "Clients.workshop";
    public static final String RegisteredVehiclesPath = DataDirectoryPath + "Vehicles.workshop";

    private static UUID loggedUserID;

    private WorkshopPaths() {}

    public static void setCurrentLoggedUserID(UUID newLoggedUserID)
    {
        loggedUserID = newLoggedUserID;
    }

    public static String getUserServicesPath()
    {
        return getUserServicesPath(loggedUserID);
    }

    public static String getUserServicesPath(UUID userID)
    {
        return DataDirectoryPath + "Services/" + userID + "_Services.workshop";
    }
}