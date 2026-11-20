package com.SALOUM.Gestion.de.production.d.impression.repos;

import com.SALOUM.Gestion.de.production.d.impression.entities.Encre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EncreRepository extends MongoRepository<Encre, String> {
}