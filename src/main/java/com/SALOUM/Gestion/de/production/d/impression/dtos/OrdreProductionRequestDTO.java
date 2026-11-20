package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.entities.Rouleau;
import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdreProductionRequestDTO {

    @NotBlank(message = "Le numéro de l'ordre de production est obligatoire")
    @Size(max = 30)
    private String numero;

    @NotBlank(message = "Le client est obligatoire")
    private String clientId;

    private String motifId;

    private String textileId;

    private String encreId;

    @NotNull(message = "La longueur est obligatoire")
    @Positive(message = "La longueur doit être positive")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal longueurM;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal largeurCm;

    @Min(value = 1, message = "La quantité de rouleaux doit être au minimum 1")
    @Builder.Default
    private Integer quantiteRouleaux = 1;

    @NotNull(message = "Le statut est obligatoire")
    private StatutOP statut;

    @Min(value = 1)
    @Max(value = 5)
    @Builder.Default
    private Integer priorite = 3;

    private String operateurId;

    private LocalDateTime dateDebutImpression;

    private LocalDateTime dateFinImpression;

    private LocalDateTime dateLivraison;

    @Digits(integer = 8, fraction = 2)
    private BigDecimal metrageReel;

    @Digits(integer = 10, fraction = 2)
    private BigDecimal coutEncre;

    private String commentaire;

}
