// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.maintenance;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.catalog.PricedItem;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.core.store.Purchase;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Módulo responsável pelo gerenciamento de ordens de serviço e manutenções na oficina.
 * Controla o fluxo de trabalho desde o agendamento inicial até a conclusão do serviço.
 *
 * @author Filipe Durães
 */
public class MaintenanceModule
{
    private final CrudRepository<ServiceOrder> serviceOrderRepository;
    private final Set<UUID> userServices;
    private final Set<UUID> openedServices;
    private final UUID loggedEmployeeID;
    private final ParameterizedType serviceIDSetType;

    /**
     * Cria uma nova instância do módulo de manutenção.
     *
     * @param loggedEmployeeID ID do funcionário que está logado no sistema
     */
    public MaintenanceModule(UUID loggedEmployeeID)
    {
        this.loggedEmployeeID = loggedEmployeeID;

        serviceIDSetType = Persistence.createParameterizedType(HashSet.class, UUID.class);

        serviceOrderRepository = new CrudRepository<>(WorkshopPaths.SERVICES_PATH, ServiceOrder.class);
        openedServices = Persistence.loadFile(WorkshopPaths.OPENED_SERVICES_PATH, serviceIDSetType, new HashSet<>());
        userServices = Persistence.loadFile(WorkshopPaths.getUserServicesPath(), serviceIDSetType, new HashSet<>());
    }

    /**
     * Obtém o repositório de ordens de serviço.
     *
     * @return repositório que gerencia as ordens de serviço
     */
    public CrudRepository<ServiceOrder> getServiceOrderRepository()
    {
        return serviceOrderRepository;
    }

    /**
     * Obtém o conjunto de IDs dos serviços atribuídos ao funcionário atual.
     *
     * @return conjunto de IDs dos serviços do funcionário
     */
    public Set<UUID> getUserServices()
    {
        return userServices;
    }

    /**
     * Obtém o conjunto de IDs dos serviços que estão em aberto na oficina.
     *
     * @return conjunto de IDs dos serviços em aberto
     */
    public Set<UUID> getOpenedServices()
    {
        return openedServices;
    }

    /**
     * Registra um novo agendamento de serviço na oficina.
     *
     * @param clientID ID do cliente solicitante do serviço
     * @param vehicleID ID do veículo que receberá o serviço
     * @param shortDescription descrição curta do problema ou serviço
     * @param detailedDescription descrição detalhada do problema ou serviço
     * @return ID do serviço criado
     */
    public UUID registerNewAppointment(UUID clientID, UUID vehicleID, String shortDescription, String detailedDescription)
    {
        ServiceOrder serviceOrder = new ServiceOrder(clientID, vehicleID);

        serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
        serviceOrder.getCurrentStep().setShortDescription(shortDescription);
        serviceOrder.getCurrentStep().setDetailedDescription(detailedDescription);
        serviceOrder.getCurrentStep().finishStep();

        UUID serviceID = serviceOrderRepository.registerEntity(serviceOrder);
        openedServices.add(serviceID);

        Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
        return serviceID;
    }

    /**
     * Inicia o processo de inspeção de um serviço agendado.
     *
     * @param serviceID ID do serviço a ser inspecionado
     */
    public boolean startInspection(UUID serviceID)
    {
        ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);

        if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.APPOINTMENT)
        {
            serviceOrder.registerStep(new ServiceStep(loggedEmployeeID));
            openedServices.remove(serviceID);
            userServices.add(serviceID);

            Persistence.saveFile(openedServices, WorkshopPaths.OPENED_SERVICES_PATH);
            Persistence.saveFile(userServices, WorkshopPaths.getUserServicesPath());
            return serviceOrderRepository.updateEntity(serviceOrder);
        }

        return false;
    }

    /**
     * Finaliza a inspeção de um serviço e o encaminha para outro funcionário.
     *
     * @param serviceID ID do serviço inspecionado
     * @param newEmployee ID do funcionário que receberá o serviço
     * @param shortDescription descrição curta dos resultados da inspeção
     * @param detailedDescription descrição dos resultados da inspeção
     */
    public boolean finishInspection(UUID serviceID, UUID newEmployee, String shortDescription, String detailedDescription)
    {
        if (loggedUserHasService(serviceID))
        {
            ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);

            if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
            {
                ServiceStep currentStep = serviceOrder.getCurrentStep();

                currentStep.setShortDescription(shortDescription);
                currentStep.setDetailedDescription(detailedDescription);
                currentStep.finishStep();

                removeServiceOrderFromEmployeeServices(serviceID);
                addServiceOrderToEmployeeServices(serviceID, newEmployee);

                return serviceOrderRepository.updateEntity(serviceOrder);
            }
        }

        return false;
    }

    /**
     * Inicia o processo de manutenção de um serviço após a inspeção.
     *
     * @param serviceID ID do serviço a ser iniciado
     */
    public boolean startMaintenance(UUID serviceID)
    {
        ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);

        if (serviceOrder.getCurrentMaintenanceStep() == MaintenanceStep.ASSESSMENT)
        {
            ServiceStep serviceStep = new ServiceStep(loggedEmployeeID);
            serviceOrder.registerStep(serviceStep);

            return serviceOrderRepository.updateEntity(serviceOrder);
        }

        return false;
    }

    /**
     * Finaliza o processo de manutenção de um serviço.
     *
     * @param serviceID ID do serviço a ser finalizado
     * @param detailedDescription descrição do trabalho realizado
     * @param services lista de produtos utilizados no serviço
     * @param purchase informações da compra associada ao serviço
     */
    public boolean finishMaintenance(UUID serviceID, String shortDescription, String detailedDescription, ArrayList<PricedItem> services, Purchase purchase)
    {
        ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceID);
        ServiceStep currentStep = serviceOrder.getCurrentStep();
        currentStep.setShortDescription(shortDescription);
        currentStep.setDetailedDescription(detailedDescription);
        currentStep.finishStep();

        serviceOrder.finish(services, purchase);

        Set<UUID> finishedServices = Persistence.loadFile(WorkshopPaths.FINISHED_SERVICES_PATH, serviceIDSetType, new HashSet<>());
        finishedServices.add(serviceID);

        removeServiceOrderFromEmployeeServices(serviceID);
        Persistence.saveFile(finishedServices, WorkshopPaths.FINISHED_SERVICES_PATH);
        return serviceOrderRepository.updateEntity(serviceOrder);
    }

    /**
     * Remove uma ordem de serviço do sistema.
     *
     * @param serviceOrderID ID do serviço a ser removido
     */
    public void deleteServiceOrder(UUID serviceOrderID)
    {
        removeServiceOrderFromEmployeeServices(serviceOrderID);

        serviceOrderRepository.deleteEntityWithID(serviceOrderID);
    }

    private void addServiceOrderToEmployeeServices(UUID serviceOrderID, UUID employeeID)
    {
        Set<UUID> employeeUserServices = loadEmployeeUserServices(employeeID);
        employeeUserServices.add(serviceOrderID);
        saveEmployeeUserServices(employeeID, employeeUserServices);
    }

    private void removeServiceOrderFromEmployeeServices(UUID serviceOrderID)
    {
        ServiceOrder serviceOrder = serviceOrderRepository.getEntityWithID(serviceOrderID);
        UUID employeeID = serviceOrder.getCurrentStep().getEmployeeID();

        Set<UUID> employeeUserServices = loadEmployeeUserServices(employeeID);
        employeeUserServices.remove(serviceOrderID);

        saveEmployeeUserServices(employeeID, employeeUserServices);
    }

    private Set<UUID> loadEmployeeUserServices(UUID employeeID)
    {
        if(!employeeID.equals(loggedEmployeeID))
        {
            String employeeUserPath = WorkshopPaths.getUserServicesPath(employeeID);
            return Persistence.loadFile(employeeUserPath, serviceIDSetType, new HashSet<>());
        }

        return userServices;
    }

    private void saveEmployeeUserServices(UUID employeeID, Set<UUID> services)
    {
        String employeeUserPath = WorkshopPaths.getUserServicesPath(employeeID);
        Persistence.saveFile(services, employeeUserPath);
    }

    private boolean loggedUserHasService(UUID serviceID)
    {
        return userServices.contains(serviceID) && serviceOrderRepository.hasEntityWithID(serviceID);
    }
}