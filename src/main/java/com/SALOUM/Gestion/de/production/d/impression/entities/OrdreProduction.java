package com.SALOUM.Gestion.de.production.d.impression.entities;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "ordre_production")
public class OrdreProduction extends BaseEntity {

    @NotBlank
    @Size(max = 30)
    @Indexed(unique = true)
    private String numero; // ex: OP-2025-0001

    @NotBlank
    @Indexed
    @Field("client_id")
    private String clientId;

    @Field("motif_id")
    private String motifId;

    @Field("textile_id")
    private String textileId;

    @Field("encre_id")
    private String encreId;

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    @Field(name = "longueur_m", targetType = FieldType.DECIMAL128)
    private BigDecimal longueurM;

    @Digits(integer = 8, fraction = 2)
    @Field(name = "largeur_cm", targetType = FieldType.DECIMAL128)
    private BigDecimal largeurCm;

    @Builder.Default
    @Min(1)
    @Field("quantite_rouleaux")
    private Integer quantiteRouleaux = 1;

    @NotNull
    @Indexed
    private StatutOP statut;

    @Builder.Default
    private Integer priorite = 3; // 1=urgent ... 5=bas

    @Indexed
    @Field("operateur_id")
    private String operateurId;

    @Field("date_debut_impression")
    private LocalDateTime dateDebutImpression;

    @Field("date_fin_impression")
    private LocalDateTime dateFinImpression;

    @Field("date_livraison")
    private LocalDateTime dateLivraison;

    /** Métrage réellement imprimé (saisi ou importé) */
    @Digits(integer = 8, fraction = 2)
    @Field(name = "metrage_reel", targetType = FieldType.DECIMAL128)
    private BigDecimal metrageReel;

    /** Coût encre calculé */
    @Digits(integer = 10, fraction = 2)
    @Field(name = "cout_encre", targetType = FieldType.DECIMAL128)
    private BigDecimal coutEncre;

    private String commentaire;

    /** Embedded: les rouleaux font partie du document de l'ordre */
    @Builder.Default
    private List<Rouleau> rouleaux = new ArrayList<>();

    public void addRouleau(Rouleau rouleau) {
        rouleaux.add(rouleau);
    }
}