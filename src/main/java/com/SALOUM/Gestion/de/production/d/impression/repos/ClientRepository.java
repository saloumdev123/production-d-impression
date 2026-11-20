package com.SALOUM.Gestion.de.production.d.impression.repos;

import com.SALOUM.Gestion.de.production.d.impression.entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByCode(String code);
    boolean existsByCode(String code);
}