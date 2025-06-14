package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.ServiceOrderDTO;
import com.filipeduraes.workshop.client.viewmodel.ViewModelRegistry;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceRequest;
import com.filipeduraes.workshop.client.viewmodel.maintenance.MaintenanceViewModel;

public class ServiceDetailsMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Detalhes";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        MaintenanceViewModel maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();

        maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_DETAILED_SERVICE_INFO);

        if(maintenanceViewModel.getMaintenanceRequest() == MaintenanceRequest.REQUEST_SUCCESS)
        {
            ServiceOrderDTO selectedService = maintenanceViewModel.getSelectedService();
            System.out.println("Servico selecionado:");
            System.out.printf(" - ID: %s%n", selectedService.getId());
            System.out.printf(" - Estado: %s%n", selectedService.getServiceState());
            System.out.printf(" - Descricao Curta: %s%n", selectedService.getShortDescription());
            System.out.printf(" - Descricao Detalhada: %s%n", selectedService.getDetailedDescription());
            System.out.printf(" - Cliente: %s%n", selectedService.getClientName());
            System.out.printf(" - Veiculo: %s%n", selectedService.getVehicleDescription());

            /*
            Se: estado == Agendamento
                Iniciar inspecao
             */
        }
        else
        {
            System.out.println("Nao foi possivel selecionar o servico. Tente novamente.");
        }

        return MenuResult.pop();
    }
}
