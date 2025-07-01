// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.persistence;

import java.time.LocalDateTime;
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
     * Caminho para o arquivo com os dados dos elevadores automotivos
     */
    public static final String ELEVATORS_PATH = DATA_DIRECTORY_PATH + "Elevators" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de clientes registrados
     */
    public static final String REGISTERED_CLIENTS_PATH = DATA_DIRECTORY_PATH + "Clients" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo de veículos registrados
     */
    public static final String REGISTERED_VEHICLES_PATH = DATA_DIRECTORY_PATH + "Vehicles" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo do catálogo de serviços
     */
    public static final String SERVICE_CATALOG_PATH = DATA_DIRECTORY_PATH + "ServiceCatalog" + FILE_EXTENSION;
    /**
     * Caminho para o arquivo do catálogo de produtos da loja
     */
    public static final String STORE_ITEMS_CATALOG_PATH = DATA_DIRECTORY_PATH + "StoreItems" + FILE_EXTENSION;

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


    public static String getFinishedServicesCurrentMonthPath()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        return getFinishedServicesMonthPath(currentDate);
    }

    public static String getPurchasesCurrentMonthPath()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        return getSalesMonthPath(currentDate);
    }

    public static String getClockInCurrentMonthPath()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        return getClockInMonthPath(currentDate);
    }

    public static String getExpensesCurrentMonthPath()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        return getExpensesMonthPath(currentDate);
    }


    public static String getFinishedServicesMonthPath(LocalDateTime time)
    {
        String monthDirectory = getMonthDirectory(time);
        return String.format("%s/FinishedServices%s", monthDirectory, FILE_EXTENSION);
    }

    public static String getClockInMonthPath(LocalDateTime time)
    {
        String monthDirectory = getMonthDirectory(time);
        return String.format("%s/ClockIn%s", monthDirectory, FILE_EXTENSION);
    }

    public static String getSalesMonthPath(LocalDateTime time)
    {
        String monthDirectory = getMonthDirectory(time);
        return String.format("%s/Sales%s", monthDirectory, FILE_EXTENSION);
    }

    public static String getExpensesMonthPath(LocalDateTime time)
    {
        String monthDirectory = getMonthDirectory(time);
        return String.format("%s/Expenses%s", monthDirectory, FILE_EXTENSION);
    }


    private static String getMonthDirectory(LocalDateTime time)
    {
        return String.format("%s/%04d/%02d", DATA_DIRECTORY_PATH, time.getYear(), time.getMonthValue());
    }
}