// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.core;

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
import java.lang.reflect.Type;

/**
 *
 * @author Filipe Durães
 */
public class AuthModule 
{
    private Employee loggedUser = null;
    private static final String UsersPath = "./Data/Users.workshop";
    
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
            Map<String, Employee> registeredUsers = LoadRegisteredUsers();
            registeredUsers.put(user.getEmail(), user);
            
            FileWriter fileWriter = new FileWriter(UsersPath);
            
            Gson gson = new Gson();
            String usersJson = gson.toJson(registeredUsers);
            
            try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) 
            {
                bufferedWriter.write(usersJson);
            }
        }
        catch(IOException exception)
        {
            exception.printStackTrace(System.out);
        }
    }
    
    public boolean tryLogIn(String email, int passwordHash)
    {
        Map<String, Employee> registeredUsers = LoadRegisteredUsers();
            
        if(!registeredUsers.containsKey(email))
        {
            return false;
        }

        Employee userToValidate = registeredUsers.get(email);
        boolean passwordIsValid = userToValidate.isPasswordValid(passwordHash);
        
        if(passwordIsValid)
        {
            loggedUser = userToValidate;
        }
        
        return passwordIsValid;
    }
    
    private Map<String, Employee> LoadRegisteredUsers()
    {
        try 
        {
            ensureUsersDirectoriesAndFileExists();
            
            FileReader fileReader = new FileReader(UsersPath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            HashMap<String, Employee> result = new HashMap<>();
            StringBuilder builder = new StringBuilder();
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) 
            {
                builder.append(currentLine).append(System.lineSeparator());
            }
            
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Employee>>(){}.getType();
            result = gson.fromJson(builder.toString(), type);
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
}
