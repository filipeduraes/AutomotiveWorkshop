package com.filipeduraes.workshop.client.consoleview.employee;

import com.filipeduraes.workshop.client.consoleview.IWorkshopMenu;
import com.filipeduraes.workshop.client.consoleview.MenuManager;
import com.filipeduraes.workshop.client.consoleview.MenuResult;
import com.filipeduraes.workshop.client.consoleview.input.ConsoleInput;
import com.filipeduraes.workshop.client.consoleview.input.EmailInputValidator;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.client.viewmodel.EmployeeViewModel;
import com.filipeduraes.workshop.client.viewmodel.FieldType;

public class EditEmployeeMenu implements IWorkshopMenu
{
    private final FieldType[] editableFields =
    {
        FieldType.NAME,
        FieldType.EMAIL,
        FieldType.ROLE,
        FieldType.PASSWORD
    };

    @Override
    public String getMenuDisplayName()
    {
        return "Editar colaborador";
    }

    @Override
    public MenuResult showMenu(MenuManager menuManager)
    {
        int selectedFieldIndex = ConsoleInput.readOptionFromList("Qual campo deseja editar?", editableFields, true);

        if(selectedFieldIndex >= editableFields.length)
        {
            System.out.println("Operação cancelada. Voltando...");
            return MenuResult.pop();
        }

        EmployeeViewModel employeeViewModel = menuManager.getViewModelRegistry().getEmployeeViewModel();
        FieldType selectedField = editableFields[selectedFieldIndex];

        employeeViewModel.setFieldType(selectedField);
        EmployeeDTO editedEmployee = createEditedEmployeeFromField(selectedField);

        employeeViewModel.setSelectedDTO(editedEmployee);
        employeeViewModel.OnEditRequest.broadcast();

        if (employeeViewModel.getRequestWasSuccessful())
        {
            System.out.printf("%nColaborador editado com sucesso. Novos dados:%n%s%n", employeeViewModel.getSelectedDTO());
        }
        else
        {
            System.out.println("Nao foi possivel editar o colaborador. Tente novamente!");
        }

        return MenuResult.pop();
    }

    private static EmployeeDTO createEditedEmployeeFromField(FieldType selectedField)
    {
        EmployeeDTO editedEmployee = new EmployeeDTO();

        switch (selectedField)
        {
            case NAME ->
            {
                String newName = ConsoleInput.readLine("Insira o novo nome do colaborador");
                editedEmployee.setName(newName);
            }
            case EMAIL ->
            {
                String newEmail = ConsoleInput.readValidatedLine("Insira o novo email do colaborador", new EmailInputValidator());
                editedEmployee.setEmail(newEmail);
            }
            case ROLE ->
            {
                int newRole = ConsoleInput.readOptionFromList("Insira o novo cargo do colaborador", EmployeeRoleDTO.values());
                editedEmployee.setRole(EmployeeRoleDTO.values()[newRole]);
            }
            case PASSWORD ->
            {
                String newPassword = ConsoleInput.readLine("Insira a nova senha do colaborador");
                editedEmployee.setPasswordHash(newPassword.hashCode());
            }
        }
        return editedEmployee;
    }
}