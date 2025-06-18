package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceViewModel;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceQueryType;

public class QueryUserServicesMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Meus Servicos";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();
        serviceViewModel.setQueryType(ServiceQueryType.USER);

        System.out.println("Servicos do usuario selecionados");

        return MenuResult.replace(new QueryServicesMenu());
    }
}
