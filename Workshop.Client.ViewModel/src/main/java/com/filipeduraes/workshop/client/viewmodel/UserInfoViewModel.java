// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.viewmodel;
import com.filipeduraes.workshop.client.utils.Observer;
import java.util.ArrayList;

/**
 *
 * @author Filipe Durães
 */
public class UserInfoViewModel 
{
    public Observer OnLoginStateChanged = new Observer();
    
    private ArrayList<String> possibleRoles = new ArrayList<>();
    private LoginState loginState = LoginState.WAITING;
    private String name;
    private String email;
    private int passwordHash;
    private int selectedRole;
    private int accessLevel;
    
    public LoginState getLoginState()
    {
        return loginState;
    }
    
    public void setLoginState(LoginState newLoginState)
    {
        if(newLoginState != loginState)
        {
            loginState = newLoginState;
            OnLoginStateChanged.broadcast();
        }
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String newName)
    {
        name = newName;
    }
    
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String newEmail)
    {
        email = newEmail;
    }
    
    
    public int getPasswordHash()
    {
        return passwordHash;
    }
    
    public void setPasswordHash(int newPasswordHash)
    {
        passwordHash = newPasswordHash;
    }
    
    
    public int getAccessLevel()
    {
        return accessLevel;
    }
    
    public void setAccessLevel(int newAccessLevel)
    {
        accessLevel = newAccessLevel;
    }
    
    
    public ArrayList<String> getPossibleRoles()
    {
        return possibleRoles;
    }
    
    public void setPossibleRoles(ArrayList<String> newPossibleRoles)
    {
        possibleRoles = newPossibleRoles;
    }
    
    
    public String getSelectedRoleName()
    {
        if(selectedRole >= 0 && selectedRole < possibleRoles.size())
        {
            return possibleRoles.get(selectedRole);
        }
        
        return "INVALID_ROLE";
    }
    
    public int getSelectedRole()
    {
        return selectedRole;
    }
    
    public void setSelectedRole(int newSelectedRole)
    {
        selectedRole = newSelectedRole;
    }
}