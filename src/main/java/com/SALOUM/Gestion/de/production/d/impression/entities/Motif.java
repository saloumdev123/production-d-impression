package com.SALOUM.Gestion.de.production.d.impression.entities;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "motif")
public class Motif extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Indexed(unique = true)
    private String code;

    @NotBlank
    @Size(max = 200)
    private String nom;

    private String description;

    /** Chemin vers le fichier graphique haute résolution (utilisé par le RIP) */
    @Size(max = 500)
    @Field("fichier_path")
    private String fichierPath;

    /** Chemin vers un aperçu (thumbnail) */
    @Size(max = 500)
    @Field("preview_path")
    private String previewPath;

    @Digits(integer = 8, fraction = 2)
    @Field(name = "largeur_cm", targetType = FieldType.DECIMAL128)
    private BigDecimal largeurCm;

    @Digits(integer = 8, fraction = 2)
    @Field(name = "hauteur_cm", targetType = FieldType.DECIMAL128)
    private BigDecimal hauteurCm;

    /** Replaces @ManyToOne Client client */
    @Indexed
    @Field("client_id")
    private String clientId;
}