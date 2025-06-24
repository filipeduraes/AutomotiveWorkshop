// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.IInputValidator;

import java.util.function.Consumer;
import java.util.function.Function;

public class SearchInputStrategy
{
    private final SearchInputStrategyType strategyType;
    private final IWorkshopMenu menuToRedirect;
    private final Function<MenuManager, String> resolveInput;
    private final Function<MenuManager, Boolean> validateInput;
    private final Consumer<MenuManager> cleanup;

    public SearchInputStrategy(String inputMessage)
    {
        strategyType = SearchInputStrategyType.DIRECT_INPUT;
        menuToRedirect = null;
        resolveInput = (menuManager) -> ConsoleInput.readLine(inputMessage);
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    public SearchInputStrategy(String inputMessage, IInputValidator inputValidator)
    {
        strategyType = SearchInputStrategyType.DIRECT_INPUT;
        menuToRedirect = null;
        resolveInput = (menuManager) -> ConsoleInput.readValidatedLine(inputMessage, inputValidator);
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    public <T> SearchInputStrategy(String inputMessage, T[] options)
    {
        strategyType = SearchInputStrategyType.DIRECT_INPUT;
        menuToRedirect = null;
        resolveInput = (menuManager) -> Integer.toString(ConsoleInput.readOptionFromList(inputMessage, options));
        validateInput = (menuManager) -> true;
        cleanup = (menuManager) -> {};
    }

    public SearchInputStrategy(IWorkshopMenu menuToRedirect, Function<MenuManager, String> resolveInput, Function<MenuManager, Boolean> validateInput)
    {
        strategyType = SearchInputStrategyType.REDIRECT_MENU;
        this.menuToRedirect = menuToRedirect;
        this.resolveInput = resolveInput;
        this.validateInput = validateInput;
        this.cleanup = (viewModel) -> {};
    }

    public SearchInputStrategy(IWorkshopMenu menuToRedirect, Function<MenuManager, String> resolveInput, Function<MenuManager, Boolean> validateInput, Consumer<MenuManager> cleanup)
    {
        strategyType = SearchInputStrategyType.REDIRECT_MENU;
        this.menuToRedirect = menuToRedirect;
        this.resolveInput = resolveInput;
        this.validateInput = validateInput;
        this.cleanup = cleanup;
    }

    public SearchInputStrategyType getStrategyType()
    {
        return strategyType;
    }

    public IWorkshopMenu getMenuToRedirect()
    {
        return menuToRedirect;
    }

    public Function<MenuManager, String> getResolveInput()
    {
        return resolveInput;
    }

    public Function<MenuManager, Boolean> getValidateInput()
    {
        return validateInput;
    }

    public Consumer<MenuManager> getCleanup()
    {
        return cleanup;
    }
}
