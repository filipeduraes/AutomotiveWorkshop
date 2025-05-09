// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.auth.AuthModule;
import com.filipeduraes.workshop.core.client.ClientModule;
import com.filipeduraes.workshop.core.maintenance.MaintenanceModule;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.SerializationAdapterGroup;
import com.filipeduraes.workshop.core.persistence.serializers.DateTimeSerializer;
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
     * Obtém o módulo de manutenção
     *
     * @return módulo de manutenção
     */
    public MaintenanceModule getMaintenanceModule()
    {
        return maintenanceModule;
    }
}
