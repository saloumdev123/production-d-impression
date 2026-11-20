package com.SALOUM.Gestion.de.production.d.impression.entities;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "historique_statut")
public class HistoriqueStatut {

    @Id
    private String id;

    @NotNull
    @Indexed
    @Field("ordre_production_id")
    private String ordreProductionId;

    @Field("ancien_statut")
    private StatutOP ancienStatut;

    @NotNull
    @Field("nouveau_statut")
    private StatutOP nouveauStatut;

    @Field("utilisateur_id")
    private String utilisateurId;

    @Builder.Default
    @Field("date_changement")
    private LocalDateTime dateChangement = LocalDateTime.now();

    private String commentaire;
}