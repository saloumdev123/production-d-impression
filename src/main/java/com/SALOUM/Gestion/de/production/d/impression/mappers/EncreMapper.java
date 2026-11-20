
package com.SALOUM.Gestion.de.production.d.impression.mappers;


import com.SALOUM.Gestion.de.production.d.impression.dtos.EncreRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.EncreResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Encre;

public class EncreMapper {

    public static Encre toEntity(EncreRequestDTO dto) {
        return Encre.builder()
                .type(dto.getType())
                .couleur(dto.getCouleur())
                .prixLitre(dto.getPrixLitre())
                .mlParM2(dto.getMlParM2())
                .build();
    }

    public static EncreResponseDTO toResponseDTO(Encre encre) {
        return EncreResponseDTO.builder()
                .id(encre.getId())
                .type(encre.getType())
                .couleur(encre.getCouleur())
                .prixLitre(encre.getPrixLitre())
                .mlParM2(encre.getMlParM2())
                .createdAt(encre.getCreatedAt())
                .updatedAt(encre.getUpdatedAt())
                .build();
    }
}
