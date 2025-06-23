// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.auth;

/**
 * Representa um funcionário no sistema com suas credenciais de acesso.
 *
 * @author Filipe Durães
 */
public class LocalEmployee extends Employee
{
    private int hashPassword;

    /**
     * Cria um funcionário com suas informações e senha de acesso.
     *
     * @param name         Nome do funcionário
     * @param email        Email do funcionário
     * @param role         Cargo do funcionário
     * @param hashPassword Hash da senha do funcionário
     */
    public LocalEmployee(String name, String email, EmployeeRole role, int hashPassword)
    {
        super(name, email, role);
        this.hashPassword = hashPassword;
    }

    /**
     * Cria uma nova instância de LocalEmployee copiando as informações
     * de um objeto LocalEmployee existente, incluindo as credenciais
     * de acesso.
     *
     * @param localEmployee o funcionário local existente cujas informações
     *                       serão copiadas para a nova instância
     */
    public LocalEmployee(LocalEmployee localEmployee)
    {
        super(localEmployee.getName(), localEmployee.getEmail(), localEmployee.getRole());
        assignID(localEmployee.getID());
        hashPassword = localEmployee.hashPassword;
    }

    /**
     * Define o hash da senha do funcionário.
     *
     * @param hashPassword o novo hash da senha do funcionário
     */
    public void setHashPassword(int hashPassword)
    {
        this.hashPassword = hashPassword;
    }

    /**
     * Verifica se a senha fornecida é válida.
     *
     * @param comparedHashPassword Hash da senha a ser verificada
     * @return True se a senha for válida
     */
    public boolean isPasswordValid(int comparedHashPassword)
    {
        return comparedHashPassword == hashPassword;
    }

    /**
     * Retorna uma representação em string do funcionário local,
     * incluindo suas informações básicas e o hash da senha.
     *
     * @return String contendo nome, email, cargo e hash da senha do funcionário
     */
    @Override
    public String toString()
    {
        return String.format("%s, hashPassword=%d", super.toString(), hashPassword);
    }
}
