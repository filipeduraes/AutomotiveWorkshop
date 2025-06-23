// Copyright Filipe Durães. All rights reserved.

package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.core.client.Client;

/**
 * Classe responsável por realizar a conversão entre objetos Client e ClientDTO.
 * Fornece métodos utilitários para mapear dados entre as camadas de domínio e apresentação.
 *
 * @author Filipe Durães
 */
public final class ClientMapper
{
    /**
     * Converte um objeto Client em um objeto ClientDTO.
     *
     * @param client o objeto Client a ser convertido
     * @return um novo objeto ClientDTO com os dados do cliente
     */
    public static ClientDTO toDTO(Client client)
    {
        return new ClientDTO
        (
            client.getID(),
            client.getName(),
            client.getPhoneNumber(),
            client.getEmail(),
            client.getAddress(),
            client.getMaskedCPF()
        );
    }

    /**
     * Converte um objeto ClientDTO em um objeto Client.
     *
     * @param clientDTO o objeto ClientDTO a ser convertido
     * @return um novo objeto Client com os dados do DTO
     */
    public static Client fromDTO(ClientDTO clientDTO)
    {
        return new Client
        (
            clientDTO.getName(),
            clientDTO.getEmail(),
            clientDTO.getPhoneNumber(),
            clientDTO.getAddress(),
            clientDTO.getCPF()
        );
    }
}
