// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.client;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.CPFInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.consoleview.input.PhoneInputValidator;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

/**
 * Menu para edição dos dados de um cliente.
 * Permite ao usuário alterar informações cadastrais de um cliente já registrado,
 * como nome, email, CPF, endereço e telefone.
 *
 * O menu apresenta as opções de campos editáveis e processa a alteração conforme a escolha do usuário.
 *
 * @author Filipe Durães
 */
public class EditClientMenu implements IWorkshopMenu
{
    private final FieldType[] possibleFieldsToEdit =
    {
        FieldType.NAME,
        FieldType.EMAIL,
        FieldType.CPF,
        FieldType.ADDRESS,
        FieldType.PHONE
    };

    /**
     * Obtém o nome de exibição do menu.
     *
     * @return o nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Editar cliente";
    }

    /**
     * Exibe o menu de edição de cliente e processa a interação do usuário.
     *
     * @param menuManager gerenciador de menus da aplicação
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        int selectedFieldIndex = ConsoleInput.readOptionFromList("Qual campo deseja alterar?", possibleFieldsToEdit, true);

        if(selectedFieldIndex >= possibleFieldsToEdit.length)
        {
            System.out.println("Operacao cancelada. Voltando...");
            return MenuResult.pop();
        }

        FieldType selectedField = possibleFieldsToEdit[selectedFieldIndex];
        EntityViewModel<ClientDTO> clientViewModel = menuManager.getViewModelRegistry().getClientViewModel();

        ClientDTO editedClienteDTO = createEditedClientFromField(selectedField);

        clientViewModel.setFieldType(selectedField);
        clientViewModel.setSelectedDTO(editedClienteDTO);
        clientViewModel.OnEditRequest.broadcast();

        if(clientViewModel.getRequestWasSuccessful())
        {
            System.out.println("Dados do cliente alterados com sucesso!");
        }
        else
        {
            System.out.println("Nao foi possivel alterar os dados do cliente. Tente novamente.");
        }

        return MenuResult.pop();
    }

    private static ClientDTO createEditedClientFromField(FieldType selectedField)
    {
        ClientDTO editedClienteDTO = new ClientDTO();

        switch (selectedField)
        {
            case NAME ->
            {
                String newName = ConsoleInput.readLine("Insira o novo nome do cliente");
                editedClienteDTO.setName(newName);
            }
            case EMAIL ->
            {
                String newEmail = ConsoleInput.readValidatedLine("Insira o novo email do cliente", new EmailInputValidator());
                editedClienteDTO.setEmail(newEmail);
            }
            case CPF ->
            {
                String newCPF = ConsoleInput.readValidatedLine("Insira o novo CPF do cliente", new CPFInputValidator());
                editedClienteDTO.setCpf(newCPF);
            }
            case ADDRESS ->
            {
                String newAddress = ConsoleInput.readLine("Insira o novo endereco do cliente");
                editedClienteDTO.setAddress(newAddress);
            }
            case PHONE ->
            {
                String newPhoneNumber = ConsoleInput.readValidatedLine("Insira o novo telefone do cliente", new PhoneInputValidator());
                editedClienteDTO.setPhoneNumber(newPhoneNumber);
            }
        }
        
        return editedClienteDTO;
    }
}
