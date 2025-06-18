package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

import java.util.function.Function;

public class MenuOption
{
    private final String optionDisplayName;
    private final Function<MenuManager, MenuResult> optionAction;

    public MenuOption(String optionDisplayName, Function<MenuManager, MenuResult> optionAction)
    {
        this.optionDisplayName = optionDisplayName;
        this.optionAction = optionAction;
    }

    public String getOptionDisplayName()
    {
        return optionDisplayName;
    }

    public Function<MenuManager, MenuResult> getOptionAction()
    {
        return optionAction;
    }

    public MenuResult execute(MenuManager menuManager)
    {
        return getOptionAction().apply(menuManager);
    }
}