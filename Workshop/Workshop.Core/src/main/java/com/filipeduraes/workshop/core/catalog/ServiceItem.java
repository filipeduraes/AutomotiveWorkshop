// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core.catalog;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa um serviço disponível no catálogo da oficina, incluindo
 * informações sobre o colaborador responsável pelo serviço.
 *
 * @author Filipe Durães
 */
public class ServiceItem extends PricedItem
{
    private final UUID employeeID;

    /**
     * Cria um novo serviço com as informações especificadas.
     *
     * @param employeeID identificador único do colaborador responsável
     * @param name nome do serviço
     * @param description descrição detalhada do serviço
     * @param price preço do serviço
     */
    public ServiceItem(UUID employeeID, String name, String description, BigDecimal price)
    {
        super(name, description, price);
        this.employeeID = employeeID;
    }

    /**
     * Cria uma cópia de um serviço existente.
     *
     * @param serviceItem serviço a ser copiado
     */
    public ServiceItem(ServiceItem serviceItem)
    {
        super(serviceItem);
        this.employeeID = serviceItem.employeeID;
    }

    /**
     * Cria uma cópia de um item existente e adiciona um responsável.
     *
     * @param pricedItem item a ser copiado
     * @param employeeID ID do responsável pelo serviço
     */
    public ServiceItem(PricedItem pricedItem, UUID employeeID)
    {
        super(pricedItem);
        this.employeeID = employeeID;
    }

    /**
     * Obtém o identificador único do colaborador responsável pelo serviço.
     *
     * @return identificador único do colaborador
     */
    public UUID getEmployeeID()
    {
        return employeeID;
    }

    /**
     * Retorna uma representação textual do serviço, incluindo todas as suas informações.
     *
     * @return string formatada com as informações do serviço
     */
    @Override
    public String toString()
    {
        return String.format
        (
            "ID: %s, ID do Colaborador: %s, Nome: %s, Descricao: %s, Preco: %s",
            getID(),
            getEmployeeID(),
            getName(),
            getDescription(),
            getPrice()
        );
    }
}