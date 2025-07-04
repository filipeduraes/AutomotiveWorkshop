// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.employee;

import com.filipeduraes.workshop.core.CrudRepository;
import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import com.filipeduraes.workshop.utils.Observer;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final CrudRepository<LocalEmployee> employeeRepository;

    /**
     * Cria uma instância do módulo de autenticação.
     */
    public AuthModule()
    {
        employeeRepository = new CrudRepository<>(WorkshopPaths.REGISTERED_EMPLOYEES_PATH, LocalEmployee.class);
    }

    /**
     * Obtém o repositório de colaboradores.
     * @return Repositório de colaboradores.
     */
    public CrudRepository<LocalEmployee> getEmployeeRepository()
    {
        return employeeRepository;
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
     * @return {@code true} se um usuário estiver logado, {@code false} caso contrário
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
        List<LocalEmployee> usersWithSameEmail = employeeRepository.findEntitiesWithPredicate(localEmployee -> localEmployee.getEmail().equals(user.getEmail()));
        boolean canRegisterUser = usersWithSameEmail.isEmpty();

        if(canRegisterUser)
        {
            employeeRepository.registerEntity(user);
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
        LocalEmployee foundUser = employeeRepository.findFirstEntityWithPredicate(localEmployee -> localEmployee.getEmail().equals(email));

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

    public boolean isFirstAccess()
    {
        return !employeeRepository.hasLoadedEntities();
    }

    /**
     * Encontra um usuário registrado com base em seu ID.
     *
     * @param userID identificador único do usuário
     * @return usuário encontrado
     */
    public Employee getUserFromID(UUID userID)
    {
        return employeeRepository.getEntityWithID(userID);
    }

    public void registerClockIn(ClockInType clockInType)
    {
        loggedUser.setLastClockIn(clockInType);
        employeeRepository.saveCurrentEntities();

        List<ClockIn> clockInRegistry = loadCurrentMonthClockIns();
        clockInRegistry.add(new ClockIn(clockInType, loggedUser.getID()));

        String path = WorkshopPaths.getClockInCurrentMonthPath();
        Persistence.saveFile(clockInRegistry, path);
    }

    public List<ClockIn> loadCurrentMonthClockIns()
    {
        ParameterizedType type = Persistence.createParameterizedType(ArrayList.class, ClockIn.class);
        String path = WorkshopPaths.getClockInCurrentMonthPath();

        return Persistence.loadFile(path, type, new ArrayList<>());
    }

    public List<ClockIn> loadMonthClockIns(int month, int year)
    {
        ParameterizedType type = Persistence.createParameterizedType(ArrayList.class, ClockIn.class);

        LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 0, 0);
        String path = WorkshopPaths.getClockInMonthPath(localDateTime);

        return Persistence.loadFile(path, type, new ArrayList<>());
    }
}