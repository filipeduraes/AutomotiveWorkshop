// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controla autenticação, login e registro de usuários.
 * Gerencia sessões de usuário e valida credenciais.
 *
 * @author Filipe Durães
 */
public class AuthModule
{
    private Employee loggedUser = null;
    private Map<UUID, LocalEmployee> publicUsers;

    /**
     * Cria uma instância do módulo de autenticação.
     * Pode usar ofuscação para tornar dados persistentes mais difíceis de ler por humanos.
     *
     * @param useObfuscation
     */
    public AuthModule(boolean useObfuscation)
    {
        Persistence.setUseObfuscation(useObfuscation);
        publicUsers = loadRegisteredLocalUsers();
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
     * @param user
     */
    public void registerUser(LocalEmployee user)
    {
        Map<UUID, LocalEmployee> currentRegisteredUsers = loadRegisteredLocalUsers();

        UUID uniqueID = Persistence.generateUniqueID(currentRegisteredUsers);
        user.setID(uniqueID);

        currentRegisteredUsers.put(uniqueID, user);

        Persistence.saveFile(currentRegisteredUsers, WorkshopPaths.RegisteredEmployeesPath);
    }

    /**
     * Tenta fazer login com o email e senha hash fornecidos.
     *
     * @param email
     * @param passwordHash
     * @return login foi bem sucedido
     */
    public boolean tryLogIn(String email, int passwordHash)
    {
        publicUsers = loadRegisteredLocalUsers();
        LocalEmployee userToValidate = null;

        for (LocalEmployee employee : publicUsers.values())
        {
            if (employee.getEmail().equals(email))
            {
                userToValidate = employee;
                break;
            }
        }

        if (userToValidate == null)
        {
            return false;
        }

        boolean passwordIsValid = userToValidate.isPasswordValid(passwordHash);

        if (passwordIsValid)
        {
            loggedUser = userToValidate;
        }

        return passwordIsValid;
    }

    /**
     * Encontra um usuário registrado com base em seu ID.
     *
     * @param userID
     * @return usuário encontrado
     */
    public Employee getUserFromID(UUID userID)
    {
        if (!publicUsers.containsKey(userID))
        {
            return null;
        }

        return publicUsers.get(userID);
    }

    private Map<UUID, LocalEmployee> loadRegisteredLocalUsers()
    {
        ParameterizedType type = Persistence.createParameterizedType(HashMap.class, UUID.class, LocalEmployee.class);
        return Persistence.loadFile(WorkshopPaths.RegisteredEmployeesPath, type, new HashMap<>());
    }
}