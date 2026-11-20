package com.SALOUM.Gestion.de.production.d.impression.repos;

import com.SALOUM.Gestion.de.production.d.impression.entities.Motif;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MotifRepository extends MongoRepository<Motif, String> {
    Optional<Motif> findByCode(String code);
    boolean existsByCode(String code);
    List<Motif> findByClientId(String clientId);

    boolean existsByClientId(String id);
}