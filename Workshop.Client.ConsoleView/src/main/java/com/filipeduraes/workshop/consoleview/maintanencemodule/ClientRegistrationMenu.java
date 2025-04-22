// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.consoleview.maintanencemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.viewmodel.ClientRequest;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

/**
 *
 * @author Filipe Durães
 */
public class ClientRegistrationMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName() 
    {
        return "Cadastrar Novo Cliente";
    }

    @Override
    public boolean showMenu(MenuManager menuManager) 
    {
        final String userName = ConsoleInput.readLine("Insira o nome completo do cliente: ");
        final String userPhoneNumber = ConsoleInput.readLine("Insira o telefone do cliente: ");
        final String userEmail = ConsoleInput.readLine("Insira o email do cliente: ");
        final String userAddress = ConsoleInput.readLine("Insira o endereço do cliente: ");
        final String userCPF = ConsoleInput.readLine("Insira o CPF do cliente: ");
        
        final ClientViewModel clientViewModel = menuManager.getClientViewModel();
        clientViewModel.setName(userName);
        clientViewModel.setPhoneNumber(userPhoneNumber);
        clientViewModel.setEmail(userEmail);
        clientViewModel.setAddress(userAddress);
        clientViewModel.setCPF(userCPF);
        
        clientViewModel.setCurrentRequest(ClientRequest.REGISTER_CLIENT);
        return true;
    }
}