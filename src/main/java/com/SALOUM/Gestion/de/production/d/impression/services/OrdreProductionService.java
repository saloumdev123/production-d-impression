package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.ChangementStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.OrdreProductionRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.OrdreProductionResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.HistoriqueStatut;
import com.SALOUM.Gestion.de.production.d.impression.entities.OrdreProduction;
import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import com.SALOUM.Gestion.de.production.d.impression.mappers.OrdreProductionMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdreProductionService {

    private final OrdreProductionRepository ordreRepository;
    private final ClientRepository clientRepository;
    private final MotifRepository motifRepository;
    private final TextileRepository textileRepository;
    private final EncreRepository encreRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final RouleauRepository rouleauRepository;
    private final HistoriqueStatutRepository historiqueRepository;

    // ---------- lecture ----------

    /** One filter at a time; without filter, returns everything. */
    public List<OrdreProductionResponseDTO> findAll(StatutOP statut, String clientId, String operateurId) {
        List<OrdreProduction> ordres;
        if (statut != null) {
            ordres = ordreRepository.findByStatutOrderByPrioriteAsc(statut);
        } else if (clientId != null) {
            ordres = ordreRepository.findByClientId(clientId);
        } else if (operateurId != null) {
            ordres = ordreRepository.findByOperateurId(operateurId);
        } else {
            ordres = ordreRepository.findAll();
        }
        return ordres.stream().map(OrdreProductionMapper::toResponseDTO).toList();
    }

    public OrdreProductionResponseDTO findById(String id) {
        return OrdreProductionMapper.toResponseDTO(getOrThrow(id));
    }

    // ---------- création ----------

    public OrdreProductionResponseDTO create(OrdreProductionRequestDTO dto) {
        checkReferences(dto);

        if (ordreRepository.existsByNumero(dto.getNumero())) {
            throw duplicateNumero(dto.getNumero());
        }

        OrdreProduction saved;
        try {
            saved = ordreRepository.save(OrdreProductionMapper.toEntity(dto));
        } catch (DuplicateKeyException e) {
            throw duplicateNumero(dto.getNumero());
        }

        // first history entry
        enregistrerHistorique(saved.getId(), null, saved.getStatut(),
                null, "Création de l'ordre de production");

        return OrdreProductionMapper.toResponseDTO(saved);
    }

    // ---------- modification ----------

    public OrdreProductionResponseDTO update(String id, OrdreProductionRequestDTO dto) {
        OrdreProduction ordre = getOrThrow(id);
        checkReferences(dto);

        if (!ordre.getNumero().equals(dto.getNumero())
                && ordreRepository.existsByNumero(dto.getNumero())) {
            throw duplicateNumero(dto.getNumero());
        }

        StatutOP ancienStatut = ordre.getStatut();

        ordre.setNumero(dto.getNumero());
        ordre.setClientId(dto.getClientId());
        ordre.setMotifId(dto.getMotifId());
        ordre.setTextileId(dto.getTextileId());
        ordre.setEncreId(dto.getEncreId());
        ordre.setLongueurM(dto.getLongueurM());
        ordre.setLargeurCm(dto.getLargeurCm());
        ordre.setQuantiteRouleaux(dto.getQuantiteRouleaux() != null ? dto.getQuantiteRouleaux() : 1);
        ordre.setStatut(dto.getStatut());
        ordre.setPriorite(dto.getPriorite() != null ? dto.getPriorite() : 3);
        ordre.setOperateurId(dto.getOperateurId());
        ordre.setDateDebutImpression(dto.getDateDebutImpression());
        ordre.setDateFinImpression(dto.getDateFinImpression());
        ordre.setDateLivraison(dto.getDateLivraison());
        ordre.setMetrageReel(dto.getMetrageReel());
        ordre.setCoutEncre(dto.getCoutEncre());
        ordre.setCommentaire(dto.getCommentaire());

        OrdreProduction saved;
        try {
            saved = ordreRepository.save(ordre);
        } catch (DuplicateKeyException e) {
            throw duplicateNumero(dto.getNumero());
        }

        // if the status changed through a normal update, keep the history complete
        if (ancienStatut != saved.getStatut()) {
            enregistrerHistorique(saved.getId(), ancienStatut, saved.getStatut(),
                    null, "Statut modifié lors de la mise à jour");
        }

        return OrdreProductionMapper.toResponseDTO(saved);
    }

    /** Preferred way to change a status: updates the order AND records the history. */
    public OrdreProductionResponseDTO changerStatut(String id, ChangementStatutRequestDTO dto) {
        OrdreProduction ordre = getOrThrow(id);

        if (dto.getUtilisateurId() != null
                && !utilisateurRepository.existsById(dto.getUtilisateurId())) {
            throw notFound("Utilisateur", dto.getUtilisateurId());
        }

        StatutOP ancien = ordre.getStatut();
        if (ancien == dto.getNouveauStatut()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "L'ordre est déjà au statut " + ancien);
        }

        ordre.setStatut(dto.getNouveauStatut());
        OrdreProduction saved = ordreRepository.save(ordre);

        enregistrerHistorique(saved.getId(), ancien, dto.getNouveauStatut(),
                dto.getUtilisateurId(), dto.getCommentaire());

        return OrdreProductionMapper.toResponseDTO(saved);
    }

    // ---------- suppression ----------

    public void delete(String id) {
        getOrThrow(id);

        // replaces cascade = ALL / orphanRemoval from JPA
        rouleauRepository.deleteByOrdreProductionId(id);
        historiqueRepository.deleteByOrdreProductionId(id);
        ordreRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private OrdreProduction getOrThrow(String id) {
        return ordreRepository.findById(id)
                .orElseThrow(() -> notFound("Ordre de production", id));
    }

    /** MongoDB has no foreign keys: verify every referenced id exists. */
    private void checkReferences(OrdreProductionRequestDTO dto) {
        if (!clientRepository.existsById(dto.getClientId())) {
            throw notFound("Client", dto.getClientId());
        }
        if (dto.getMotifId() != null && !motifRepository.existsById(dto.getMotifId())) {
            throw notFound("Motif", dto.getMotifId());
        }
        if (dto.getTextileId() != null && !textileRepository.existsById(dto.getTextileId())) {
            throw notFound("Textile", dto.getTextileId());
        }
        if (dto.getEncreId() != null && !encreRepository.existsById(dto.getEncreId())) {
            throw notFound("Encre", dto.getEncreId());
        }
        if (dto.getOperateurId() != null && !utilisateurRepository.existsById(dto.getOperateurId())) {
            throw notFound("Opérateur", dto.getOperateurId());
        }
    }

    private void enregistrerHistorique(String ordreId, StatutOP ancien, StatutOP nouveau,
                                       String utilisateurId, String commentaire) {
        historiqueRepository.save(HistoriqueStatut.builder()
                .ordreProductionId(ordreId)
                .ancienStatut(ancien)
                .nouveauStatut(nouveau)
                .utilisateurId(utilisateurId)
                .commentaire(commentaire)
                .build());   // dateChangement defaults to now()
    }

    private ResponseStatusException notFound(String what, String id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, what + " introuvable : " + id);
    }

    private ResponseStatusException duplicateNumero(String numero) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Un ordre de production avec le numéro " + numero + " existe déjà");
    }
}