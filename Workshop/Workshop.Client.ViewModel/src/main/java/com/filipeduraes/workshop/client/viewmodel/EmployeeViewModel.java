// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.viewmodel;

import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.utils.Observer;

import java.util.UUID;

/**
 * ViewModel responsável por gerenciar e armazenar informações do usuário logado,
 * incluindo estado de login, dados pessoais e papéis disponíveis no sistema.
 * Esta classe mantém o estado das operações de autenticação e registro de usuários,
 * bem como informações sobre o usuário atualmente logado.
 *
 * @author Filipe Durães
 */
public class EmployeeViewModel extends EntityViewModel<EmployeeDTO>
{
    /**
     * Evento disparado quando uma solicitação de login é feita.
     */
    public final Observer OnLoginRequested = new Observer();

    /**
     * Evento disparado quando uma solicitação de registro de usuário é feita.
     */
    public final Observer OnRegisterUserRequested = new Observer();

    /**
     * Evento disparado quando uma solicitação de edição de usuário é feita.
     */
    public final Observer OnEditUserRequested = new Observer();

    private EmployeeDTO loggedUser;

    /**
     * Obtém o usuário atualmente logado no sistema.
     *
     * @return o DTO do usuário logado ou null se não houver usuário logado
     */
    public EmployeeDTO getLoggedUser()
    {
        return loggedUser;
    }

    /**
     * Define o usuário logado no sistema.
     *
     * @param loggedUser o DTO do usuário a ser definido como logado
     */
    public void setLoggedUser(EmployeeDTO loggedUser)
    {
        this.loggedUser = loggedUser;
    }

    /**
     * Verifica se o usuário selecionado é o mesmo que está logado no sistema.
     *
     * @return true se o usuário selecionado for o mesmo que está logado, false caso contrário
     */
    public boolean isSelectedUserSameAsLoggedUser()
    {
        UUID selectedUserID = getSelectedDTO().getID();
        UUID loggedUserID = getLoggedUser().getID();
        return hasLoggedUser() && hasLoadedDTO() && loggedUserID.equals(selectedUserID);
    }

    /**
     * Verifica se existe um usuário logado no sistema.
     *
     * @return true se existe um usuário logado, false caso contrário
     */
    private boolean hasLoggedUser()
    {
        return getLoggedUser() != null;
    }
}