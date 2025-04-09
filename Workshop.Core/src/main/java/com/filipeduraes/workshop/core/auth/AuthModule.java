// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import com.filipeduraes.workshop.core.persistence.Persistence;
import com.filipeduraes.workshop.core.persistence.WorkshopPaths;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Filipe Durães
 */
public class AuthModule 
{
    private Employee loggedUser = null;
    private Map<UUID, LocalEmployee> publicUsers;
    
    public AuthModule(boolean useObfuscation)
    {
        Persistence.setUseObfuscation(useObfuscation);
        publicUsers = loadRegisteredLocalUsers();
    }
    
    public Employee getLoggedUser()
    {
        return loggedUser;
    }
    
    public boolean isLoggedIn()
    {
        return loggedUser != null;
    }
    
    public void registerUser(LocalEmployee user)
    {
        Map<UUID, LocalEmployee> currentRegisteredUsers = loadRegisteredLocalUsers();

        UUID uniqueID = Persistence.generateUniqueID(currentRegisteredUsers);
        user.setID(uniqueID);

        currentRegisteredUsers.put(uniqueID, user);

        Persistence.saveFile(currentRegisteredUsers, WorkshopPaths.RegisteredEmployeesPath);
    }
    
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
