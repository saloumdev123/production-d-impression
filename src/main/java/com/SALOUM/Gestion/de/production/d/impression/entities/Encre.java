package com.SALOUM.Gestion.de.production.d.impression.entities;

import com.SALOUM.Gestion.de.production.d.impression.enums.TypeEncre;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "encre")
public class Encre extends BaseEntity {

    @NotNull
    private TypeEncre type;

    @Size(max = 30)
    private String couleur;

    /** Prix au litre en FCFA */
    @Digits(integer = 10, fraction = 2)
    @Field(name = "prix_litre", targetType = FieldType.DECIMAL128)
    private BigDecimal prixLitre;

    /** Consommation moyenne en ml/m² (pour estimation du coût) */
    @Digits(integer = 4, fraction = 4)
    @Field(name = "ml_par_m2", targetType = FieldType.DECIMAL128)
    private BigDecimal mlParM2;
}