package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.utils.ConsumerObserver;
import com.filipeduraes.workshop.utils.FuzzyTokenMatcher;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CrudModule<TEntity extends WorkshopEntity>
{
    public final ConsumerObserver<TEntity> OnEntityRegistered = new ConsumerObserver<>();

    private final Map<UUID, TEntity> loadedEntities;
    private final String path;

    public CrudModule(String path, Class<TEntity> entityType)
    {
        this.path = path;
        ParameterizedType parameterizedType = Persistence.createParameterizedType(HashMap.class, UUID.class, entityType);
        loadedEntities = Persistence.loadFile(path, parameterizedType, new HashMap<>());
    }

    public boolean hasEntityWithID(UUID id)
    {
        return loadedEntities.containsKey(id);
    }

    public UUID registerEntity(TEntity newEntity)
    {
        UUID uniqueID = Persistence.generateUniqueID(loadedEntities);
        newEntity.assignID(uniqueID);

        loadedEntities.put(uniqueID, newEntity);
        saveCurrentEntities();

        OnEntityRegistered.broadcast(newEntity);

        return uniqueID;
    }

    public TEntity deleteEntityWithID(UUID id)
    {
        return loadedEntities.remove(id);
    }

    public void updateEntity(TEntity updatedEntity)
    {
        loadedEntities.put(updatedEntity.getID(), updatedEntity);
        saveCurrentEntities();
    }

    public TEntity getEntityWithID(UUID id)
    {
        return loadedEntities.get(id);
    }

    public List<TEntity> findEntitiesWithPredicate(Predicate<TEntity> predicate)
    {
        return loadedEntities.values()
                             .stream()
                             .filter(predicate)
                             .collect(Collectors.toList());
    }

    public List<TEntity> searchEntitiesWithPattern(String pattern, Function<TEntity, String> stringExtractor)
    {
        List<TEntity> foundItems = FuzzyTokenMatcher.findSimilarItems(loadedEntities.values(), pattern, 0.5, stringExtractor);
        return foundItems;
    }

    public TEntity findFirstEntityWithPredicate(Predicate<TEntity> predicate)
    {
        for (TEntity entity : loadedEntities.values())
        {
            if (predicate.test(entity))
            {
                return entity;
            }
        }

        return null;
    }

    public void saveCurrentEntities()
    {
        Persistence.saveFile(loadedEntities, path);
    }
}