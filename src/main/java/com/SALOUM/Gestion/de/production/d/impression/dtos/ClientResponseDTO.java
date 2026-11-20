package com.SALOUM.Gestion.de.production.d.impression.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {

    private String id;

    private String code;

    private String nom;

    private String contact;

    private String telephone;

    private String email;

    private String adresse;

    private Boolean actif;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
