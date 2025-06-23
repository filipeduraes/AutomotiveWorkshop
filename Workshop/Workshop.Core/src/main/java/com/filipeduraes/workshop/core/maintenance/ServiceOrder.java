// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.core.catalog.Product;
import com.filipeduraes.workshop.core.store.Purchase;

import java.util.*;

/**
 * Representa uma ordem de serviço na oficina.
 * Mantém o registro de todas as etapas do serviço, produtos utilizados
 * e informações sobre o cliente e veículo.
 *
 * @author Filipe Durães
 */
public class ServiceOrder extends WorkshopEntity
{
    private final Deque<ServiceStep> steps = new ArrayDeque<>();
    private List<Product> services = new ArrayList<>();

    private Purchase purchase;
    private UUID clientID;
    private UUID vehicleID;
    private boolean finished = false;

    /**
     * Cria uma nova ordem de serviço.
     *
     * @param clientID ID do cliente
     * @param vehicleID ID do veículo
     */
    public ServiceOrder(UUID clientID, UUID vehicleID)
    {
        this.vehicleID = vehicleID;
        this.clientID = clientID;
    }

    /**
     * Verifica se a ordem de serviço foi finalizada.
     *
     * @return true se a ordem foi finalizada, false caso contrário
     */
    public boolean getFinished()
    {
        return finished;
    }

    /**
     * Registra uma nova etapa na ordem de serviço.
     * Finaliza a etapa atual antes de registrar a nova.
     *
     * @param step nova etapa a ser registrada
     */
    public void registerStep(ServiceStep step)
    {
        finishCurrentStep();
        steps.push(step);
    }

    /**
     * Obtém a etapa atual do serviço.
     *
     * @return etapa atual ou null se não houver etapa em andamento
     */
    public ServiceStep getCurrentStep()
    {
        return steps.peek();
    }

    /**
     * Obtém a etapa atual de manutenção com base no número de etapas concluídas.
     *
     * @return etapa atual de manutenção
     */
    public MaintenanceStep getCurrentMaintenanceStep()
    {
        return MaintenanceStep.fromInt(steps.size());
    }

    /**
     * Finaliza a ordem de serviço.
     *
     * @param services lista de produtos/serviços utilizados
     * @param purchase informações da compra associada
     */
    public void finish(ArrayList<Product> services, Purchase purchase)
    {
        this.services = services;
        this.purchase = purchase;
        finishCurrentStep();

        finished = true;
    }

    /**
     * Obtém o ID do cliente associado à ordem.
     *
     * @return ID do cliente
     */
    public UUID getClientID()
    {
        return clientID;
    }

    /**
     * Define o ID do cliente associado à ordem.
     *
     * @param clientID novo ID do cliente
     */
    public void setClientID(UUID clientID)
    {
        this.clientID = clientID;
    }

    /**
     * Obtém o ID do veículo associado à ordem.
     *
     * @return ID do veículo
     */
    public UUID getVehicleID()
    {
        return vehicleID;
    }

    /**
     * Define o ID do veículo associado à ordem.
     *
     * @param vehicleID novo ID do veículo
     */
    public void setVehicleID(UUID vehicleID)
    {
        this.vehicleID = vehicleID;
    }

    /**
     * Verifica se a etapa atual foi finalizada.
     *
     * @return true se a etapa atual foi finalizada, false caso contrário
     */
    public boolean getCurrentStepWasFinished()
    {
        return getCurrentStep().getWasFinished();
    }

    /**
     * Obtém todas as etapas da ordem de serviço.
     *
     * @return coleção de etapas do serviço
     */
    public Deque<ServiceStep> getSteps()
    {
        return steps;
    }

    private void finishCurrentStep()
    {
        if (!steps.isEmpty())
        {
            getCurrentStep().finishStep();
        }
    }
}