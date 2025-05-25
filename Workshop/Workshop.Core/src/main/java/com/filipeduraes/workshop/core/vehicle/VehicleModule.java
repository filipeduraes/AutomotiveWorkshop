package com.filipeduraes.workshop.core.vehicle;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.utils.ConsumerObserver;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VehicleModule
{
    public ConsumerObserver<Vehicle> OnVehicleRegistered = new ConsumerObserver<>();

    private Map<UUID, Vehicle> loadedVehicles;

    public VehicleModule()
    {
        ParameterizedType type = Persistence.createParameterizedType(HashMap.class, UUID.class, Vehicle.class);
        loadedVehicles = Persistence.loadFile(WorkshopPaths.REGISTERED_VEHICLES_PATH, type, new HashMap<>());
    }

    public UUID registerVehicle(Vehicle newVehicle)
    {
        UUID uniqueID = Persistence.generateUniqueID(loadedVehicles);

        loadedVehicles.put(uniqueID, newVehicle);
        Persistence.saveFile(loadedVehicles, WorkshopPaths.REGISTERED_VEHICLES_PATH);
        newVehicle.setID(uniqueID);
        OnVehicleRegistered.broadcast(newVehicle);

        return uniqueID;
    }

    public Vehicle findVehicleByID(UUID vehicleID)
    {
        if (!loadedVehicles.containsKey(vehicleID))
        {
            return null;
        }

        return loadedVehicles.get(vehicleID);
    }
}