
package com.SALOUM.Gestion.de.production.d.impression.mappers;


import com.SALOUM.Gestion.de.production.d.impression.dtos.ClientRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.ClientResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Client;

public class ClientMapper {

    public static Client toEntity(ClientRequestDTO dto) {
        return Client.builder()
                .code(dto.getCode())
                .nom(dto.getNom())
                .contact(dto.getContact())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .adresse(dto.getAdresse())
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .build();
    }

    public static ClientResponseDTO toResponseDTO(Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .code(client.getCode())
                .nom(client.getNom())
                .contact(client.getContact())
                .telephone(client.getTelephone())
                .email(client.getEmail())
                .adresse(client.getAdresse())
                .actif(client.getActif())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
