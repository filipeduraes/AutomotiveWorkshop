// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.serviceorder;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.service.ServiceOrderDTO;
import com.filipeduraes.workshop.client.model.mappers.ServiceOrderMapper;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceOrderViewModel;
import com.filipeduraes.workshop.client.viewmodel.service.ServiceQueryType;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.maintenance.ServiceOrderModule;
import com.filipeduraes.workshop.core.maintenance.ServiceOrder;
import com.filipeduraes.workshop.core.maintenance.ServiceStep;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar consultas e buscas de ordens de serviço.
 * Esta classe coordena as operações de busca, filtragem e carregamento de dados
 * detalhados das ordens de serviço, fornecendo diferentes tipos de consulta
 * baseados em critérios específicos.
 *
 * @author Filipe Durães
 */
public class ServiceOrderQueryService
{
    private final ServiceOrderViewModel serviceOrderViewModel;
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final Workshop workshop;
    private List<ServiceOrder> queriedEntities = new ArrayList<>();

    /**
     * Constrói um novo serviço de consulta de ordens de serviço.
     * Inicializa as referências necessárias e registra os listeners para
     * responder aos eventos da interface do usuário.
     *
     * @param viewModelRegistry registro contendo as referências para os ViewModels
     * @param workshop instância principal da oficina contendo os módulos do sistema
     */
    public ServiceOrderQueryService(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        serviceOrderViewModel = viewModelRegistry.getServiceOrderViewModel();
        clientViewModel = viewModelRegistry.getClientViewModel();

        this.workshop = workshop;

        serviceOrderViewModel.OnSearchRequest.addListener(this::requestServices);
        serviceOrderViewModel.OnLoadDataRequest.addListener(this::requestDetailedServiceInfo);
    }

    /**
     * Remove todos os listeners registrados nos eventos dos ViewModels.
     * Deve ser chamado quando o serviço não for mais necessário para evitar vazamento de memória.
     */
    public void dispose()
    {
        serviceOrderViewModel.OnSearchRequest.removeListener(this::requestServices);
        serviceOrderViewModel.OnLoadDataRequest.removeListener(this::requestDetailedServiceInfo);
    }

    /**
     * Atualiza a entidade selecionada com dados frescos do repositório.
     * Recarrega os dados da ordem de serviço atualmente selecionada.
     */
    public void refreshSelectedEntity()
    {
        if(serviceOrderViewModel.hasLoadedDTO() && serviceOrderViewModel.hasValidSelectedIndex())
        {
            ServiceOrderModule serviceOrderModule = workshop.getMaintenanceModule();

            ServiceOrderDTO selectedDTO = serviceOrderViewModel.getSelectedDTO();
            ServiceOrder updatedServiceOrder = serviceOrderModule.getServiceOrderRepository().getEntityWithID(selectedDTO.getID());
            ServiceOrderDTO updatedServiceOrderDTO = ServiceOrderMapper.toDTO(updatedServiceOrder, workshop);

            queriedEntities.set(serviceOrderViewModel.getSelectedIndex(), updatedServiceOrder);
            serviceOrderViewModel.setSelectedDTO(updatedServiceOrderDTO);
        }
    }

    /**
     * Processa a solicitação de busca de serviços.
     * Executa a busca com base no tipo de consulta e filtros configurados.
     */
    private void requestServices()
    {
        ServiceQueryType queryType = serviceOrderViewModel.getQueryType();
        FieldType filterType = serviceOrderViewModel.getFieldType();

        if (filterType == FieldType.NONE)
        {
            queriedEntities = getServicesWithoutFiltering(queryType);
        }
        else if (filterType == FieldType.CLIENT)
        {
            ClientDTO selectedDTO = clientViewModel.getSelectedDTO();

            if (selectedDTO != null)
            {
                queriedEntities = getServicesFilteringByClient(queryType, selectedDTO.getID());
            }
            else
            {
                queriedEntities = getServicesWithoutFiltering(queryType);
            }
        }
        else if (filterType == FieldType.DESCRIPTION)
        {
            String descriptionQueryPattern = serviceOrderViewModel.getDescriptionQueryPattern();
            queriedEntities = getServicesFilteringByDescription(queryType, descriptionQueryPattern);
        }

        List<String> descriptions = queriedEntities.stream()
                                                   .map(this::getServiceListingName)
                                                   .collect(Collectors.toList());

        serviceOrderViewModel.setFoundEntitiesDescriptions(descriptions);
        serviceOrderViewModel.setRequestWasSuccessful(true);
    }

    /**
     * Carrega informações detalhadas da ordem de serviço selecionada.
     * Recupera e atualiza os dados completos da ordem de serviço escolhida.
     */
    private void requestDetailedServiceInfo()
    {
        int selectedMaintenanceIndex = serviceOrderViewModel.getSelectedIndex();

        if (selectedMaintenanceIndex < 0 || selectedMaintenanceIndex >= queriedEntities.size())
        {
            serviceOrderViewModel.setRequestWasSuccessful(false);
            return;
        }

        ServiceOrder queriedServiceOrder = queriedEntities.get(selectedMaintenanceIndex);
        ServiceOrder serviceOrder = workshop.getMaintenanceModule().getServiceOrderRepository().getEntityWithID(queriedServiceOrder.getID());
        ServiceOrderDTO serviceOrderDTO = ServiceOrderMapper.toDTO(serviceOrder, workshop);

        queriedEntities.set(selectedMaintenanceIndex, serviceOrder);
        serviceOrderViewModel.setSelectedDTO(serviceOrderDTO);
        serviceOrderViewModel.setRequestWasSuccessful(true);
    }

    /**
     * Gera o nome de exibição para uma ordem de serviço na listagem.
     * Cria uma string formatada com informações relevantes do serviço.
     *
     * @param service ordem de serviço para gerar o nome
     * @return string formatada com o nome da listagem
     */
    private String getServiceListingName(ServiceOrder service)
    {
        ServiceStep currentStep = service.getCurrentStep();
        UUID clientID = service.getClientID();
        UUID vehicleID = service.getVehicleID();

        Client owner = workshop.getClientRepository().getEntityWithID(clientID);
        Vehicle vehicle = workshop.getVehicleRepository().getEntityWithID(vehicleID);

        if (owner == null || vehicle == null)
        {
            return "INVALID_SERVICE";
        }

        LocalDateTime startDate = currentStep.getStartDate();
        String shortDescription;

        if (!currentStep.getWasFinished())
        {
            shortDescription = service.getPreviousStep().getShortDescription();
        }
        else
        {
            shortDescription = currentStep.getShortDescription();
        }

        return String.format("%s — %s — %s — %s", shortDescription, owner.getName(), vehicle, TextUtils.formatDate(startDate));
    }

    /**
     * Obtém serviços sem aplicar filtros específicos.
     *
     * @param queryType tipo de consulta a ser aplicada
     * @return lista de serviços filtrados apenas pelo tipo de consulta
     */
    private List<ServiceOrder> getServicesWithoutFiltering(ServiceQueryType queryType)
    {
        return getServicesFiltering(queryType, s -> true);
    }

    /**
     * Obtém serviços filtrados por cliente específico.
     *
     * @param queryType tipo de consulta a ser aplicada
     * @param clientID identificador do cliente para filtrar
     * @return lista de serviços do cliente especificado
     */
    private List<ServiceOrder> getServicesFilteringByClient(ServiceQueryType queryType, UUID clientID)
    {
        return getServicesFiltering(queryType, s -> s.getClientID().equals(clientID));
    }

    /**
     * Obtém serviços filtrados por descrição.
     *
     * @param queryType tipo de consulta a ser aplicada
     * @param pattern padrão de texto para buscar nas descrições
     * @return lista de serviços que contêm o padrão nas descrições
     */
    private List<ServiceOrder> getServicesFilteringByDescription(ServiceQueryType queryType, String pattern)
    {
        String lowerCasePattern = pattern.toLowerCase();

        return getServicesFiltering
        (
            queryType, s ->
            {
                String shortDescription = s.getCurrentStep().getShortDescription().toLowerCase();
                String detailedDescription = s.getCurrentStep().getDetailedDescription().toLowerCase();

                return shortDescription.contains(lowerCasePattern) || detailedDescription.contains(lowerCasePattern);
            }
        );
    }

    /**
     * Aplica filtros personalizados aos serviços.
     * Combina o tipo de consulta com um predicado personalizado para filtrar os serviços.
     *
     * @param queryType tipo de consulta a ser aplicada
     * @param filter predicado personalizado para filtrar os serviços
     * @return lista de serviços que atendem aos critérios de filtro
     */
    private List<ServiceOrder> getServicesFiltering(ServiceQueryType queryType, Predicate<ServiceOrder> filter)
    {
        ServiceOrderModule maintenanceModule = workshop.getMaintenanceModule();
        final CrudRepository<ServiceOrder> serviceOrderModule = maintenanceModule.getServiceOrderRepository();
        Set<UUID> loadedClosedServices = maintenanceModule.loadClosedServicesInMonth(LocalDateTime.now());

        Predicate<ServiceOrder> typePredicate = switch (queryType)
        {
            case OPENED -> s -> maintenanceModule.getOpenedServices().contains(s.getID());
            case USER -> s -> maintenanceModule.getUserServices().contains(s.getID());
            case GENERAL -> s -> true;
            case CLOSED_IN_CURRENT_MONTH -> s -> loadedClosedServices.contains(s.getID());
        };

        return serviceOrderModule.findEntitiesWithPredicate(s -> typePredicate.test(s) && filter.test(s));
    }
}
