// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel responsável por gerenciar os dados e estado da interface de cliente,
 * incluindo busca, seleção e informações do cliente.
 *
 * @author Filipe Durães
 */
public class ClientViewModel
{
    /**
     * Observer notificado quando uma requisição de cliente é alterada
     */
    public final Observer OnClientRequest = new Observer();

    private ClientRequest currentRequest;

    private SearchByOption searchByOption;
    private List<String> foundClientDescriptions = new ArrayList<>();
    private String searchPattern;
    private int selectedFoundClientIndex = -1;

    private ClientDTO client;

    public SearchByOption getSearchByOption()
    {
        return searchByOption;
    }

    public void setSearchByOption(SearchByOption searchByOption)
    {
        this.searchByOption = searchByOption;
    }

    /**
     * Obtém a lista de nomes de clientes encontrados na busca.
     *
     * @return lista de nomes dos clientes encontrados
     */
    public List<String> getFoundClientDescriptions()
    {
        return foundClientDescriptions;
    }

    /**
     * Define a lista de nomes de clientes encontrados na busca.
     *
     * @param foundClientDescriptions nova lista de nomes dos clientes
     */
    public void setFoundClientDescriptions(List<String> foundClientDescriptions)
    {
        this.foundClientDescriptions = foundClientDescriptions;
    }


    /**
     * Obtém o padrão de busca atual.
     *
     * @return padrão de busca atual
     */
    public String getSearchPattern()
    {
        return searchPattern;
    }

    /**
     * Define o padrão de busca.
     *
     * @param searchPattern novo padrão de busca
     */
    public void setSearchPattern(String searchPattern)
    {
        this.searchPattern = searchPattern;
    }


    /**
     * Obtém o índice do cliente selecionado na lista de resultados.
     *
     * @return índice do cliente selecionado
     */
    public int getSelectedFoundClientIndex()
    {
        return selectedFoundClientIndex;
    }

    /**
     * Define o índice do cliente selecionado na lista de resultados.
     *
     * @param selectedFoundClientIndex novo índice do cliente selecionado
     */
    public void setSelectedFoundClientIndex(int selectedFoundClientIndex)
    {
        this.selectedFoundClientIndex = selectedFoundClientIndex;
    }

    /**
     * Reseta a seleção de cliente para nenhum selecionado.
     */
    public void resetSelectedClient()
    {
        selectedFoundClientIndex = -1;
        client = null;
    }

    /**
     * Verifica se há um cliente selecionado.
     *
     * @return true se houver um cliente selecionado, false caso contrário
     */
    public boolean hasSelectedClient()
    {
        return selectedFoundClientIndex >= 0 && getClient() != null;
    }

    public ClientDTO getClient()
    {
        return client;
    }

    public void setClient(ClientDTO client)
    {
        this.client = client;
    }

    /**
     * Obtém a requisição atual do cliente.
     *
     * @return requisição atual do cliente
     */
    public ClientRequest getCurrentRequest()
    {
        return currentRequest;
    }

    /**
     * Define uma nova requisição do cliente e notifica os observadores se houver mudança.
     *
     * @param newCurrentRequest nova requisição do cliente
     */
    public void setCurrentRequest(ClientRequest newCurrentRequest)
    {
        if (currentRequest != newCurrentRequest)
        {
            currentRequest = newCurrentRequest;
            OnClientRequest.broadcast();
        }
    }
}