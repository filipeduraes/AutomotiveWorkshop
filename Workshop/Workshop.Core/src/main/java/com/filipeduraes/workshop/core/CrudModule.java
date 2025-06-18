package com.filipeduraes.workshop.core;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.utils.ConsumerObserver;
import com.filipeduraes.workshop.utils.FuzzyTokenMatcher;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Classe genérica responsável por gerenciar operações de CRUD (Create, Read, Update, Delete)
 * de uma entidade do sistema.
 *
 * <p>Permite registrar, buscar, atualizar, excluir e persistir entidades na memória
 * e no armazenamento de dados.</p>
 *
 * @param <TEntity> O tipo da entidade que este módulo irá gerenciar.
 */
public class CrudModule<TEntity extends WorkshopEntity>
{
    /**
     * Evento acionado sempre que uma nova entidade é registrada.
     */
    public final ConsumerObserver<TEntity> OnEntityRegistered = new ConsumerObserver<>();

    private final Map<UUID, TEntity> loadedEntities;
    private final String path;

    /**
     * Cria uma nova instância do módulo de CRUD para uma entidade específica.
     *
     * @param path Caminho do arquivo onde os dados serão persistidos.
     * @param entityType Classe da entidade que será gerenciada.
     */
    public CrudModule(String path, Class<TEntity> entityType)
    {
        this.path = path;
        ParameterizedType parameterizedType = Persistence.createParameterizedType(HashMap.class, UUID.class, entityType);
        loadedEntities = Persistence.loadFile(path, parameterizedType, new HashMap<>());
    }

    /**
     * Verifica se existe uma entidade com o ID especificado.
     *
     * @param id Identificador único da entidade.
     * @return {@code true} se existir, {@code false} caso contrário.
     */
    public boolean hasEntityWithID(UUID id)
    {
        return loadedEntities.containsKey(id);
    }

    /**
     * Registra uma nova entidade no sistema.
     *
     * <p>Um UUID único é gerado e atribuído à entidade. A entidade é salva na memória
     * e persistida em disco.</p>
     *
     * @param newEntity A entidade a ser registrada.
     * @return O UUID gerado e atribuído à entidade.
     */
    public UUID registerEntity(TEntity newEntity)
    {
        UUID uniqueID = Persistence.generateUniqueID(loadedEntities);
        newEntity.assignID(uniqueID);

        loadedEntities.put(uniqueID, newEntity);
        saveCurrentEntities();

        OnEntityRegistered.broadcast(newEntity);

        return uniqueID;
    }

    /**
     * Remove uma entidade com o ID especificado.
     *
     * @param id O identificador único da entidade a ser removida.
     * @return A entidade removida, ou {@code null} se não encontrada.
     */
    public TEntity deleteEntityWithID(UUID id)
    {
        TEntity deletedEntity = loadedEntities.remove(id);
        saveCurrentEntities();
        return deletedEntity;
    }

    /**
     * Atualiza uma entidade existente.
     *
     * <p>Se a entidade com o mesmo ID existir, ela será substituída pela entidade fornecida.</p>
     *
     * @param updatedEntity A entidade atualizada.
     */
    public void updateEntity(TEntity updatedEntity)
    {
        loadedEntities.put(updatedEntity.getID(), updatedEntity);
        saveCurrentEntities();
    }

    /**
     * Retorna a entidade com o ID especificado.
     *
     * @param id Identificador único da entidade.
     * @return A entidade correspondente, ou {@code null} se não encontrada.
     */
    public TEntity getEntityWithID(UUID id)
    {
        return loadedEntities.get(id);
    }

    /**
     * Retorna uma lista com todas as entidades carregadas.
     *
     * @return Lista de todas as entidades.
     */
    public List<TEntity> getAllEntities()
    {
        return new ArrayList<>(loadedEntities.values());
    }

    /**
     * Busca entidades que satisfaçam um predicado.
     *
     * @param predicate O predicado que define o filtro.
     * @return Lista de entidades que correspondem ao filtro.
     */
    public List<TEntity> findEntitiesWithPredicate(Predicate<TEntity> predicate)
    {
        return loadedEntities.values()
                             .stream()
                             .filter(predicate)
                             .collect(Collectors.toList());
    }

    /**
     * Realiza uma busca textual aproximada nas entidades, com base em um padrão e uma função
     * que extrai a string de comparação da entidade.
     *
     * @param pattern Padrão textual a ser buscado.
     * @param stringExtractor Função que extrai a string de cada entidade para a comparação.
     * @return Lista de entidades que correspondem ao padrão.
     */
    public List<TEntity> searchEntitiesWithPattern(String pattern, Function<TEntity, String> stringExtractor)
    {
        List<TEntity> foundItems = FuzzyTokenMatcher.findSimilarItems(loadedEntities.values(), pattern, 0.5, stringExtractor);
        return foundItems;
    }

    /**
     * Busca a primeira entidade que satisfaça um predicado.
     *
     * @param predicate O predicado de filtro.
     * @return A primeira entidade encontrada ou {@code null} se nenhuma corresponder.
     */
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

    /**
     * Salva o estado atual das entidades no arquivo persistente.
     */
    public void saveCurrentEntities()
    {
        Persistence.saveFile(loadedEntities, path);
    }

    /**
     * Retorna a quantidade de entidades carregadas.
     *
     * @return O número de entidades.
     */
    public int getEntitiesCount()
    {
        return loadedEntities.size();
    }

    /**
     * Verifica se há pelo menos uma entidade carregada.
     *
     * @return {@code true} se houver entidades, {@code false} caso contrário.
     */
    public boolean hasLoadedEntities()
    {
        return getEntitiesCount() > 0;
    }
}