// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.clientmodule;

import com.filipeduraes.workshop.client.consoleview.input.CPFInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.PhoneInputValidator;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;

/**
 * Menu responsável pelo cadastro de novos clientes no sistema.
 * Coleta informações básicas do cliente como nome, telefone, email, endereço e CPF.
 *
 * @author Filipe Durães
 */
public class ClientRegistrationMenu implements IWorkshopMenu
{
    /**
     * Obtém o nome de exibição do menu.
     *
     * @return o nome do menu que será exibido para o usuário
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Cadastrar Novo Cliente";
    }

    /**
     * Exibe o menu de cadastro de cliente e processa as entradas do usuário.
     *
     * @param menuManager o gerenciador de menus que controla a navegação
     * @return o resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        final String userName = ConsoleInput.readLine("Insira o nome completo do cliente: ");
        final String userPhoneNumber = ConsoleInput.readValidatedLine("Insira o telefone do cliente: ", new PhoneInputValidator());
        final String userEmail = ConsoleInput.readValidatedLine("Insira o email do cliente: ", new EmailInputValidator());
        final String userAddress = ConsoleInput.readLine("Insira o endereço do cliente: ");
        final String userCPF = ConsoleInput.readValidatedLine("Insira o CPF do cliente: ", new CPFInputValidator());

        final ClientViewModel clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();
        ClientDTO clientDTO = new ClientDTO(userName, userPhoneNumber, userEmail, userAddress, userCPF);
        clientViewModel.setSelectedDTO(clientDTO);

        clientViewModel.OnClientRegisterRequest.broadcast();
        return MenuResult.pop();
    }
}