package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.List;

public class VehicleViewModel
{
    public Observer OnVehicleRequest = new Observer();

    private VehicleRequest currentVehicleRequest;
    private VehicleDTO selectedVehicle;

    private int selectedVehicleIndex = -1;
    private ArrayList<String> selectedClientVehicles = new ArrayList<>();

    public VehicleRequest getCurrentVehicleRequest()
    {
        return currentVehicleRequest;
    }

    public void setCurrentVehicleRequest(VehicleRequest newVehicleRequest)
    {
        if (newVehicleRequest != currentVehicleRequest)
        {
            currentVehicleRequest = newVehicleRequest;
            OnVehicleRequest.broadcast();
        }
    }

    public VehicleDTO getSelectedVehicle()
    {
        return selectedVehicle;
    }

    public void setSelectedVehicle(VehicleDTO selectedVehicle)
    {
        this.selectedVehicle = selectedVehicle;
    }

    public boolean hasSelectedVehicle()
    {
        return selectedVehicleIndex >= 0 && selectedVehicleIndex < selectedClientVehicles.size();
    }

    public void cleanCurrentSelectedVehicle()
    {
        selectedVehicle = null;
        selectedVehicleIndex = -1;
    }

    public void setSelectedVehicleIndex(int newSelectedVehicleIndex)
    {
        selectedVehicleIndex = newSelectedVehicleIndex;
    }

    public int getSelectedVehicleIndex()
    {
        return selectedVehicleIndex;
    }


    public void setSelectedClientVehicles(ArrayList<String> newSelectedClientVehicles)
    {
        selectedClientVehicles = newSelectedClientVehicles;
    }

    public String getSelectedClientVehicleFromIndex(int index)
    {
        return selectedClientVehicles.get(index);
    }

    public List<String> getSelectedClientVehicles()
    {
        return selectedClientVehicles;
    }

    public boolean clientHasVehicles()
    {
        return !selectedClientVehicles.isEmpty();
    }
}