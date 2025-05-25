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
    private static final String DATA_DIRECTORY_PATH = "./Data/";
    private static final String FILE_EXTENSION = ".workshop";
    public static final String REGISTERED_EMPLOYEES_PATH = DATA_DIRECTORY_PATH + "Users" + FILE_EXTENSION;
    public static final String OPENED_SERVICES_PATH = DATA_DIRECTORY_PATH + "OpenedServices" + FILE_EXTENSION;
    public static final String ONGOING_SERVICES_PATH = DATA_DIRECTORY_PATH + "Services" + FILE_EXTENSION;
    public static final String REGISTERED_CLIENTS_PATH = DATA_DIRECTORY_PATH + "Clients" + FILE_EXTENSION;
    public static final String REGISTERED_VEHICLES_PATH = DATA_DIRECTORY_PATH + "Vehicles" + FILE_EXTENSION;

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
        return String.format("%sServices/%s_Services%s", DATA_DIRECTORY_PATH, userID.toString(), FILE_EXTENSION);
    }
}