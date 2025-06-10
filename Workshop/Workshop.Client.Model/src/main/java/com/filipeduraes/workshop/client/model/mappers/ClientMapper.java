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
            maskCPF(clientDTO.getCPF())
        );
    }

    private static String maskCPF(String cpf)
    {
        String numberOnlyCPF = cpf.replaceAll("\\D", ""); // Substitui qualquer caractere não numérico por uma string vazia

        if(numberOnlyCPF.length() != 11)
        {
            return "";
        }

        String thirdPart = numberOnlyCPF.substring(6, 9);
        String fourthPart = numberOnlyCPF.substring(9);
        return String.format("XXX.XXX.%s-%s", thirdPart, fourthPart);
    }
}
