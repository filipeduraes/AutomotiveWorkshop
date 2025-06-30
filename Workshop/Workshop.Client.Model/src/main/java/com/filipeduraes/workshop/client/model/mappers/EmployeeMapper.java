// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ClockInTypeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeDTO;
import com.filipeduraes.workshop.client.dtos.EmployeeRoleDTO;
import com.filipeduraes.workshop.core.employee.ClockInType;
import com.filipeduraes.workshop.core.employee.Employee;
import com.filipeduraes.workshop.core.employee.EmployeeRole;
import com.filipeduraes.workshop.core.employee.LocalEmployee;

/**
 * Classe responsável por mapear objetos entre as classes de domínio Employee e seus DTOs correspondentes.
 * Fornece métodos para converter entre diferentes representações de funcionários no sistema.
 *
 * @author Filipe Durães
 */
public class EmployeeMapper
{
    /**
     * Converte um objeto Employee do domínio para sua representação DTO.
     *
     * @param employee o funcionário a ser convertido
     * @return o DTO correspondente ao funcionário
     */
    public static EmployeeDTO toDTO(Employee employee)
    {
        return new EmployeeDTO
        (
            employee.getID(),
            employee.getName(),
            employee.getEmail(),
            toEmployeeRoleDTO(employee.getRole()),
            toClockInTypeDTO(employee.getLastClockIn())
        );
    }

    /**
     * Converte um DTO de funcionário para um objeto Employee do domínio.
     *
     * @param employee o DTO a ser convertido
     * @return o objeto Employee correspondente
     */
    public static Employee fromDTO(EmployeeDTO employee)
    {
        return new Employee
        (
            employee.getName(),
            employee.getEmail(),
            fromEmployeeRoleDTO(employee.getRole())
        );
    }

    /**
     * Converte um DTO de funcionário para um objeto LocalEmployee do domínio.
     *
     * @param employee o DTO a ser convertido
     * @return o objeto LocalEmployee correspondente
     */
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

    /**
     * Converte um cargo de funcionário do domínio para sua representação DTO.
     *
     * @param role o cargo a ser convertido
     * @return o DTO correspondente ao cargo
     */
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

    /**
     * Converte um DTO de cargo para um objeto EmployeeRole do domínio.
     *
     * @param selectedRole o DTO do cargo a ser convertido
     * @return o objeto EmployeeRole correspondente
     */
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

    public static ClockInTypeDTO toClockInTypeDTO(ClockInType clockInType)
    {
        return switch (clockInType)
        {
            case IN -> ClockInTypeDTO.IN;
            case START_BREAK -> ClockInTypeDTO.START_BREAK;
            case END_BREAK -> ClockInTypeDTO.END_BREAK;
            case OUT -> ClockInTypeDTO.OUT;
        };
    }

    public static ClockInType fromClockInTypeDTO(ClockInTypeDTO clockInType)
    {
        return switch (clockInType)
        {
            case IN -> ClockInType.IN;
            case START_BREAK -> ClockInType.START_BREAK;
            case END_BREAK -> ClockInType.END_BREAK;
            case OUT -> ClockInType.OUT;
        };
    }
}
