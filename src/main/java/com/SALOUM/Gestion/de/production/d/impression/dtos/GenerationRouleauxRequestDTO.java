package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutRouleau;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerationRouleauxRequestDTO {

    /** Initial status given to every generated roll */
    @NotNull(message = "Le statut initial des rouleaux est obligatoire")
    private StatutRouleau statut;
}