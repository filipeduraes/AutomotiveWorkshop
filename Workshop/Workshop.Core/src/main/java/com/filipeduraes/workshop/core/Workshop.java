// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.client.ClientModule;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.SerializationAdapterGroup;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.persistence.serializers.DateTimeSerializer;
import com.filipeduraes.workshop.core.vehicle.VehicleModule;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controla o sistema da oficina e as dependências dos módulos
 *
 * @author Filipe Durães
 */
public class Workshop
{
    private AuthModule authModule = new AuthModule();
    private ClientModule clientModule = new ClientModule();
    private VehicleModule vehicleModule = new VehicleModule();
    private MaintenanceModule maintenanceModule;

    /**
     * Cria uma nova instância de oficina
     * Pode usar ofuscação para tornar dados persistentes mais difíceis de ler por humanos.
     */
    public Workshop(boolean useObfuscation)
    {
        DateTimeSerializer dateTimeSerializer = new DateTimeSerializer();

        ArrayList<SerializationAdapterGroup> adapters = new ArrayList<>();
        adapters.add(new SerializationAdapterGroup(LocalDate.class, dateTimeSerializer, dateTimeSerializer));

        Persistence.setUseObfuscation(useObfuscation);
        Persistence.registerCustomSerializationAdapters(adapters);

        authModule.OnUserLogged.addListener(this::initializeUserData);
    }

    public void dispose()
    {
        authModule.OnUserLogged.removeListener(this::initializeUserData);
    }

    /**
     * Obtém o módulo de autenticação
     *
     * @return módulo de autenticação
     */
    public AuthModule getAuthModule()
    {
        return authModule;
    }

    /**
     * Obtém o módulo de clientes
     *
     * @return módulo de clientes
     */
    public ClientModule getClientModule()
    {
        return clientModule;
    }

    /**
     * Obtém o módulo de veículos
     *
     * @return módulo de veículos
     */
    public VehicleModule getVehicleModule()
    {
        return vehicleModule;
    }

    /**
     * Obtém o módulo de manutenção
     *
     * @return módulo de manutenção
     */
    public MaintenanceModule getMaintenanceModule()
    {
        return maintenanceModule;
    }

    private void initializeUserData()
    {
        Employee loggedUser = authModule.getLoggedUser();
        WorkshopPaths.setCurrentLoggedUserID(loggedUser.getID());
        maintenanceModule = new MaintenanceModule(loggedUser.getID());
    }
}