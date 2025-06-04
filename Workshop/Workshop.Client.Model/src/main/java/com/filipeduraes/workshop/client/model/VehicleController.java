package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleRequest;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.core.CrudModule;
import com.filipeduraes.workshop.core.Workshop;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VehicleController
{
    private final VehicleViewModel vehicleViewModel;
    private final ClientViewModel clientViewModel;
    private final CrudModule<Vehicle> vehicleModule;
    private final CrudModule<Client> clientModule;

    public VehicleController(ViewModelRegistry viewModelRegistry, Workshop workshop)
    {
        this.vehicleViewModel = viewModelRegistry.getVehicleViewModel();
        this.clientViewModel = viewModelRegistry.getClientViewModel();
        this.vehicleModule = workshop.getVehicleModule();
        this.clientModule = workshop.getClientModule();

        vehicleViewModel.OnVehicleRequest.addListener(this::processVehicleRequest);
    }

    public void dispose()
    {
        vehicleViewModel.OnVehicleRequest.removeListener(this::processVehicleRequest);
    }

    private void processVehicleRequest()
    {
        switch (vehicleViewModel.getCurrentVehicleRequest())
        {
            case REQUEST_CLIENT_VEHICLES:
            {
                processClientVehiclesRequest();
                break;
            }
            case REQUEST_VEHICLE_REGISTRATION:
            {
                processVehicleRegistrationRequest();
                break;
            }
            case REQUEST_SELECTED_VEHICLE_DETAILS:
            {
                processVehicleDetailsRequest();
                break;
            }
        }
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

            vehicleViewModel.setSelectedClientVehicles(vehicleNames);
            vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_SUCCESS);
        }
    }

    private void processVehicleRegistrationRequest()
    {
        Client selectedClient = getSelectedClient();

        if(selectedClient != null)
        {
            VehicleDTO registerRequestedVehicleDTO = vehicleViewModel.getSelectedVehicle();

            Vehicle vehicle = convertDTOToVehicle(selectedClient, registerRequestedVehicleDTO);
            UUID vehicleID = vehicleModule.registerEntity(vehicle);
            registerRequestedVehicleDTO.setID(vehicleID);

            vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_SUCCESS);
        }
    }

    private void processVehicleDetailsRequest()
    {
        Client selectedClient = getSelectedClient();

        if(selectedClient != null)
        {
            int selectedVehicleIndex = vehicleViewModel.getSelectedVehicleIndex();
            List<UUID> ownedVehiclesIDs = selectedClient.getOwnedVehiclesIDs();

            if(selectedVehicleIndex >= 0 && selectedVehicleIndex < ownedVehiclesIDs.size())
            {
                UUID selectedVehicleID = ownedVehiclesIDs.get(selectedVehicleIndex);
                Vehicle vehicle = vehicleModule.getEntityWithID(selectedVehicleID);

                VehicleDTO selectedVehicle = convertVehicleToDTO(vehicle);

                vehicleViewModel.setSelectedVehicle(selectedVehicle);
                vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_SUCCESS);
            }
            else
            {
                vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_FAILED);
            }
        }
    }

    private Vehicle convertDTOToVehicle(Client selectedClient, VehicleDTO vehicleDTO)
    {
        Vehicle vehicle = new Vehicle
        (
            selectedClient.getID(),
            vehicleDTO.getModel(),
            vehicleDTO.getColor(),
            vehicleDTO.getVinNumber(),
            vehicleDTO.getLicensePlate(),
            vehicleDTO.getYear()
        );

        return vehicle;
    }

    private VehicleDTO convertVehicleToDTO(Vehicle vehicle)
    {
        VehicleDTO selectedVehicle = new VehicleDTO
        (
            vehicle.getID(),
            vehicle.getModel(),
            vehicle.getColor(),
            vehicle.getVinNumber(),
            vehicle.getLicensePlate(),
            vehicle.getYear()
        );

        return selectedVehicle;
    }

    private Client getSelectedClient()
    {
        if(!clientViewModel.hasSelectedClient())
        {
            System.out.println("Nenhum cliente selecionado, por favor selecione um cliente antes de prosseguir.");
            vehicleViewModel.setCurrentVehicleRequest(VehicleRequest.REQUEST_FAILED);
            return null;
        }

        UUID selectedClientID = clientViewModel.getClient().getID();
        Client client = clientModule.getEntityWithID(selectedClientID);
        return client;
    }
}