package com.filipeduraes.workshop.client.model.mappers;

import com.filipeduraes.workshop.client.dtos.ClientDTO;
import com.filipeduraes.workshop.core.client.Client;

public final class ClientMapper
{
    public static ClientDTO toDTO(Client client)
    {
        return new ClientDTO
        (
            client.getID(),
            client.getName(),
            client.getEmail(),
            client.getPhoneNumber(),
            client.getAddress(),
            client.getMaskedCPF()
        );
    }

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
