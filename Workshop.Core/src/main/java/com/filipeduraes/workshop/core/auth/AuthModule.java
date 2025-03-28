// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core.auth;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 *
 * @author Filipe Durães
 */
public class AuthModule 
{
    private Employee loggedUser = null;
    private Map<UUID, Employee> registeredUsers;
    private boolean useObfuscation;
    
    private static final String UsersPath = "./Data/Users.workshop";
    private static final byte Key = 12;
    
    public AuthModule(boolean useObfuscation)
    {
        this.useObfuscation = useObfuscation;
    }
    
    public Employee getLoggedUser()
    {
        return loggedUser;
    }
    
    public boolean isLoggedIn()
    {
        return loggedUser != null;
    }
    
    public void registerUser(Employee user)
    {
        try
        {
            Map<UUID, Employee> currentRegisteredUsers = loadRegisteredUsers();
            
            UUID uniqueID = generateUniqueID(currentRegisteredUsers);
            user = new Employee(uniqueID, user);
            
            currentRegisteredUsers.put(uniqueID, user);
            
            FileWriter fileWriter = new FileWriter(UsersPath);
            
            Gson gson = new Gson();
            String usersJson = gson.toJson(currentRegisteredUsers);
            String obfuscatedUsers = useObfuscation ? obfuscate(usersJson) : usersJson;
            
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) 
            {
                bufferedWriter.write(obfuscatedUsers);
            }
        }
        catch(IOException exception)
        {
            exception.printStackTrace(System.out);
        }
    }
    
    public boolean tryLogIn(String email, int passwordHash)
    {
        registeredUsers = loadRegisteredUsers();
        Employee userToValidate = null;
        
        for(Employee employee : registeredUsers.values())
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
        if(!registeredUsers.containsKey(userID))
        {
            return null;
        }
        
        return registeredUsers.get(userID);
    }
    
    private Map<UUID, Employee> loadRegisteredUsers()
    {
        try 
        {
            ensureUsersDirectoriesAndFileExists();
            
            FileReader fileReader = new FileReader(UsersPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            HashMap<UUID, Employee> result = new HashMap<>();
            String obfuscatedUsers = Files.readString(Path.of(UsersPath), StandardCharsets.UTF_8);
            
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Employee>>(){}.getType();
            
            String usersJson = useObfuscation ? deobfuscate(obfuscatedUsers) : obfuscatedUsers;
            result = gson.fromJson(usersJson, type);
            return result == null ? new HashMap<>() : result;
        } 
        catch (IOException e) 
        {
            e.printStackTrace(System.out);
            return new HashMap<>();
        }        
    }
    
    private void ensureUsersDirectoriesAndFileExists() throws IOException
    {
        Path path = Path.of(UsersPath);
        Path directoryPath = path.getParent();
        
        if (!Files.exists(directoryPath)) 
        {
            Files.createDirectories(directoryPath);
        }
        
        if (!Files.exists(path)) 
        {
            Files.createFile(path);
        }
    }

    private UUID generateUniqueID(Map<UUID, Employee> registeredUsers) 
    {
        UUID uniqueID = UUID.randomUUID();
        
        while(registeredUsers.containsKey(uniqueID))
        {
            uniqueID = UUID.randomUUID();
        }
        
        return uniqueID;
    }
    
    private String obfuscate(String text)
    {        
        byte[] buffer = text.getBytes(StandardCharsets.UTF_8);

        for (int i = 0; i < buffer.length; i++)
        {
            buffer[i] ^= Key;
        }

        return Base64.getEncoder().encodeToString(buffer);
    }

    private String deobfuscate(String base64)
    {
        byte[] buffer = Base64.getDecoder().decode(base64);

        for (int i = 0; i < buffer.length; i++)
        {
            buffer[i] ^= Key;
        }

        return new String(buffer, StandardCharsets.UTF_8);
    }
}
