
package com.SALOUM.Gestion.de.production.d.impression.mappers;


import com.SALOUM.Gestion.de.production.d.impression.dtos.TextileRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.TextileResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Textile;

public class TextileMapper {

    public static Textile toEntity(TextileRequestDTO dto) {

        return Textile.builder()
                .code(dto.getCode())
                .nom(dto.getNom())
                .type(dto.getType())
                .composition(dto.getComposition())
                .largeurCm(dto.getLargeurCm())
                .grammageGm2(dto.getGrammageGm2())
                .prixMetre(dto.getPrixMetre())
                .build();
    }

    public static TextileResponseDTO toResponseDTO(Textile textile) {

        return TextileResponseDTO.builder()
                .id(textile.getId())
                .code(textile.getCode())
                .nom(textile.getNom())
                .type(textile.getType())
                .composition(textile.getComposition())
                .largeurCm(textile.getLargeurCm())
                .grammageGm2(textile.getGrammageGm2())
                .prixMetre(textile.getPrixMetre())
                .createdAt(textile.getCreatedAt())
                .updatedAt(textile.getUpdatedAt())
                .build();
    }
}
