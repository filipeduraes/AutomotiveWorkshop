// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe base abstrata para ViewModels de entidades.
 * Fornece funcionalidades comuns para gerenciamento de entidades, incluindo busca,
 * carregamento e exclusão de dados.
 *
 * @param <TEntityDTO> Tipo do DTO da entidade gerenciada
 * @author Filipe Durães
 */
public abstract class EntityViewModel<TEntityDTO>
{
    /**
     * Evento disparado quando uma busca é solicitada.
     */
    public final Observer OnSearchRequest = new Observer();

    /**
     * Evento disparado quando o carregamento de dados é solicitado.
     */
    public final Observer OnLoadDataRequest = new Observer();

    /**
     * Evento disparado quando uma exclusão é solicitada.
     */
    public final Observer OnDeleteRequest = new Observer();

    private List<String> foundEntitiesDescriptions = new ArrayList<>();
    private FieldType fieldType;
    private TEntityDTO selectedDTO;
    private int selectedIndex = -1;

    /**
     * Obtém a lista de descrições das entidades encontradas.
     *
     * @return lista de descrições das entidades
     */
    public List<String> getFoundEntitiesDescriptions()
    {
        return foundEntitiesDescriptions;
    }

    /**
     * Define a lista de descrições das entidades encontradas.
     *
     * @param foundEntitiesDescriptions nova lista de descrições
     */
    public void setFoundEntitiesDescriptions(List<String> foundEntitiesDescriptions)
    {
        this.foundEntitiesDescriptions = foundEntitiesDescriptions;
    }

    /**
     * Obtém o tipo de campo usado para busca.
     *
     * @return tipo do campo de busca
     */
    public FieldType getSearchByOption()
    {
        return fieldType;
    }

    /**
     * Define o tipo de campo usado para busca.
     *
     * @param fieldType novo tipo de campo
     */
    public void setSearchFieldType(FieldType fieldType)
    {
        this.fieldType = fieldType;
    }

    /**
     * Obtém o DTO da entidade selecionada.
     *
     * @return DTO selecionado
     */
    public TEntityDTO getSelectedDTO()
    {
        return selectedDTO;
    }

    /**
     * Define o DTO da entidade selecionada.
     *
     * @param selectedDTO novo DTO selecionado
     */
    public void setSelectedDTO(TEntityDTO selectedDTO)
    {
        this.selectedDTO = selectedDTO;
    }

    /**
     * Obtém o índice da entidade selecionada.
     *
     * @return índice selecionado
     */
    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    /**
     * Define o índice da entidade selecionada.
     *
     * @param selectedIndex novo índice selecionado
     */
    public void setSelectedIndex(int selectedIndex)
    {
        this.selectedIndex = selectedIndex;
    }

    /**
     * Verifica se existe um DTO carregado.
     *
     * @return true se existe um DTO carregado, false caso contrário
     */
    public boolean hasLoadedDTO()
    {
        return selectedDTO != null;
    }

    /**
     * Verifica se o índice selecionado é válido.
     *
     * @return true se o índice é válido, false caso contrário
     */
    public boolean hasValidSelectedIndex()
    {
        return selectedIndex >= 0 && selectedIndex < foundEntitiesDescriptions.size();
    }

    /**
     * Verifica se existem entidades encontradas.
     *
     * @return true se existem entidades, false caso contrário
     */
    public boolean hasAnyFoundEntities()
    {
        return !foundEntitiesDescriptions.isEmpty();
    }

    /**
     * Reinicia o estado de seleção, limpando o DTO e índice selecionados.
     */
    public void resetSelectedDTO()
    {
        selectedIndex = -1;
        selectedDTO = null;
    }
}