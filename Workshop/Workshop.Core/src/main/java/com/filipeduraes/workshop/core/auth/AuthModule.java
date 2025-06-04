// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import com.filipeduraes.workshop.core.CrudModule;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.utils.Observer;

import java.util.List;
import java.util.UUID;

/**
 * Controla autenticação, login e registro de usuários.
 * Gerencia sessões de usuário e valida credenciais.
 *
 * @author Filipe Durães
 */
public class AuthModule
{
    public final Observer OnUserLogged = new Observer();
    private Employee loggedUser = null;

    private final CrudModule<LocalEmployee> employeeCrudModule;

    /**
     * Cria uma instância do módulo de autenticação.
     */
    public AuthModule()
    {
        employeeCrudModule = new CrudModule<>(WorkshopPaths.REGISTERED_EMPLOYEES_PATH, LocalEmployee.class);
    }

    /**
     * Obtém o usuário atual logado.
     * Retorna null se nenhum usuário estiver logado.
     *
     * @return usuário logado
     */
    public Employee getLoggedUser()
    {
        return loggedUser;
    }

    /**
     * Verifica se há algum usuário logado.
     *
     * @return true se um usuário estiver logado, false caso contrário
     */
    public boolean isLoggedIn()
    {
        return loggedUser != null;
    }

    /**
     * Registra um usuário com base nos dados fornecidos.
     *
     * @param user usuário para registrar
     */
    public boolean registerUser(LocalEmployee user)
    {
        List<LocalEmployee> usersWithSameEmail = employeeCrudModule.findEntitiesWithPredicate(localEmployee -> localEmployee.getEmail().equals(user.getEmail()));
        boolean canRegisterUser = usersWithSameEmail.isEmpty();

        if(canRegisterUser)
        {
            employeeCrudModule.registerEntity(user);
        }

        return canRegisterUser;
    }

    /**
     * Tenta fazer login com o email e senha hash fornecidos.
     *
     * @param email email usado para o login
     * @param passwordHash senha usada para o login convertida em hash
     * @return login foi bem sucedido
     */
    public boolean tryLogIn(String email, int passwordHash)
    {
        LocalEmployee foundUser = employeeCrudModule.findFirstEntityWithPredicate(localEmployee -> localEmployee.getEmail().equals(email));

        if (foundUser == null)
        {
            return false;
        }

        boolean passwordIsValid = foundUser.isPasswordValid(passwordHash);

        if (passwordIsValid)
        {
            loggedUser = foundUser;
            OnUserLogged.broadcast();
        }

        return passwordIsValid;
    }

    /**
     * Encontra um usuário registrado com base em seu ID.
     *
     * @param userID identificador único do usuário
     * @return usuário encontrado
     */
    public Employee getUserFromID(UUID userID)
    {
        return employeeCrudModule.getEntityWithID(userID);
    }
}