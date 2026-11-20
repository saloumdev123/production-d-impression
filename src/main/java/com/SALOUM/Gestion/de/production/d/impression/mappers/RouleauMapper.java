
package com.SALOUM.Gestion.de.production.d.impression.mappers;


import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Rouleau;

public class RouleauMapper {

    public static Rouleau toEntity(RouleauRequestDTO dto) {

        return Rouleau.builder()
                .codeQr(dto.getCodeQr())
                .ordreProductionId(dto.getOrdreProductionId())
                .numeroSequence(dto.getNumeroSequence())
                .metrage(dto.getMetrage())
                .statut(dto.getStatut())
                .qrCodePath(dto.getQrCodePath())
                .build();
    }

    public static RouleauResponseDTO toResponseDTO(Rouleau rouleau) {

        return RouleauResponseDTO.builder()
                .id(rouleau.getId())
                .codeQr(rouleau.getCodeQr())
                .ordreProductionId(rouleau.getOrdreProductionId())
                .numeroSequence(rouleau.getNumeroSequence())
                .metrage(rouleau.getMetrage())
                .statut(rouleau.getStatut())
                .qrCodePath(rouleau.getQrCodePath())
                .createdAt(rouleau.getCreatedAt())
                .updatedAt(rouleau.getUpdatedAt())
                .build();
    }
}
