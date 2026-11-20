
package com.SALOUM.Gestion.de.production.d.impression.mappers;

import com.SALOUM.Gestion.de.production.d.impression.dtos.OrdreProductionRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.OrdreProductionResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.OrdreProduction;

import java.util.ArrayList;

public class OrdreProductionMapper {

    public static OrdreProduction toEntity(OrdreProductionRequestDTO dto) {

        return OrdreProduction.builder()
                .numero(dto.getNumero())
                .clientId(dto.getClientId())
                .motifId(dto.getMotifId())
                .textileId(dto.getTextileId())
                .encreId(dto.getEncreId())
                .longueurM(dto.getLongueurM())
                .largeurCm(dto.getLargeurCm())
                .quantiteRouleaux(
                        dto.getQuantiteRouleaux() != null
                                ? dto.getQuantiteRouleaux() : 1)
                .statut(dto.getStatut())
                .priorite(
                        dto.getPriorite() != null
                                ? dto.getPriorite() : 3)
                .operateurId(dto.getOperateurId())
                .dateDebutImpression(dto.getDateDebutImpression())
                .dateFinImpression(dto.getDateFinImpression())
                .dateLivraison(dto.getDateLivraison())
                .metrageReel(dto.getMetrageReel())
                .coutEncre(dto.getCoutEncre())
                .commentaire(dto.getCommentaire())
                .build();
    }

    public static OrdreProductionResponseDTO toResponseDTO(
            OrdreProduction ordre) {

        return OrdreProductionResponseDTO.builder()
                .id(ordre.getId())
                .numero(ordre.getNumero())
                .clientId(ordre.getClientId())
                .motifId(ordre.getMotifId())
                .textileId(ordre.getTextileId())
                .encreId(ordre.getEncreId())
                .longueurM(ordre.getLongueurM())
                .largeurCm(ordre.getLargeurCm())
                .quantiteRouleaux(ordre.getQuantiteRouleaux())
                .statut(ordre.getStatut())
                .priorite(ordre.getPriorite())
                .operateurId(ordre.getOperateurId())
                .dateDebutImpression(ordre.getDateDebutImpression())
                .dateFinImpression(ordre.getDateFinImpression())
                .dateLivraison(ordre.getDateLivraison())
                .metrageReel(ordre.getMetrageReel())
                .coutEncre(ordre.getCoutEncre())
                .commentaire(ordre.getCommentaire())
                .rouleaux(ordre.getRouleaux())
                .createdAt(ordre.getCreatedAt())
                .updatedAt(ordre.getUpdatedAt())
                .build();
    }
}
