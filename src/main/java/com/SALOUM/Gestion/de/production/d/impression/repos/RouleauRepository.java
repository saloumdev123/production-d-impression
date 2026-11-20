package com.SALOUM.Gestion.de.production.d.impression.repos;



import com.SALOUM.Gestion.de.production.d.impression.entities.Rouleau;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RouleauRepository extends MongoRepository<Rouleau, String> {
    Optional<Rouleau> findByCodeQr(String codeQr);
    List<Rouleau> findByOrdreProductionIdOrderByNumeroSequenceAsc(String ordreProductionId);
    void deleteByOrdreProductionId(String ordreProductionId);

    int countByOrdreProductionId(String ordreProductionId);

    boolean existsByCodeQr(@NotBlank(message = "Le code QR est obligatoire") @Size(max = 50, message = "Le code QR ne doit pas dépasser 50 caractères") String codeQr);

    boolean existsByOrdreProductionIdAndNumeroSequence(@NotBlank(message = "L'identifiant de l'ordre de production est obligatoire") String ordreProductionId, @NotNull(message = "Le numéro de séquence est obligatoire") Integer numeroSequence);
}