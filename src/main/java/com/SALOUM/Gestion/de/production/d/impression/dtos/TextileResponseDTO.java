package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.TypeTextile;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TextileResponseDTO {

    private String id;

    private String code;

    private String nom;

    private TypeTextile type;

    private String composition;

    private BigDecimal largeurCm;

    private Integer grammageGm2;

    private BigDecimal prixMetre;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
