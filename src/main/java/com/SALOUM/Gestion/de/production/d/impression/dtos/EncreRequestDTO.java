package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.TypeEncre;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncreRequestDTO {

    @NotNull(message = "Le type d'encre est obligatoire")
    private TypeEncre type;

    @Size(max = 30, message = "La couleur ne doit pas dépasser 30 caractères")
    private String couleur;

    @Digits(integer = 10, fraction = 2,
            message = "Le prix au litre doit avoir au maximum 10 chiffres entiers et 2 décimales")
    private BigDecimal prixLitre;

    @Digits(integer = 4, fraction = 4,
            message = "La consommation doit avoir au maximum 4 chiffres entiers et 4 décimales")
    private BigDecimal mlParM2;
}
