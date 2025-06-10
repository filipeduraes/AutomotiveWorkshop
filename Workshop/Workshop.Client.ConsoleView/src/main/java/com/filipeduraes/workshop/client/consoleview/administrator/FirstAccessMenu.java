package com.filipeduraes.workshop.client.consoleview.administrator;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;

public class FirstAccessMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Primeiro Acesso";
    }

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
