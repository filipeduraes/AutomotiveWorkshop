// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.core;

import java.util.UUID;

/**
 * Classe base para todas as entidades identificáveis por um ID único no sistema da oficina.
 * Fornece funcionalidades básicas de identificação e gerenciamento de entidades,
 * incluindo atribuição e comparação de identificadores UUID.
 *
 * @author Filipe Durães
 */
public class WorkshopEntity
{
    private UUID id;

    /**
     * Cria uma nova entidade sem ID definido.
     * O ID deve ser atribuído posteriormente através do método assignID.
     */
    public WorkshopEntity(){}

    /**
     * Construtor de cópia que cria uma nova entidade a partir de uma existente.
     * Copia o ID da entidade original para a nova instância.
     *
     * @param other entidade a ser copiada
     */
    public WorkshopEntity(WorkshopEntity other)
    {
        assignID(other.getID());
    }

    /**
     * Atribui um identificador único à entidade.
     * Este método permite definir ou alterar o ID da entidade.
     *
     * @param newID novo identificador UUID da entidade
     */
    public void assignID(UUID newID)
    {
        id = newID;
    }

    /**
     * Obtém o identificador único da entidade.
     *
     * @return identificador UUID da entidade, ou null se não foi atribuído
     */
    public UUID getID()
    {
        return id;
    }

    /**
     * Verifica se um ID é igual ao ID da entidade.
     * Realiza uma comparação direta entre os identificadores UUID.
     *
     * @param otherID ID a ser comparado
     * @return true se os IDs forem iguais, false caso contrário
     */
    public boolean matchID(UUID otherID)
    {
        return id.equals(otherID);
    }
}
