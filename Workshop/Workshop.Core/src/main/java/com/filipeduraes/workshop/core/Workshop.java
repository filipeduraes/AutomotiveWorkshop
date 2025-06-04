// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.SerializationAdapterGroup;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.persistence.serializers.DateTimeSerializer;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Controla o sistema da oficina e as dependências dos módulos
 *
 * @author Filipe Durães
 */
public class Workshop
{
    private final AuthModule authModule = new AuthModule();
    private final CrudModule<Client> clientModule;
    private final CrudModule<Vehicle> vehicleModule;
    private MaintenanceModule maintenanceModule;

    /**
     * Cria uma nova instância de oficina
     * Pode usar ofuscação para tornar dados persistentes mais difíceis de ler por humanos.
     * 
     * @param useObfuscation Determina se irá usar ou não ofuscação
     */
    public Workshop(boolean useObfuscation)
    {
        DateTimeSerializer dateTimeSerializer = new DateTimeSerializer();

        ArrayList<SerializationAdapterGroup> adapters = new ArrayList<>();
        adapters.add(new SerializationAdapterGroup(LocalDateTime.class, dateTimeSerializer, dateTimeSerializer));

        Persistence.setUseObfuscation(useObfuscation);
        Persistence.registerCustomSerializationAdapters(adapters);

        vehicleModule = new CrudModule<>(WorkshopPaths.REGISTERED_VEHICLES_PATH, Vehicle.class);
        clientModule = new CrudModule<>(WorkshopPaths.REGISTERED_CLIENTS_PATH, Client.class);

        authModule.OnUserLogged.addListener(this::initializeUserData);
        vehicleModule.OnEntityRegistered.addListener(this::registerVehicleToOwner);
    }

    public void dispose()
    {
        authModule.OnUserLogged.removeListener(this::initializeUserData);
        vehicleModule.OnEntityRegistered.removeListener(this::registerVehicleToOwner);
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
    public CrudModule<Client> getClientModule()
    {
        return clientModule;
    }

    /**
     * Obtém o módulo de veículos
     *
     * @return módulo de veículos
     */
    public CrudModule<Vehicle> getVehicleModule()
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

    private void registerVehicleToOwner(Vehicle vehicle)
    {
        UUID ownerID = vehicle.getOwnerID();
        Client owner = clientModule.getEntityWithID(ownerID);
        owner.addOwnedVehicle(vehicle.getID());
        clientModule.updateEntity(owner);
    }
}