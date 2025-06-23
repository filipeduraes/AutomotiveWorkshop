package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.core.auth.Employee;
import com.filipeduraes.workshop.core.auth.EmployeeRole;
import com.filipeduraes.workshop.core.auth.LocalEmployee;

public class EmployeeMapper
{
    public static EmployeeDTO toDTO(Employee employee)
    {
        return new EmployeeDTO
        (
            employee.getName(),
            employee.getEmail(),
            toEmployeeRoleDTO(employee.getRole())
        );
    }

    public static Employee fromDTO(EmployeeDTO employee)
    {
        return new Employee
        (
            employee.getName(),
            employee.getEmail(),
            fromEmployeeRoleDTO(employee.getRole())
        );
    }

    public static LocalEmployee fromLocalDTO(EmployeeDTO employee)
    {
        return new LocalEmployee
        (
            employee.getName(),
            employee.getEmail(),
            fromEmployeeRoleDTO(employee.getRole()),
            employee.getPasswordHash()
        );
    }

    public static EmployeeRoleDTO toEmployeeRoleDTO(EmployeeRole role)
    {
        return switch (role)
        {
            case COSTUMER_SERVICE -> EmployeeRoleDTO.COSTUMER_SERVICE;
            case MECHANIC -> EmployeeRoleDTO.MECHANIC;
            case SPECIALIST_MECHANIC -> EmployeeRoleDTO.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> EmployeeRoleDTO.ADMINISTRATOR;
        };
    }

    public static EmployeeRole fromEmployeeRoleDTO(EmployeeRoleDTO selectedRole)
    {
        return switch (selectedRole)
        {
            case COSTUMER_SERVICE -> EmployeeRole.COSTUMER_SERVICE;
            case MECHANIC -> EmployeeRole.MECHANIC;
            case SPECIALIST_MECHANIC -> EmployeeRole.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> EmployeeRole.ADMINISTRATOR;
        };
    }
}
