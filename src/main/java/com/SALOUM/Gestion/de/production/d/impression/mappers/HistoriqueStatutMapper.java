
package com.SALOUM.Gestion.de.production.d.impression.mappers;


import com.SALOUM.Gestion.de.production.d.impression.dtos.HistoriqueStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.HistoriqueStatutResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.HistoriqueStatut;

import java.time.LocalDateTime;

public class HistoriqueStatutMapper {

    public static HistoriqueStatut toEntity(HistoriqueStatutRequestDTO dto) {
        return HistoriqueStatut.builder()
                .ordreProductionId(dto.getOrdreProductionId())
                .ancienStatut(dto.getAncienStatut())
                .nouveauStatut(dto.getNouveauStatut())
                .utilisateurId(dto.getUtilisateurId())
                .commentaire(dto.getCommentaire())
                .dateChangement(LocalDateTime.now())
                .build();
    }

    public static HistoriqueStatutResponseDTO toResponseDTO(
            HistoriqueStatut historique) {

        return HistoriqueStatutResponseDTO.builder()
                .id(historique.getId())
                .ordreProductionId(historique.getOrdreProductionId())
                .ancienStatut(historique.getAncienStatut())
                .nouveauStatut(historique.getNouveauStatut())
                .utilisateurId(historique.getUtilisateurId())
                .dateChangement(historique.getDateChangement())
                .commentaire(historique.getCommentaire())
                .build();
    }
}
