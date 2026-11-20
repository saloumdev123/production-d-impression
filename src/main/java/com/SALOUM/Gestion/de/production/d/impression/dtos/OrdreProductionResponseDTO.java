package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.entities.Rouleau;
import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdreProductionResponseDTO {

    private String id;

    private String numero;

    private String clientId;

    private String motifId;

    private String textileId;

    private String encreId;

    private BigDecimal longueurM;

    private BigDecimal largeurCm;

    private Integer quantiteRouleaux;

    private StatutOP statut;

    private Integer priorite;

    private String operateurId;

    private LocalDateTime dateDebutImpression;

    private LocalDateTime dateFinImpression;

    private LocalDateTime dateLivraison;

    private BigDecimal metrageReel;

    private BigDecimal coutEncre;

    private String commentaire;

    private List<Rouleau> rouleaux;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
