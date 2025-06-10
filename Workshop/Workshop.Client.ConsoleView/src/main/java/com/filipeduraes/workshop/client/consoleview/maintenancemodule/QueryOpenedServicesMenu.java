package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.viewmodel.*;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;
import com.filipeduraes.workshop.client.viewmodel.maintenance.ServiceQueryType;

public class QueryOpenedServicesMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Servicos Abertos";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        MaintenanceViewModel maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();
        maintenanceViewModel.setQueryType(ServiceQueryType.OPENED);
        System.out.println("Servicos abertos selecionados");

        return MenuResult.replace(new QueryServicesMenu());
    }
}
