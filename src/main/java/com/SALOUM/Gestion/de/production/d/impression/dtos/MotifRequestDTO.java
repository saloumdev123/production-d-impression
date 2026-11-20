package com.SALOUM.Gestion.de.production.d.impression.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotifRequestDTO {

    @NotBlank(message = "Le code du motif est obligatoire")
    @Size(max = 50, message = "Le code ne doit pas dépasser 50 caractères")
    private String code;

    @NotBlank(message = "Le nom du motif est obligatoire")
    @Size(max = 200, message = "Le nom ne doit pas dépasser 200 caractères")
    private String nom;

    private String description;

    @Size(max = 500, message = "Le chemin du fichier ne doit pas dépasser 500 caractères")
    private String fichierPath;

    @Size(max = 500, message = "Le chemin de l'aperçu ne doit pas dépasser 500 caractères")
    private String previewPath;

    @Digits(integer = 8, fraction = 2,
            message = "La largeur doit avoir au maximum 8 chiffres entiers et 2 décimales")
    private BigDecimal largeurCm;

    @Digits(integer = 8, fraction = 2,
            message = "La hauteur doit avoir au maximum 8 chiffres entiers et 2 décimales")
    private BigDecimal hauteurCm;

    private String clientId;
}
