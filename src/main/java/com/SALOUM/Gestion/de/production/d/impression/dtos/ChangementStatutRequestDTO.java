package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangementStatutRequestDTO {

    @NotNull(message = "Le nouveau statut est obligatoire")
    private StatutOP nouveauStatut;

    private String utilisateurId;

    private String commentaire;
}