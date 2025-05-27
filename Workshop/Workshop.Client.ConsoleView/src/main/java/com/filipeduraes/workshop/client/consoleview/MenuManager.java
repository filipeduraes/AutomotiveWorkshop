// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview;
import com.filipeduraes.workshop.client.viewmodel.ClientViewModel;
import com.filipeduraes.workshop.client.viewmodel.UserInfoViewModel;
import com.filipeduraes.workshop.client.viewmodel.VehicleViewModel;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Gerencia a navegação entre menus da aplicação, controlando uma pilha de menus e coordenando a transição entre eles.
 *
 * @author Filipe Durães
 */
public class MenuManager
{
    private final Deque<IWorkshopMenu> menuStack = new ArrayDeque<>();
    private final UserInfoViewModel userInfoViewModel;
    private final ClientViewModel clientViewModel;
    private final VehicleViewModel vehicleViewModel;

    /**
     * Cria um novo gerenciador de menus com os modelos de visualização necessários
     * e o menu inicial.
     *
     * @param initialMenu menu inicial a ser exibido
     * @param userInfoViewModel modelo de visualização com informações do usuário
     * @param clientViewModel modelo de visualização com informações do cliente
     * @param vehicleViewModel modelo de visualização com informações do veículo
     */
    public MenuManager(IWorkshopMenu initialMenu, UserInfoViewModel userInfoViewModel, ClientViewModel clientViewModel, VehicleViewModel vehicleViewModel)
    {
        this.userInfoViewModel = userInfoViewModel;
        this.clientViewModel = clientViewModel;
        this.vehicleViewModel = vehicleViewModel;

        menuStack.push(initialMenu);
    }

    /**
     * Obtém o modelo de visualização que contém as informações do usuário.
     *
     * @return modelo de visualização do usuário
     */
    public UserInfoViewModel getUserInfoViewModel()
    {
        return userInfoViewModel;
    }

    /**
     * Obtém o modelo de visualização que contém as informações do cliente.
     *
     * @return modelo de visualização do cliente
     */
    public ClientViewModel getClientViewModel()
    {
        return clientViewModel;
    }

    /**
     * Obtém o modelo de visualização que contém as informações do veículo.
     *
     * @return modelo de visualização do veículo
     */
    public VehicleViewModel getVehicleViewModel()
    {
        return vehicleViewModel;
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
        String formatText = menuStack.size() == 1 ? "X Sair" : "X Voltar";
        return formatText;
    }
}