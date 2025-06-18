package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityViewModel<TEntityDTO>
{
    public final Observer OnSearchRequest = new Observer();
    public final Observer OnLoadDataRequest = new Observer();
    public final Observer OnDeleteRequest = new Observer();

    private List<String> foundEntitiesDescriptions = new ArrayList<>();
    private SearchByOption searchByOption;
    private TEntityDTO selectedDTO;
    private int selectedIndex = -1;

    public List<String> getFoundEntitiesDescriptions()
    {
        return foundEntitiesDescriptions;
    }

    public void setFoundEntitiesDescriptions(List<String> foundEntitiesDescriptions)
    {
        this.foundEntitiesDescriptions = foundEntitiesDescriptions;
    }

    public SearchByOption getSearchByOption()
    {
        return searchByOption;
    }

    public void setSearchByOption(SearchByOption searchByOption)
    {
        this.searchByOption = searchByOption;
    }

    public TEntityDTO getSelectedDTO()
    {
        return selectedDTO;
    }

    public void setSelectedDTO(TEntityDTO selectedDTO)
    {
        this.selectedDTO = selectedDTO;
    }

    public int getSelectedIndex()
    {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex)
    {
        this.selectedIndex = selectedIndex;
    }

    public boolean hasLoadedDTO()
    {
        return selectedDTO != null;
    }

    public boolean hasValidSelectedIndex()
    {
        return selectedIndex >= 0 && selectedIndex < foundEntitiesDescriptions.size();
    }

    public boolean hasAnyFoundEntities()
    {
        return !foundEntitiesDescriptions.isEmpty();
    }

    public void resetSelectedDTO()
    {
        selectedIndex = -1;
        selectedDTO = null;
    }
}