// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.model.mappers.VehicleMapper;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controlador responsável por gerenciar as operações relacionadas a veículos no sistema.
 * Coordena a comunicação entre a interface do usuário e os dados dos veículos,
 * processando requisições de busca, registro e detalhamento de veículos.
 *
 * @author Filipe Durães
 */
public class VehicleController
{
    private final EntityViewModel<VehicleDTO> vehicleViewModel;
    private final EntityViewModel<ClientDTO> clientViewModel;
    private final CrudRepository<Vehicle> vehicleModule;
    private final CrudRepository<Client> clientModule;

    /**
     * Cria uma nova instância do controlador de veículos.
     * Inicializa os listeners para processar as requisições relacionadas a veículos.
     *
     * @param viewModelRegistry registro contendo as referências dos ViewModels
     * @param workshop instância principal da oficina contendo os repositórios
     */
    public VehicleController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        this.vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        this.clientViewModel = viewModelRegistry.getClientViewModel();
        this.vehicleModule = workshop.getVehicleRepository();
        this.clientModule = workshop.getClientRepository();

        vehicleViewModel.OnLoadDataRequest.addListener(this::processVehicleDetailsRequest);
        vehicleViewModel.OnSearchRequest.addListener(this::processClientVehiclesRequest);
        vehicleViewModel.OnRegisterRequest.addListener(this::processVehicleRegistrationRequest);
    }

    /**
     * Remove os listeners registrados pelo controlador.
     * Deve ser chamado quando o controlador não for mais necessário.
     */
    public void dispose()
    {
        vehicleViewModel.OnLoadDataRequest.removeListener(this::processVehicleDetailsRequest);
        vehicleViewModel.OnSearchRequest.removeListener(this::processClientVehiclesRequest);
        vehicleViewModel.OnRegisterRequest.removeListener(this::processVehicleRegistrationRequest);
    }

    private void processClientVehiclesRequest()
    {
        Client client = getSelectedClient();

        if (client != null)
        {
            ArrayList<String> vehicleNames = new ArrayList<>();

            for (UUID ownedVehiclesID : client.getOwnedVehiclesIDs())
            {
                Vehicle vehicle = vehicleModule.getEntityWithID(ownedVehiclesID);
                vehicleNames.add(String.format("%s %d - %s", vehicle.getModel(), vehicle.getYear(), vehicle.getLicensePlate()));
            }

            vehicleViewModel.setFoundEntitiesDescriptions(vehicleNames);
            vehicleViewModel.setRequestWasSuccessful(true);
        }
    }

    private void processVehicleRegistrationRequest()
    {
        Client selectedClient = getSelectedClient();

        if (selectedClient != null)
        {
            VehicleDTO registerRequestedVehicleDTO = vehicleViewModel.getSelectedDTO();

            Vehicle vehicle = VehicleMapper.fromDTO(selectedClient.getID(), registerRequestedVehicleDTO);
            UUID vehicleID = vehicleModule.registerEntity(vehicle);
            registerRequestedVehicleDTO.setID(vehicleID);

            vehicleViewModel.setRequestWasSuccessful(true);
        }
    }

    private void processVehicleDetailsRequest()
    {
        Client selectedClient = getSelectedClient();

        if (selectedClient != null)
        {
            int selectedVehicleIndex = vehicleViewModel.getSelectedIndex();
            List<UUID> ownedVehiclesIDs = selectedClient.getOwnedVehiclesIDs();

            boolean wasRequestSuccessful = selectedVehicleIndex >= 0 && selectedVehicleIndex < ownedVehiclesIDs.size();

            if (wasRequestSuccessful)
            {
                UUID selectedVehicleID = ownedVehiclesIDs.get(selectedVehicleIndex);
                Vehicle vehicle = vehicleModule.getEntityWithID(selectedVehicleID);

                VehicleDTO selectedVehicle = VehicleMapper.toDTO(vehicle);

                vehicleViewModel.setSelectedDTO(selectedVehicle);
            }

            vehicleViewModel.setRequestWasSuccessful(wasRequestSuccessful);
            return;
        }

        vehicleViewModel.setRequestWasSuccessful(false);
    }

    private Client getSelectedClient()
    {
        String clientIDAsString = vehicleViewModel.getSearchPattern();
        UUID clientID = UUID.fromString(clientIDAsString);
        Client client = clientModule.getEntityWithID(clientID);

        return client;
    }
}