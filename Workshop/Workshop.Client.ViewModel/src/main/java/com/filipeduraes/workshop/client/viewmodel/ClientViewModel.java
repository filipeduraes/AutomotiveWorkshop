// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.utils.Observer;

/**
 * ViewModel responsável por gerenciar os dados e estado da interface de cliente,
 * incluindo busca, seleção e informações do cliente.
 *
 * @author Filipe Durães
 */
public class ClientViewModel extends EntityViewModel<ClientDTO>
{
    /**
     * Evento chamado quando o registro do cliente selecionado é requisitado.
     */
    public final Observer OnClientRegisterRequest = new Observer();

    /**
     * Evento chamado quando a edição do cliente selecionado é requisitado.
     */
    public final Observer OnClientEditRequest = new Observer();

    private String searchPattern;

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
}