package com.SALOUM.Gestion.de.production.d.impression.repos;

import com.SALOUM.Gestion.de.production.d.impression.entities.HistoriqueStatut;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoriqueStatutRepository extends MongoRepository<HistoriqueStatut, String> {
    List<HistoriqueStatut> findByOrdreProductionIdOrderByDateChangementAsc(String ordreProductionId);

    // Needed when deleting an order (no cascade in Mongo)
    void deleteByOrdreProductionId(String ordreProductionId);

    boolean existsByUtilisateurId(String id);
}