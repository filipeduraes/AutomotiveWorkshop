package com.filipeduraes.workshop.client.dtos;

public enum EmployeeRoleDTO
{
    COSTUMER_SERVICE("Atendimento ao Cliente"),
    MECHANIC("Mecanico"),
    SPECIALIST_MECHANIC("Mecanico Especialista"),
    ADMINISTRATOR("Administrador");

    private final String displayName;

    EmployeeRoleDTO(String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return displayName;
    }
}
