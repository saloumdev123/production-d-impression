package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutRouleau;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouleauResponseDTO {

    private String id;

    private String codeQr;

    private String ordreProductionId;

    private Integer numeroSequence;

    private BigDecimal metrage;

    private StatutRouleau statut;

    private String qrCodePath;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
