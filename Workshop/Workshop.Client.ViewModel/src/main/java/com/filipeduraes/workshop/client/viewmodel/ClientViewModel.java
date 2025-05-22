// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.utils.Observer;
import java.util.ArrayList;
import java.util.UUID;

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
    public Observer OnClientRequest = new Observer();

    private ClientRequest currentRequest;

    private ArrayList<String> foundClientNames = new ArrayList<>();
    private String searchPattern;
    private int selectedFoundClientIndex = -1;

    private UUID id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String cpf;

    /**
     * Obtém a lista de nomes de clientes encontrados na busca.
     *
     * @return lista de nomes dos clientes encontrados
     */
    public ArrayList<String> getFoundClientNames()
    {
        return foundClientNames;
    }

    /**
     * Define a lista de nomes de clientes encontrados na busca.
     *
     * @param foundClientNames nova lista de nomes dos clientes
     */
    public void setFoundClientNames(ArrayList<String> foundClientNames)
    {
        this.foundClientNames = foundClientNames;
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
    public void resetSelectedFoundClientIndex()
    {
        selectedFoundClientIndex = -1;
    }

    /**
     * Verifica se há um cliente selecionado.
     *
     * @return true se houver um cliente selecionado, false caso contrário
     */
    public boolean hasSelectedClient()
    {
        return selectedFoundClientIndex >= 0;
    }


    /**
     * Obtém o ID do cliente.
     *
     * @return ID do cliente
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Define o id do cliente.
     *
     * @param newID novo ID do cliente
     */
    public void setID(UUID newID)
    {
        id = newID;
    }


    /**
     * Obtém o nome do cliente.
     *
     * @return nome do cliente
     */
    public String getName()
    {
        return name;
    }

    /**
     * Define o nome do cliente.
     *
     * @param name novo nome do cliente
     */
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * Obtém o número de telefone do cliente.
     *
     * @return número de telefone do cliente
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Define o número de telefone do cliente.
     *
     * @param phoneNumber novo número de telefone do cliente
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }


    /**
     * Obtém o email do cliente.
     *
     * @return email do cliente
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Define o email do cliente.
     *
     * @param email novo email do cliente
     */
    public void setEmail(String email)
    {
        this.email = email;
    }


    /**
     * Obtém o endereço do cliente.
     *
     * @return endereço do cliente
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Define o endereço do cliente.
     *
     * @param address novo endereço do cliente
     */
    public void setAddress(String address)
    {
        this.address = address;
    }


    /**
     * Obtém o CPF do cliente.
     *
     * @return CPF do cliente
     */
    public String getCPF()
    {
        return cpf;
    }

    /**
     * Define o CPF do cliente.
     *
     * @param cpf novo CPF do cliente
     */
    public void setCPF(String cpf)
    {
        this.cpf = cpf;
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