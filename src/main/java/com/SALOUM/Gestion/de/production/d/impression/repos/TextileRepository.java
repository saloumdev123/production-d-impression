package com.SALOUM.Gestion.de.production.d.impression.repos;


import com.SALOUM.Gestion.de.production.d.impression.entities.Textile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TextileRepository extends MongoRepository<Textile, String> {
    Optional<Textile> findByCode(String code);

    boolean existsByCode(@NotBlank(message = "Le code du textile est obligatoire") @Size(max = 30, message = "Le code ne doit pas dépasser 30 caractères") String code);
}