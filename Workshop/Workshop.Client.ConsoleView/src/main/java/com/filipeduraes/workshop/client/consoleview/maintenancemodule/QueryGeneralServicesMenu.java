package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceViewModel;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceQueryType;

public class QueryGeneralServicesMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Servicos Gerais";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        ServiceViewModel serviceViewModel = viewModelRegistry.getServiceViewModel();
        serviceViewModel.setQueryType(ServiceQueryType.GENERAL);
        System.out.println("Servicos gerais selecionados");

        return MenuResult.replace(new QueryServicesMenu());
    }
}
