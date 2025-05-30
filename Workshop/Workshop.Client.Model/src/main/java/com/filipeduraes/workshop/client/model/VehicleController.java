package com.filipeduraes.workshop.client.model;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleRequest;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;
import com.filipeduraes.workshop.core.client.Client;
import com.filipeduraes.workshop.core.client.ClientModule;
import com.filipeduraes.workshop.core.vehicle.Vehicle;
import com.filipeduraes.workshop.core.vehicle.VehicleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VehicleController
{
    private final VehicleViewModel vehicleViewModel;
    private final ClientViewModel clientViewModel;
    private final VehicleModule vehicleModule;
    private final ClientModule clientModule;

    public VehicleController(VehicleViewModel vehicleViewModel, ClientViewModel clientViewModel, VehicleModule vehicleModule, ClientModule clientModule)
    {
        this.vehicleViewModel = vehicleViewModel;
        this.clientViewModel = clientViewModel;
        this.vehicleModule = vehicleModule;
        this.clientModule = clientModule;

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
                Vehicle vehicle = vehicleModule.findVehicleByID(ownedVehiclesID);
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
            VehicleDTO registerRequestedVehicle = vehicleViewModel.getSelectedVehicle();

            Vehicle vehicle = convertDTOToVehicle(selectedClient, registerRequestedVehicle);
            UUID vehicleID = vehicleModule.registerVehicle(vehicle);
            registerRequestedVehicle.setID(vehicleID);
            clientModule.saveCurrentClients();

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
                Vehicle vehicle = vehicleModule.findVehicleByID(selectedVehicleID);

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

    private Vehicle convertDTOToVehicle(Client selectedClient, VehicleDTO registerRequestedVehicle)
    {
        Vehicle vehicle = new Vehicle
        (
            selectedClient.getID(),
            registerRequestedVehicle.getModel(),
            registerRequestedVehicle.getColor(),
            registerRequestedVehicle.getVinNumber(),
            registerRequestedVehicle.getLicensePlate(),
            registerRequestedVehicle.getYear()
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
        Client client = clientModule.findClientByID(selectedClientID);
        return client;
    }
}