package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.core.auth.EmployeeRole;

public final class EmployeeRoleMapper
{
    public static EmployeeRole fromDTO(EmployeeRoleDTO selectedRole)
    {
        return switch (selectedRole)
        {
            case COSTUMER_SERVICE -> EmployeeRole.COSTUMER_SERVICE;
            case MECHANIC -> EmployeeRole.MECHANIC;
            case SPECIALIST_MECHANIC -> EmployeeRole.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> EmployeeRole.ADMINISTRATOR;
        };
    }

    public static EmployeeRoleDTO toDTO(EmployeeRole employeeRole)
    {
        return switch (employeeRole)
        {
            case COSTUMER_SERVICE -> EmployeeRoleDTO.COSTUMER_SERVICE;
            case MECHANIC -> EmployeeRoleDTO.MECHANIC;
            case SPECIALIST_MECHANIC -> EmployeeRoleDTO.SPECIALIST_MECHANIC;
            case ADMINISTRATOR -> EmployeeRoleDTO.ADMINISTRATOR;
        };
    }
}
