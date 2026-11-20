package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutRouleau;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouleauRequestDTO {

    @NotBlank(message = "Le code QR est obligatoire")
    @Size(max = 50, message = "Le code QR ne doit pas dépasser 50 caractères")
    private String codeQr;

    @NotBlank(message = "L'identifiant de l'ordre de production est obligatoire")
    private String ordreProductionId;

    @NotNull(message = "Le numéro de séquence est obligatoire")
    private Integer numeroSequence;

    @Digits(integer = 8, fraction = 2,
            message = "Le métrage doit avoir au maximum 8 chiffres entiers et 2 décimales")
    private BigDecimal metrage;

    @NotNull(message = "Le statut du rouleau est obligatoire")
    private StatutRouleau statut;

    @Size(max = 500, message = "Le chemin du QR code ne doit pas dépasser 500 caractères")
    private String qrCodePath;
}
