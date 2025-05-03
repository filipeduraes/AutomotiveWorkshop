// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.utils.Observer;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class ClientViewModel
{
    public Observer OnClientRequest = new Observer();
    
    private ClientRequest currentRequest;
    
    private ArrayList<String> foundClientNames = new ArrayList<>();
    private String searchPattern;
    private int selectedFoundClientIndex = -1;
    
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String cpf;
    
    public ArrayList<String> getFoundClientNames() 
    {
        return foundClientNames;
    }

    public void setFoundClientNames(ArrayList<String> foundClientNames) 
    {
        this.foundClientNames = foundClientNames;
    }

    
    public String getSearchPattern() 
    {
        return searchPattern;
    }

    public void setSearchPattern(String searchPattern) 
    {
        this.searchPattern = searchPattern;
    }

    
    public int getSelectedFoundClientIndex() 
    {
        return selectedFoundClientIndex;
    }

    public void setSelectedFoundClientIndex(int selectedFoundClientIndex) 
    {
        this.selectedFoundClientIndex = selectedFoundClientIndex;
    }
    
    public void resetSelectedFoundClientIndex() 
    {
        selectedFoundClientIndex = -1;
    }
    
    public boolean hasSelectedClient()
    {
        return selectedFoundClientIndex >= 0;
    }

    
    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    
    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    
    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    
    public String getAddress() 
    {
        return address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }
    
    
    public String getCPF() 
    {
        return cpf;
    }

    public void setCPF(String cpf) 
    {
        this.cpf = cpf;
    }

    
    
    public ClientRequest getCurrentRequest()
    {
        return currentRequest;
    }
    
    public void setCurrentRequest(ClientRequest newCurrentRequest)
    {
        if(currentRequest != newCurrentRequest)
        {
            currentRequest = newCurrentRequest;
            OnClientRequest.broadcast();
        }
    }
}