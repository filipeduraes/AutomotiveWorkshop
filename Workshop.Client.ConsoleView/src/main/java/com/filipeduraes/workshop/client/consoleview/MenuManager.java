// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import java.util.Stack;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            showMenuTitle();
            
            boolean shouldPop = currentMenu.showMenu(this);

            if(shouldPop)
            {
                menuStack.pop();
            }
        }
    }
    
    public IWorkshopMenu showSubmenuOptions(String headerText, IWorkshopMenu[] options)
    {
        return showSubmenuOptions(headerText, options, true);
    }
    
    public IWorkshopMenu showSubmenuOptions(String headerText, IWorkshopMenu[] options, boolean showExitOption)
    {
        final Stream<String> menuNamesStream = Arrays.stream(options).map(IWorkshopMenu::getMenuDisplayName);
        final Stream<String> finalStream = Stream.concat(menuNamesStream, Stream.of(getLastOption()));
        
        String[] optionList = finalStream.toArray(String[]::new);
        int selectedOptionIndex = ConsoleInput.readOptionFromList(headerText, optionList);

        if(selectedOptionIndex == options.length)
        {
            menuStack.pop();
            return null;
        }
        
        return options[selectedOptionIndex];
    }

    private void showMenuTitle()
    {
        String path = menuStack.stream()
                               .map(IWorkshopMenu::getMenuDisplayName)
                               .collect(Collectors.joining(" > "));

        System.out.println("\n" + path);
        System.out.println("-".repeat(path.length()));
    }

    private String getLastOption() 
    {
        String formatText = menuStack.size() == 1 ? "Sair" : "Voltar";
        return formatText;
    }
}
