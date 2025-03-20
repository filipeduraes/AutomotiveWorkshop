// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.views;

import java.util.Scanner;

/**
 *
 * @author Filipe Durães
 */
public class LoginMenu implements IWorkshopMenu 
{
    @Override
    public void ShowMenu()
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Insira o Nome do Usuário: ");
        String userName = scanner.nextLine();
        
        System.out.println("Insira a senha: ");
        String password = scanner.nextLine();
        
        System.out.println(String.format("\nUser: %s, Password: %s", userName, password));
    }
}
