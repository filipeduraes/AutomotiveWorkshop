// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Controls authentication, login and registration of users.
 * Manages user sessions and validate credentials.
 * @author Filipe Durães
 */
public class AuthModule 
{
    private Employee loggedUser = null;
    private Map<UUID, LocalEmployee> publicUsers;
    
    /**
     * Creates a new instance of the authentication module.
     * Can use obfuscation to make persistent data harder to read by humans.
     * @param useObfuscation
     */
    public AuthModule(boolean useObfuscation)
    {
        Persistence.setUseObfuscation(useObfuscation);
        publicUsers = loadRegisteredLocalUsers();
    }
    
    /**
     * Gets the current logged user.
     * Returns null if no user has logged in.
     * @return logged user
     */
    public Employee getLoggedUser()
    {
        return loggedUser;
    }
    
    /**
     * Checks if there's any user logged in.
     * @return true if a user is logged in, false otherwise
     */
    public boolean isLoggedIn()
    {
        return loggedUser != null;
    }
    
    /**
     * Register an user based on the given data.
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
     * Tries to log in with the given email and hashed password.
     * @param email
     * @param passwordHash
     * @return log in was successful
     */
    public boolean tryLogIn(String email, int passwordHash)
    {
        publicUsers = loadRegisteredLocalUsers();
        LocalEmployee userToValidate = null;
        
        for(LocalEmployee employee : publicUsers.values())
        {
            if(employee.getEmail().equals(email))
            {
                userToValidate = employee;
                break;
            }
        }
        
        if(userToValidate == null)
        {
            return false;
        }

        boolean passwordIsValid = userToValidate.isPasswordValid(passwordHash);
        
        if(passwordIsValid)
        {
            loggedUser = userToValidate;
        }
        
        return passwordIsValid;
    }
    
    /**
     * Finds a registered user based on their ID.
     * @param userID
     * @return found user
     */
    public Employee getUserFromID(UUID userID)
    {
        if(!publicUsers.containsKey(userID))
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