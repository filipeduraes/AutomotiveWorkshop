// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.WorkshopEntity;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.catalog.ServiceItem;
import com.filipeduraes.workshop.core.financial.Sale;

import java.math.BigDecimal;
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
    private final List<ServiceStep> steps = new ArrayList<>();
    private List<ServiceItem> services = new ArrayList<>();
    private List<Sale> sales = new ArrayList<>();

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
     * Cria uma cópia profunda de uma ordem de serviço existente.
     * Todos os atributos são copiados, incluindo as etapas de serviço e produtos.
     *
     * @param serviceOrder ordem de serviço a ser copiada
     */
    public ServiceOrder(ServiceOrder serviceOrder)
    {
        super(serviceOrder);

        vehicleID = serviceOrder.vehicleID;
        clientID = serviceOrder.clientID;
        sales = serviceOrder.sales;
        finished = serviceOrder.finished;
        services = serviceOrder.services;

        for (ServiceStep step : serviceOrder.steps)
        {
            steps.add(new ServiceStep(step));
        }

        for (ServiceItem service : serviceOrder.services)
        {
            services.add(new ServiceItem(service));
        }
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
        steps.add(step);
    }

    /**
     * Obtém a etapa atual do serviço.
     *
     * @return etapa atual ou null se não houver etapa em andamento
     */
    public ServiceStep getCurrentStep()
    {
        return steps.get(steps.size() - 1);
    }

    /**
     * Obtém a etapa anterior do serviço.
     *
     * @return etapa anterior ou a atual se for a primeira
     */
    public ServiceStep getPreviousStep()
    {
        int previousStepIndex = steps.size() - 2;

        if(previousStepIndex < 0)
        {
            return getCurrentStep();
        }

        return steps.get(previousStepIndex);
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
     */
    public void finish()
    {
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
    public List<ServiceStep> getSteps()
    {
        return steps;
    }

    /**
     * Registra uma nova venda realizada para a ordem de serviço
     *
     * @param sale venda realizada
     */
    public void registerSale(Sale sale)
    {
        sales.add(sale);
    }

    /**
     * Registra um novo serviço realizado e atribui o responsável atual a ele
     *
     * @param service serviço realizado
     */
    public void registerService(PricedItem service)
    {
        UUID currentStepEmployeeID = getCurrentStep().getEmployeeID();
        ServiceItem serviceItem = new ServiceItem(service, currentStepEmployeeID);
        services.add(serviceItem);
    }

    /**
     * Obtém todos os serviços prestados na ordem de serviço.
     *
     * @return os serviços prestados
     */
    public List<ServiceItem> getServices()
    {
        if(services == null)
        {
            services = new ArrayList<>();
        }

        return services;
    }

    /**
     * Obtém todas as vendas realizadas na ordem de serviço.
     *
     * @return as vendas realizadas
     */
    public List<Sale> getSales()
    {
        if(sales == null)
        {
            sales = new ArrayList<>();
        }

        return sales;
    }

    private void finishCurrentStep()
    {
        if (!steps.isEmpty())
        {
            getCurrentStep().finishStep();
        }
    }

    public BigDecimal getTotalPrice()
    {
        BigDecimal totalPrice = BigDecimal.ZERO;

        totalPrice = totalPrice.add(getSalesPrice());
        totalPrice = totalPrice.add(getServicesPrice());

        return totalPrice;
    }

    public BigDecimal getSalesPrice()
    {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(Sale sale : getSales())
        {
            totalPrice = totalPrice.add(sale.getTotalPrice());
        }

        return totalPrice;
    }

    public BigDecimal getServicesPrice()
    {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(ServiceItem service : getServices())
        {
            totalPrice = totalPrice.add(service.getPrice());
        }

        return totalPrice;
    }
}