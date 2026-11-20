package com.SALOUM.Gestion.de.production.d.impression.repos;

import com.SALOUM.Gestion.de.production.d.impression.entities.OrdreProduction;
import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OrdreProductionRepository extends MongoRepository<OrdreProduction, String> {

    Optional<OrdreProduction> findByNumero(String numero);
    boolean existsByNumero(String numero);

    List<OrdreProduction> findByClientId(String clientId);
    List<OrdreProduction> findByStatutOrderByPrioriteAsc(StatutOP statut);
    List<OrdreProduction> findByOperateurId(String operateurId);

    // Reference checks before deleting a client, motif, textile, encre or user
    boolean existsByClientId(String clientId);
    boolean existsByMotifId(String motifId);
    boolean existsByTextileId(String textileId);
    boolean existsByEncreId(String encreId);
    boolean existsByOperateurId(String operateurId);
}