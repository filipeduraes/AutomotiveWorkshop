// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;
import com.filipeduraes.workshop.client.consoleview.general.MenuOption;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Gerencia a navegação entre menus da aplicação, controlando uma pilha de menus e coordenando a transição entre eles.
 * Permite exibir menus, processar opções do usuário e controlar o fluxo de navegação.
 *
 * @author Filipe Durães
 */
public class MenuManager
{
    private final Deque<IWorkshopMenu> menuStack = new ArrayDeque<>();
    private final ViewModelRegistry viewModelRegistry;

    /**
     * Cria um novo gerenciador de menus com os modelos de visualização necessários
     * e o menu inicial.
     *
     * @param initialMenu menu inicial a ser exibido
     * @param viewModelRegistry registro de modelos de visualização
     */
    public MenuManager(IWorkshopMenu initialMenu, ViewModelRegistry viewModelRegistry)
    {
        this.viewModelRegistry = viewModelRegistry;

        menuStack.push(initialMenu);
    }

    /**
     * Obtém o registro de modelos de visualização.
     *
     * @return registro de modelos de visualização
     */
    public ViewModelRegistry getViewModelRegistry()
    {
        return viewModelRegistry;
    }

    /**
     * Inicia o loop principal de execução dos menus, gerenciando a navegação
     * entre eles até que o usuário decida sair da aplicação.
     */
    public void run()
    {
        while (!menuStack.isEmpty())
        {
            IWorkshopMenu currentMenu = menuStack.peek();
            showMenuTitle();

            MenuResult menuAction = currentMenu.showMenu(this);

            if(menuAction == null)
            {
                System.out.println("Resultado de menu invalido, fechando menu atual.");
                menuAction = MenuResult.pop();
            }

            switch (menuAction.getAction())
            {
                case PUSH_MENU ->
                {
                    menuStack.push(menuAction.getTargetMenu());
                }
                case REPLACE_MENU ->
                {
                    menuStack.pop();
                    menuStack.push(menuAction.getTargetMenu());
                }
                case POP_MENU -> menuStack.pop();
                case EXIT -> menuStack.clear();
            }
        }
    }

    /**
     * Exibe uma lista de opções de submenu para o usuário escolher,
     * incluindo uma opção de saída.
     *
     * @param headerText texto de cabeçalho a ser exibido
     * @param options array com as opções de submenu disponíveis
     * @return menu selecionado pelo usuário ou null se escolher sair
     */
    public IWorkshopMenu showSubmenuOptions(String headerText, IWorkshopMenu[] options)
    {
        return showSubmenuOptions(headerText, options, true);
    }

    /**
     * Exibe uma lista de opções de submenu para o usuário escolher,
     * com controle sobre a exibição da opção de saída.
     *
     * @param headerText texto de cabeçalho a ser exibido
     * @param options array com as opções de submenu disponíveis
     * @param showExitOption indica se deve mostrar a opção de saída
     * @return menu selecionado pelo usuário ou null se escolher sair
     */
    public IWorkshopMenu showSubmenuOptions(String headerText, IWorkshopMenu[] options, boolean showExitOption)
    {
        final Stream<String> menuNamesStream = Arrays.stream(options).map(IWorkshopMenu::getMenuDisplayName);
        String[] optionList;

        if(showExitOption)
        {
            final Stream<String> finalStream = Stream.concat(menuNamesStream, Stream.of(getLastOption()));
            optionList = finalStream.toArray(String[]::new);
        }
        else
        {
            optionList = menuNamesStream.toArray(String[]::new);
        }

        int selectedOptionIndex = ConsoleInput.readOptionFromList(headerText, optionList);

        if (showExitOption && selectedOptionIndex == options.length)
        {
            return null;
        }

        return options[selectedOptionIndex];
    }

    /**
     * Exibe uma lista de opções de menu para o usuário escolher.
     *
     * @param message mensagem de cabeçalho a ser exibida
     * @param options lista com as opções de menu disponíveis
     * @return opção selecionada pelo usuário
     */
    public MenuOption showMenuOptions(String message, List<MenuOption> options)
    {
        return showMenuOptions(message, options, false);
    }

    /**
     * Exibe uma lista de opções de menu para o usuário escolher,
     * com controle sobre a exibição da opção de saída.
     *
     * @param message mensagem de cabeçalho a ser exibida
     * @param options lista com as opções de menu disponíveis
     * @param showExitOption indica se deve mostrar a opção de saída
     * @return opção selecionada pelo usuário
     */
    public MenuOption showMenuOptions(String message, List<MenuOption> options, boolean showExitOption)
    {
        String[] optionList = options.stream().map(MenuOption::getOptionDisplayName).toArray(String[]::new);
        int selectedOption = ConsoleInput.readOptionFromList(message, optionList, showExitOption);

        if(selectedOption < 0 || selectedOption >= options.size())
        {
            return new MenuOption("Sair", m -> MenuResult.pop());
        }

        return options.get(selectedOption);
    }

    private void showMenuTitle()
    {
        String path = menuStack.stream()
                               .map(IWorkshopMenu::getMenuDisplayName)
                               .collect(Collectors.joining(" > "));

        System.out.printf("%n%s%n", path);
        System.out.println("-".repeat(path.length()));
    }

    private String getLastOption()
    {
        String formatText = menuStack.size() == 1 ? "X Sair" : "X Voltar";
        return formatText;
    }
}