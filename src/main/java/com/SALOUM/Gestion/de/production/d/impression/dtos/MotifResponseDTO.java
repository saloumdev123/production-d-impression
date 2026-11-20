package com.SALOUM.Gestion.de.production.d.impression.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotifResponseDTO {

    private String id;

    private String code;

    private String nom;

    private String description;

    private String fichierPath;

    private String previewPath;

    private BigDecimal largeurCm;

    private BigDecimal hauteurCm;

    private String clientId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
