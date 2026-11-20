package com.SALOUM.Gestion.de.production.d.impression.repos;


import com.SALOUM.Gestion.de.production.d.impression.entities.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Optional<Utilisateur> findByUsername(String username);
    boolean existsByUsername(String username);
}