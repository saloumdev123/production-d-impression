
package com.SALOUM.Gestion.de.production.d.impression.mappers;


import com.SALOUM.Gestion.de.production.d.impression.dtos.UtilisateurRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.UtilisateurResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Utilisateur;

public class UtilisateurMapper {

    public static Utilisateur toEntity(UtilisateurRequestDTO dto) {

        return Utilisateur.builder()
                .username(dto.getUsername())
                .passwordHash(dto.getPassword())
                .nomComplet(dto.getNomComplet())
                .role(dto.getRole())
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .build();
    }

    public static UtilisateurResponseDTO toResponseDTO(Utilisateur utilisateur) {

        return UtilisateurResponseDTO.builder()
                .id(utilisateur.getId())
                .username(utilisateur.getUsername())
                .nomComplet(utilisateur.getNomComplet())
                .role(utilisateur.getRole())
                .actif(utilisateur.getActif())
                .createdAt(utilisateur.getCreatedAt())
                .updatedAt(utilisateur.getUpdatedAt())
                .build();
    }
}
