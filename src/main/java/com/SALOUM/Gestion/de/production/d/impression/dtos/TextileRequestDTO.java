package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.TypeTextile;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextileRequestDTO {

    @NotBlank(message = "Le code du textile est obligatoire")
    @Size(max = 30, message = "Le code ne doit pas dépasser 30 caractères")
    private String code;

    @NotBlank(message = "Le nom du textile est obligatoire")
    @Size(max = 150, message = "Le nom ne doit pas dépasser 150 caractères")
    private String nom;

    @NotNull(message = "Le type de textile est obligatoire")
    private TypeTextile type;

    @Size(max = 100, message = "La composition ne doit pas dépasser 100 caractères")
    private String composition;

    @Digits(integer = 8, fraction = 2,
            message = "La largeur doit avoir au maximum 8 chiffres entiers et 2 décimales")
    private BigDecimal largeurCm;

    @Positive(message = "Le grammage doit être positif")
    private Integer grammageGm2;

    @Digits(integer = 10, fraction = 2,
            message = "Le prix au mètre doit avoir au maximum 10 chiffres entiers et 2 décimales")
    private BigDecimal prixMetre;
}
