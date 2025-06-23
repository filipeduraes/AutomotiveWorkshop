// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import java.util.UUID;

/**
 * Armazena todos os caminhos relativos que estão sendo usados no projeto.
 * Esta classe fornece acesso centralizado aos caminhos de arquivos do sistema.
 *
 * @author Filipe Durães
 */
public final class WorkshopPaths
{
    private static final String DATA_DIRECTORY_PATH = "./Data/";
    private static final String FILE_EXTENSION = ".workshop";
    /**
     * Caminho para o arquivo de funcionários registrados
     */
    public static final String REGISTERED_EMPLOYEES_PATH = DATA_DIRECTORY_PATH + "Users" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de serviços abertos
     */
    public static final String OPENED_SERVICES_PATH = DATA_DIRECTORY_PATH + "OpenedServices" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de serviços em andamento
     */
    public static final String SERVICES_PATH = DATA_DIRECTORY_PATH + "OngoingServices" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de serviços finalizados
     */
    public static final String FINISHED_SERVICES_PATH = DATA_DIRECTORY_PATH + "FinishedServices" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de clientes registrados
     */
    public static final String REGISTERED_CLIENTS_PATH = DATA_DIRECTORY_PATH + "Clients" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de veículos registrados
     */
    public static final String REGISTERED_VEHICLES_PATH = DATA_DIRECTORY_PATH + "Vehicles" + FILE_EXTENSION;

    private static UUID loggedUserID;

    private WorkshopPaths()
    {
    }

    /**
     * Define o ID do usuário atualmente logado no sistema.
     *
     * @param newLoggedUserID ID do usuário logado
     */
    public static void setCurrentLoggedUserID(UUID newLoggedUserID)
    {
        loggedUserID = newLoggedUserID;
    }

    /**
     * Obtém o caminho para os serviços do usuário atualmente logado.
     *
     * @return caminho para os serviços do usuário
     */
    public static String getUserServicesPath()
    {
        return getUserServicesPath(loggedUserID);
    }

    /**
     * Obtém o caminho para os serviços de um usuário específico.
     *
     * @param userID ID do usuário
     * @return caminho para os serviços do usuário especificado
     */
    public static String getUserServicesPath(UUID userID)
    {
        return String.format("%sServices/%s_Services%s", DATA_DIRECTORY_PATH, userID.toString(), FILE_EXTENSION);
    }
}