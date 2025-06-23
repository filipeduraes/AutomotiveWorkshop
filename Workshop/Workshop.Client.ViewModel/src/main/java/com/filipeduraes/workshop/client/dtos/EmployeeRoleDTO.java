// Copyright Filipe Durães. All rights reserved.
package com.filipeduraes.workshop.client.dtos;

/**
 * Representa os diferentes cargos que um funcionário pode ter na oficina automotiva
 * em formato DTO (Data Transfer Object).
 * Cada cargo possui um nome de exibição associado.
 *
 * @author Filipe Durães
 */
public enum EmployeeRoleDTO
{
    /**
     * Cargo responsável pelo atendimento e suporte aos clientes da oficina.
     */
    COSTUMER_SERVICE("Atendimento ao Cliente"),

    /**
     * Cargo para mecânicos que realizam serviços gerais de manutenção.
     */
    MECHANIC("Mecanico"),

    /**
     * Cargo para mecânicos com expertise em serviços complexos e especializados.
     */
    SPECIALIST_MECHANIC("Mecanico Especialista"),

    /**
     * Cargo com acesso administrativo e gerencial do sistema.
     */
    ADMINISTRATOR("Administrador");

    private final String displayName;

    EmployeeRoleDTO(String displayName)
    {
        this.displayName = displayName;
    }

    /**
     * Retorna o nome de exibição do cargo.
     *
     * @return nome de exibição do cargo
     */
    @Override
    public String toString()
    {
        return displayName;
    }
}
