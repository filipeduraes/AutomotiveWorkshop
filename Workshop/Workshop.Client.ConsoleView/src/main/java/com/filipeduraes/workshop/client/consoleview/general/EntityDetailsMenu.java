package com.filipeduraes.workshop.client.consoleview.general;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.viewmodel.EntityViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class EntityDetailsMenu<TViewModel extends EntityViewModel<TEntityDTO>, TEntityDTO> implements IWorkshopMenu
{
    private TViewModel viewModel;

    @Override
    public String getMenuDisplayName()
    {
        return "Exibir detalhes";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        viewModel = findViewModel(menuManager);

        if(!viewModel.hasValidSelectedIndex())
        {
            System.out.println("Selecao invalida, fechando menu de detalhes...");
            return MenuResult.pop();
        }

        viewModel.OnLoadDataRequest.broadcast();

        System.out.printf("%s%n", viewModel.getSelectedDTO());
        MenuOption menuOption = menuManager.showMenuOptions("O que deseja fazer?", buildOptions(), true);

        return menuOption.execute(menuManager);
    }

    protected List<MenuOption> buildOptions()
    {
        ArrayList<MenuOption> optionsList = new ArrayList<>();
        optionsList.add(new MenuOption("Excluir", this::deleteEntity));

        return optionsList;
    }

    protected TViewModel getViewModel()
    {
        return viewModel;
    }

    protected abstract TViewModel findViewModel(MenuManager menuManager);

    private MenuResult deleteEntity(MenuManager menuManager)
    {
        boolean canDelete = ConsoleInput.readConfirmation("Tem certeza que deseja excluir?\nEssa acao nao pode ser desfeita!");

        if(canDelete)
        {
            getViewModel().OnDeleteRequest.broadcast();
            return MenuResult.pop();
        }

        return MenuResult.none();
    }
}
