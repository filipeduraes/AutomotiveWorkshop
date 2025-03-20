// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.views;

/**
 *
 * @author Filipe Durães
 */
public class EnterUserMenu implements IWorkshopMenu
{
    private IWorkshopMenu[] menus = 
    {
        new LoginMenu(),
        new SignInMenu()
    };
    
    @Override
    public String GetMenuDisplayName() 
    {
        return "Tela de Login";
    }
    
    @Override
    public void ShowMenu() 
    {
        System.out.println("Qual a opção de login?");
        
        for(int i = 0; i < menus.length; i++)
        {
            System.out.println(String.format("[%d] %s", i, menus[i].GetMenuDisplayName()));
        }
        
        String indexInput = ConsoleInput.ReadLine();
        int index = Integer.parseInt(indexInput);
        
        menus[index].ShowMenu();
    }
}