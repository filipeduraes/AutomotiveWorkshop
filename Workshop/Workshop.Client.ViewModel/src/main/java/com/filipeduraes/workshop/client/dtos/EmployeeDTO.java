package com.filipeduraes.workshop.client.dtos;

public class EmployeeDTO
{
    private String name;
    private String email;
    private EmployeeRoleDTO role;
    private int passwordHash = -1;

    public EmployeeDTO(String email, int passwordHash)
    {
        this.email = email;
        this.passwordHash = passwordHash;

        name = "";
        role = EmployeeRoleDTO.COSTUMER_SERVICE;
    }

    public EmployeeDTO(String name, String email, EmployeeRoleDTO role)
    {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public EmployeeDTO(String name, String email, EmployeeRoleDTO role, int passwordHash)
    {
        this.name = name;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public EmployeeRoleDTO getRole()
    {
        return role;
    }

    public void setRole(EmployeeRoleDTO role)
    {
        this.role = role;
    }

    public int getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(int passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString()
    {
        return String.format
        (
            " - Nome: %s%n - Cargo: %s%n - Email: %s",
            getName(),
            getRole(),
            getEmail()
        );
    }
}
