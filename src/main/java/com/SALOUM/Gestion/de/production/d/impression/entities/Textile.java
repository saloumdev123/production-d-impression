package com.SALOUM.Gestion.de.production.d.impression.entities;

import com.SALOUM.Gestion.de.production.d.impression.enums.TypeTextile;
import jakarta.validation.constraints.Digits;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "textile")
public class Textile extends BaseEntity {

    @NotBlank
    @Size(max = 30)
    @Indexed(unique = true)
    private String code;

    @NotBlank
    @Size(max = 150)
    private String nom;

    @NotNull
    private TypeTextile type;

    @Size(max = 100)
    private String composition;

    @Digits(integer = 8, fraction = 2)
    @Field(name = "largeur_cm", targetType = FieldType.DECIMAL128)
    private BigDecimal largeurCm;

    @Positive
    @Field("grammage_gm2")
    private Integer grammageGm2;

    @Digits(integer = 10, fraction = 2)
    @Field(name = "prix_metre", targetType = FieldType.DECIMAL128)
    private BigDecimal prixMetre;
}