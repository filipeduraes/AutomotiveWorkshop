package com.filipeduraes.workshop.client.dtos;

import java.util.UUID;

/**
 * Representa um Data Transfer Object (DTO) para funcionários da oficina.
 * Esta classe é responsável por transportar dados de funcionários entre as diferentes camadas do sistema.
 *
 * @author Filipe Durães
 */
public class EmployeeDTO
{
    private UUID id;
    private String name = "";
    private String email = "";
    private EmployeeRoleDTO role = EmployeeRoleDTO.COSTUMER_SERVICE;
    private ClockInTypeDTO lastClockIn = ClockInTypeDTO.OUT;
    private int passwordHash = -1;

    /**
     * Cria uma nova instância vazia de EmployeeDTO.
     */
    public EmployeeDTO()
    {
    }

    /**
     * Cria uma nova instância de EmployeeDTO com credenciais básicas.
     *
     * @param email Email do funcionário
     * @param passwordHash Hash da senha do funcionário
     */
    public EmployeeDTO(String email, int passwordHash)
    {
        this.email = email;
        this.passwordHash = passwordHash;

        name = "";
    }

    /**
     * Cria uma nova instância de EmployeeDTO com informações completas do funcionário.
     *
     * @param id Identificador único do funcionário
     * @param name Nome do funcionário
     * @param email Email do funcionário
     * @param role Cargo do funcionário
     */
    public EmployeeDTO(UUID id, String name, String email, EmployeeRoleDTO role)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public EmployeeDTO(UUID id, String name, String email, EmployeeRoleDTO role, ClockInTypeDTO lastClockIn)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.lastClockIn = lastClockIn;
    }

    /**
     * Cria uma nova instância de EmployeeDTO para registro de funcionário.
     *
     * @param name Nome do funcionário
     * @param email Email do funcionário
     * @param role Cargo do funcionário
     * @param passwordHash Hash da senha do funcionário
     */
    public EmployeeDTO(String name, String email, EmployeeRoleDTO role, int passwordHash)
    {
        this.name = name;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    /**
     * Obtém o identificador único do funcionário.
     *
     * @return identificador único do funcionário
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Define o identificador único do funcionário.
     *
     * @param id novo identificador único
     */
    public void setID(UUID id)
    {
        this.id = id;
    }

    /**
     * Obtém o nome do funcionário.
     *
     * @return nome do funcionário
     */
    public String getName()
    {
        return name;
    }

    /**
     * Define o nome do funcionário.
     *
     * @param name novo nome
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Obtém o email do funcionário.
     *
     * @return email do funcionário
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Define o email do funcionário.
     *
     * @param email novo email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Obtém o cargo do funcionário.
     *
     * @return cargo do funcionário
     */
    public EmployeeRoleDTO getRole()
    {
        return role;
    }

    /**
     * Define o cargo do funcionário.
     *
     * @param role novo cargo
     */
    public void setRole(EmployeeRoleDTO role)
    {
        this.role = role;
    }

    /**
     * Obtém o hash da senha do funcionário.
     *
     * @return hash da senha
     */
    public int getPasswordHash()
    {
        return passwordHash;
    }

    /**
     * Define o hash da senha do funcionário.
     *
     * @param passwordHash novo hash de senha
     */
    public void setPasswordHash(int passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    /**
     * Obtém o último ponto batido pelo colaborador.
     *
     * @return último ponto batido
     */
    public ClockInTypeDTO getLastClockIn()
    {
        return lastClockIn;
    }

    /**
     * Define o último ponto batido pelo colaborador.
     *
     * @param lastClockIn último ponto batido
     */
    public void setLastClockIn(ClockInTypeDTO lastClockIn)
    {
        this.lastClockIn = lastClockIn;
    }

    /**
     * Retorna uma representação em string do funcionário.
     * A string contém o nome, cargo e email do funcionário em formato de lista.
     *
     * @return string representando o funcionário
     */
    @Override
    public String toString()
    {
        return String.format
        (
            " - ID: %s%n - Nome: %s%n - Cargo: %s%n - Email: %s%n - Ultimo Ponto: %s",
            getID(),
            getName(),
            getRole(),
            getEmail(),
            getLastClockIn()
        );
    }
}
