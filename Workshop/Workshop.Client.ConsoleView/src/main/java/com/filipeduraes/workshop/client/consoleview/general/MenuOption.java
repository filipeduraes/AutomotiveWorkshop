// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;

import java.util.function.Function;

/**
 * Representa uma opção de menu no sistema da oficina.
 * Mantém o nome de exibição e a ação associada a ser executada quando selecionada.
 *
 * @author Filipe Durães
 */
public class MenuOption
{
    private final String optionDisplayName;
    private final Function<MenuManager, MenuResult> optionAction;

    /**
     * Cria uma nova opção de menu com nome e ação especificados.
     *
     * @param optionDisplayName Nome de exibição da opção
     * @param optionAction Ação a ser executada quando a opção for selecionada
     */
    public MenuOption(String optionDisplayName, Function<MenuManager, MenuResult> optionAction)
    {
        this.optionDisplayName = optionDisplayName;
        this.optionAction = optionAction;
    }

    /**
     * Obtém o nome de exibição da opção.
     *
     * @return nome de exibição da opção
     */
    public String getOptionDisplayName()
    {
        return optionDisplayName;
    }

    /**
     * Obtém a ação associada à opção.
     *
     * @return ação da opção
     */
    public Function<MenuManager, MenuResult> getOptionAction()
    {
        return optionAction;
    }

    /**
     * Executa a ação associada à opção de menu.
     *
     * @param menuManager Gerenciador de menu atual
     * @return resultado da execução da ação
     */
    public MenuResult execute(MenuManager menuManager)
    {
        return getOptionAction().apply(menuManager);
    }
}