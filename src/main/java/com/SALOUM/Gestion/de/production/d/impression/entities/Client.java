package com.SALOUM.Gestion.de.production.d.impression.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "client")
public class Client extends BaseEntity {

    @NotBlank
    @Size(max = 20)
    @Indexed(unique = true)
    private String code;

    @NotBlank
    @Size(max = 150)
    private String nom;

    @Size(max = 100)
    private String contact;

    @Size(max = 30)
    private String telephone;

    @Email
    @Size(max = 150)
    private String email;

    private String adresse;

    @Builder.Default
    private Boolean actif = true;
}