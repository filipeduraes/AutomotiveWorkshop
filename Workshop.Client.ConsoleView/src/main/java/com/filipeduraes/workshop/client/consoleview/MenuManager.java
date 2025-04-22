// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import java.util.Stack;

/**
 *
 * @author Filipe Durães
 */
public class MenuManager 
{
    private final Stack<IWorkshopMenu> menuStack = new Stack<>();
    private final UserInfoViewModel userInfoViewModel;
    private final ClientViewModel clientViewModel;
    
    public MenuManager(UserInfoViewModel userInfoViewModel, ClientViewModel clientViewModel)
    {
        this.userInfoViewModel = userInfoViewModel;
        this.clientViewModel = clientViewModel;
    }
    
    public UserInfoViewModel getUserInfoViewModel()
    {
        return userInfoViewModel;
    }
    
    public ClientViewModel getClientViewModel()
    {
        return clientViewModel;
    }
        
    public void pushMenu(IWorkshopMenu menu)
    {
        menuStack.push(menu);
    }
    
    public void replaceCurrentMenu(IWorkshopMenu newMenu)
    {
        if(!menuStack.isEmpty())
        {
            menuStack.pop();
        }
        
        menuStack.push(newMenu);
    }
    
    public void run()
    {
        while(!menuStack.isEmpty())
        {
            IWorkshopMenu currentMenu = menuStack.peek();
            showMenuTitle(currentMenu);
            
            boolean shouldPop = currentMenu.showMenu(this);

            if(shouldPop)
            {
                menuStack.pop();
            }
        }
    }
    
    public IWorkshopMenu showSubmenuOptions(String headerText, IWorkshopMenu[] options)
    {
        System.out.println(headerText);
        
        for(int i = 0; i < options.length; i++)
        {
            String optionMessage = String.format("[%d] %s", i, options[i].getMenuDisplayName());
            System.out.println(optionMessage);
        }
        
        showLastOption(options.length);
        
        String indexInput = ConsoleInput.readLine();
        int index = Integer.parseInt(indexInput);
        
        if(index == options.length)
        {
            menuStack.pop();
            return null;
        }
        
        if(index < 0 || index >= options.length)
        {
            System.out.println("Insira apenas opções válidas");
            return null;
        }
        
        return options[index];
    }
    
    private void showMenuTitle(IWorkshopMenu currentMenu) 
    {
        String menuDisplayName = currentMenu.getMenuDisplayName();
        String titleSeparator = "";
        
        for(int i = 0; i < menuDisplayName.length(); i++)
        {
            titleSeparator += '=';
        }
        
        System.out.println("");
        System.out.println(titleSeparator);
        System.out.println(menuDisplayName);
        System.out.println(titleSeparator);
    }

    private void showLastOption(int index) 
    {
        String formatText = menuStack.size() == 1 ? "[%d] Sair" : "[%d] Voltar";
        String lastOptionText = String.format(formatText, index);
        System.out.println(lastOptionText);
    }
}
