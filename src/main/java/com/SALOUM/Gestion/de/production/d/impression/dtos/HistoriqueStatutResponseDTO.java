package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueStatutResponseDTO {

    private String id;

    private String ordreProductionId;

    private StatutOP ancienStatut;

    private StatutOP nouveauStatut;

    private String utilisateurId;

    private LocalDateTime dateChangement;

    private String commentaire;
}
