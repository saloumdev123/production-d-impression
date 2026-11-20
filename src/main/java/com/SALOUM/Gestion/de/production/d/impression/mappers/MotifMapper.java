
package com.SALOUM.Gestion.de.production.d.impression.mappers;

import com.SALOUM.Gestion.de.production.d.impression.dtos.MotifRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.MotifResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Motif;

public class MotifMapper {

    public static Motif toEntity(MotifRequestDTO dto) {
        return Motif.builder()
                .code(dto.getCode())
                .nom(dto.getNom())
                .description(dto.getDescription())
                .fichierPath(dto.getFichierPath())
                .previewPath(dto.getPreviewPath())
                .largeurCm(dto.getLargeurCm())
                .hauteurCm(dto.getHauteurCm())
                .clientId(dto.getClientId())
                .build();
    }

    public static MotifResponseDTO toResponseDTO(Motif motif) {
        return MotifResponseDTO.builder()
                .id(motif.getId())
                .code(motif.getCode())
                .nom(motif.getNom())
                .description(motif.getDescription())
                .fichierPath(motif.getFichierPath())
                .previewPath(motif.getPreviewPath())
                .largeurCm(motif.getLargeurCm())
                .hauteurCm(motif.getHauteurCm())
                .clientId(motif.getClientId())
                .createdAt(motif.getCreatedAt())
                .updatedAt(motif.getUpdatedAt())
                .build();
    }
}
