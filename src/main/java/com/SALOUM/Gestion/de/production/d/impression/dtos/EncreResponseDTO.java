package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.TypeEncre;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncreResponseDTO {

    private String id;

    private TypeEncre type;

    private String couleur;

    // Prix au litre en FCFA
    private BigDecimal prixLitre;

    // Consommation moyenne en ml/m²
    private BigDecimal mlParM2;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
