package com.filipeduraes.workshop.client.consoleview.maintenancemodule;

import com.filipeduraes.workshop.client.consoleview.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.client.dtos.ServiceDTO;
import com.filipeduraes.workshop.client.dtos.VehicleDTO;
import com.filipeduraes.workshop.client.viewmodel.*;

public class SelectOpenedAppointmentMenu implements IWorkshopMenu
{
    @Override
    public String getMenuDisplayName()
    {
        return "Selecionar agendamento aberto";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        ViewModelRegistry viewModelRegistry = menuManager.getViewModelRegistry();
        MaintenanceViewModel maintenanceViewModel = viewModelRegistry.getMaintenanceViewModel();
        maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_OPENED_APPOINTMENTS);

        if(maintenanceViewModel.getMaintenanceRequest() == MaintenanceRequest.REQUEST_FAILED)
        {
            System.out.println("Nao foi possivel recuperar os agendamentos abertos. Tente novamente.");
            return MenuResult.pop();
        }

        String[] openedAppointmentsDescriptions = maintenanceViewModel.getOpenedAppointmentsDescriptions();

        System.out.println("Servicos abertos:");

        for(int i = 0; i < openedAppointmentsDescriptions.length; i++)
        {
            String entry = String.format(" > [%d] %s", i, openedAppointmentsDescriptions[i]);
            System.out.println(entry);
        }

        int selectedAppointment = ConsoleInput.readOptionFromList("Selecione um servico aberto", openedAppointmentsDescriptions, true);

        if(selectedAppointment >= openedAppointmentsDescriptions.length)
        {
            System.out.println("Operacao cancelada, nenhum agendamento selecionado.");
        }
        else
        {
            maintenanceViewModel.setSelectedMaintenanceIndex(selectedAppointment);
            maintenanceViewModel.setMaintenanceRequest(MaintenanceRequest.REQUEST_DETAILED_SERVICE_INFO);

            if(maintenanceViewModel.getMaintenanceRequest() == MaintenanceRequest.REQUEST_SUCCESS)
            {
                ServiceDTO selectedService = maintenanceViewModel.getSelectedService();
                System.out.println("Servico selecionado:");
                System.out.printf(" - Descricao Curta: %s", selectedService.getShortDescription());
                System.out.printf(" - Descricao Detalhada: %s", selectedService.getDetailedDescription());
                System.out.printf(" - Cliente: %s", selectedService.getClientName());
                System.out.printf(" - Veiculo: %s", selectedService.getVehicleDescription());
            }
            else
            {
                System.out.println("Nao foi possivel selecionar o servico. Tente novamente.");
            }
        }

        return MenuResult.pop();
    }
}
