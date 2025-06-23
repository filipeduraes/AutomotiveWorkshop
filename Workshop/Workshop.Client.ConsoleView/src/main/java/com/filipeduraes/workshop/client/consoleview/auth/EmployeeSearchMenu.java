package com.filipeduraes.workshop.client.consoleview.auth;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

import java.util.List;

public class EmployeeSearchMenu implements IWorkshopMenu
{
    private final FieldType[] searchableFieldTypes =
    {
        FieldType.NAME,
        FieldType.EMAIL,
        FieldType.ROLE
    };

    @Override
    public String getMenuDisplayName()
    {
        return "Pesquisar Colaborador";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        EmployeeViewModel employeeViewModel = menuManager.getViewModelRegistry().getEmployeeViewModel();
        employeeViewModel.resetSelectedDTO();

        int selectedField = ConsoleInput.readOptionFromList("Qual campo deseja usar para pesquisar?", searchableFieldTypes, true);

        if(selectedField >= searchableFieldTypes.length)
        {
            System.out.println("Pesquisa cancelada. Voltando...");
            return MenuResult.pop();
        }

        requestSelectedFieldSearchData(selectedField, employeeViewModel);

        employeeViewModel.setSearchFieldType(searchableFieldTypes[selectedField]);
        employeeViewModel.OnSearchRequest.broadcast();
        List<String> foundEmployees = employeeViewModel.getFoundEntitiesDescriptions();

        if(!employeeViewModel.getRequestWasSuccessful() || foundEmployees.isEmpty())
        {
            boolean tryAgain = ConsoleInput.readConfirmation("Nao foi possivel encontrar nenhum colaborador.\nDeseja tentar novamente?");
            employeeViewModel.resetSelectedDTO();
            return tryAgain ? MenuResult.none() : MenuResult.pop();
        }

        String[] foundEmployeesDescriptions = foundEmployees.toArray(String[]::new);
        int selectedEmployeeIndex = ConsoleInput.readOptionFromList("Qual colaborador deseja selecionar?", foundEmployeesDescriptions, true);

        if(selectedEmployeeIndex >= foundEmployeesDescriptions.length)
        {
            System.out.println("Nenhum colaborador selecionado. Voltando...");
            employeeViewModel.resetSelectedDTO();
        }
        else
        {
            System.out.println("Colaborador selecionado com sucesso!");
            employeeViewModel.setSelectedIndex(selectedEmployeeIndex);
        }

        return MenuResult.pop();
    }

    private void requestSelectedFieldSearchData(int selectedField, EmployeeViewModel employeeViewModel)
    {
        EmployeeDTO searchedEmployee = new EmployeeDTO();

        switch (searchableFieldTypes[selectedField])
        {
            case NAME ->
            {
                String name = ConsoleInput.readLine("Insira o nome que deseja buscar");
                searchedEmployee.setName(name);
            }
            case EMAIL ->
            {
                String email = ConsoleInput.readLine("Insira o email que deseja buscar");
                searchedEmployee.setEmail(email);
            }
            case ROLE ->
            {
                int selectedRoleIndex = ConsoleInput.readOptionFromList("Qual cargo deseja filtrar?", EmployeeRoleDTO.values());
                EmployeeRoleDTO selectedRole = EmployeeRoleDTO.values()[selectedRoleIndex];
                searchedEmployee.setRole(selectedRole);
            }
        }

        employeeViewModel.setSelectedDTO(searchedEmployee);
    }
}