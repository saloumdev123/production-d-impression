package com.SALOUM.Gestion.de.production.d.impression.dtos;

import com.SALOUM.Gestion.de.production.d.impression.enums.StatutRouleau;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouleauStatutRequestDTO {

    @NotNull(message = "Le statut du rouleau est obligatoire")
    private StatutRouleau statut;
}