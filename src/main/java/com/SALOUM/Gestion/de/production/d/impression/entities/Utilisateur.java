package com.SALOUM.Gestion.de.production.d.impression.entities;

import com.SALOUM.Gestion.de.production.d.impression.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "utilisateur")
public class Utilisateur extends BaseEntity {

    @NotBlank
    @Size(max = 50)
    @Indexed(unique = true)
    private String username;

    @NotBlank
    @Size(max = 255)
    @Field("password_hash")
    private String passwordHash;

    @Size(max = 150)
    @Field("nom_complet")
    private String nomComplet;

    @NotNull
    private Role role;

    @Builder.Default
    private Boolean actif = true;
}