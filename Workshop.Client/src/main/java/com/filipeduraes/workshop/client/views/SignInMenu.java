// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.views;

/**
 *
 * @author Filipe Durães
 */
public class SignInMenu implements IWorkshopMenu 
{
    @Override
    public String GetMenuDisplayName() 
    {
        return "Cadastrar";
    }
    
    @Override
    public void ShowMenu() 
    {
        System.out.println("Insira o email: ");
        String userName = ConsoleInput.ReadLine();
        
        System.out.println("\nInsira a senha: ");
        String password = ConsoleInput.ReadLine();
        
        System.out.println(String.format("\nUser: %s, Password: %d", userName, password.hashCode()));
    }    
}
