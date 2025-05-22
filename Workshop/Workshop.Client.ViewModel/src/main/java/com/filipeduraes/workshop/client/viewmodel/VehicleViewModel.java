package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.List;

public class VehicleViewModel
{
    public Observer OnVehicleRequest = new Observer();

    private VehicleRequest currentVehicleRequest;
    private String selectedModel;
    private String selectedColor;
    private String selectedVinNumber;
    private String selectedLicensePlate;
    private int selectedYear;

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

    public void setSelectedVehicleData(String model, String color, String vinNumber, String licensePlate, int year)
    {
        selectedModel = model;
        selectedColor = color;
        selectedVinNumber = vinNumber;
        selectedLicensePlate = licensePlate;
        selectedYear = year;
    }

    public String getSelectedModel()
    {
        return selectedModel;
    }

    public String getSelectedColor()
    {
        return selectedColor;
    }

    public String getSelectedVinNumber()
    {
        return selectedVinNumber;
    }

    public String getSelectedLicensePlate()
    {
        return selectedLicensePlate;
    }

    public int getSelectedYear()
    {
        return selectedYear;
    }


    public boolean hasSelectedVehicle()
    {
        return selectedVehicleIndex >= 0 && selectedVehicleIndex < selectedClientVehicles.size();
    }

    public void cleanCurrentSelectedVehicle()
    {
        selectedModel = "";
        selectedColor = "";
        selectedVinNumber = "";
        selectedLicensePlate = "";
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