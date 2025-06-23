// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.consoleview.administrator;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;

/**
 * Menu inicial exibido no primeiro acesso ao sistema.
 * Responsável por iniciar o processo de registro do administrador.
 *
 * @author Filipe Durães
 */
public class FirstAccessMenu implements IWorkshopMenu
{
    /**
     * Retorna o nome de exibição deste menu.
     *
     * @return nome do menu para exibição
     */
    @Override
    public String getMenuDisplayName()
    {
        return "Primeiro Acesso";
    }

    /**
     * Exibe a tela de boas-vindas do primeiro acesso e direciona para o registro do administrador.
     *
     * @param menuManager gerenciador de menus do sistema
     * @return resultado da operação do menu
     */
    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        String initialMessage =
        """
        Bem vindo ao sistema de gerenciamento de oficina mecanica.
        Notamos que esse e o seu primeiro acesso, precisamos de algumas informacoes.
        Vamos criar o seu login de Administrador!
        """;

        System.out.println(initialMessage);
        System.out.println("Pressione enter para continuar...");

        ConsoleInput.readLine();

        return MenuResult.replace(new RegisterAdministrator());
    }
}
