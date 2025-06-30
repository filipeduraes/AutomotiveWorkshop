// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.employee.AuthModule;
import com.filipeduraes.workshop.core.employee.Employee;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.financial.FinancialModule;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.SerializationAdapter;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.persistence.serializers.LocalDateTimeAdapter;
import com.filipeduraes.workshop.core.financial.Store;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Controla o sistema da oficina e as dependências dos módulos
 *
 * @author Filipe Durães
 */
public class Workshop
{
    private final AuthModule authModule;
    private final CrudRepository<Client> clientRepository;
    private final CrudRepository<Vehicle> vehicleRepository;
    private final Store store;
    private final FinancialModule financialModule;
    private MaintenanceModule maintenanceModule;

    /**
     * Cria uma nova instância de oficina
     * Pode usar ofuscação para tornar dados persistentes mais difíceis de ler por humanos.
     *
     * @param useObfuscation Determina se irá usar ou não ofuscação
     */
    public Workshop(boolean useObfuscation)
    {
        LocalDateTimeAdapter localDateTimeAdapter = new LocalDateTimeAdapter();

        List<SerializationAdapter> adapters = List.of
        (
            new SerializationAdapter(LocalDateTime.class, localDateTimeAdapter)
        );

        Persistence.setUseObfuscation(useObfuscation);
        Persistence.registerCustomSerializationAdapters(adapters);

        vehicleRepository = new CrudRepository<>(WorkshopPaths.REGISTERED_VEHICLES_PATH, Vehicle.class);
        clientRepository = new CrudRepository<>(WorkshopPaths.REGISTERED_CLIENTS_PATH, Client.class);
        authModule = new AuthModule();
        store = new Store();
        financialModule = new FinancialModule();

        authModule.OnUserLogged.addListener(this::initializeUserData);
        vehicleRepository.OnEntityRegistered.addListener(this::registerVehicleToOwner);
    }

    public void dispose()
    {
        authModule.OnUserLogged.removeListener(this::initializeUserData);
        vehicleRepository.OnEntityRegistered.removeListener(this::registerVehicleToOwner);

        if (maintenanceModule != null)
        {
            maintenanceModule.getServiceOrderRepository().OnEntityRegistered.removeListener(this::registerServiceOrderToOwner);
        }
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
    public CrudRepository<Client> getClientRepository()
    {
        return clientRepository;
    }

    /**
     * Obtém o módulo de veículos
     *
     * @return módulo de veículos
     */
    public CrudRepository<Vehicle> getVehicleRepository()
    {
        return vehicleRepository;
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

    /**
     * Obtém a loja de produtos
     *
     * @return loja de produtos
     */
    public Store getStore()
    {
        return store;
    }

    /**
     * Obtém o módulo financeiro
     *
     * @return módulo financeiro
     */
    public FinancialModule getFinancialModule()
    {
        return financialModule;
    }

    private void initializeUserData()
    {
        Employee loggedUser = authModule.getLoggedUser();
        WorkshopPaths.setCurrentLoggedUserID(loggedUser.getID());
        maintenanceModule = new MaintenanceModule(loggedUser.getID());
        maintenanceModule.getServiceOrderRepository().OnEntityRegistered.addListener(this::registerServiceOrderToOwner);
    }

    private void registerVehicleToOwner(Vehicle vehicle)
    {
        UUID ownerID = vehicle.getOwnerID();
        Client owner = clientRepository.getEntityWithID(ownerID);
        owner.addOwnedVehicle(vehicle.getID());
        clientRepository.updateEntity(owner);
    }

    private void registerServiceOrderToOwner(ServiceOrder serviceOrder)
    {
        UUID ownerID = serviceOrder.getClientID();
        Client owner = clientRepository.getEntityWithID(ownerID);
        owner.addServiceOrder(serviceOrder.getID());
        clientRepository.updateEntity(owner);
    }
}