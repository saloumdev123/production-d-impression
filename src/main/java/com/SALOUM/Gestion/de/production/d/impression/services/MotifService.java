package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.MotifRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.MotifResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Motif;
import com.SALOUM.Gestion.de.production.d.impression.mappers.MotifMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.ClientRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.MotifRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.OrdreProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotifService {

    private final MotifRepository motifRepository;
    private final ClientRepository clientRepository;
    private final OrdreProductionRepository ordreProductionRepository;

    public List<MotifResponseDTO> findAll() {
        return motifRepository.findAll().stream()
                .map(MotifMapper::toResponseDTO)
                .toList();
    }

    public List<MotifResponseDTO> findByClient(String clientId) {
        return motifRepository.findByClientId(clientId).stream()
                .map(MotifMapper::toResponseDTO)
                .toList();
    }

    public MotifResponseDTO findById(String id) {
        return MotifMapper.toResponseDTO(getOrThrow(id));
    }

    public MotifResponseDTO create(MotifRequestDTO dto) {
        checkClientExists(dto.getClientId());

        if (motifRepository.existsByCode(dto.getCode())) {
            throw duplicateCode(dto.getCode());
        }
        try {
            Motif saved = motifRepository.save(MotifMapper.toEntity(dto));
            return MotifMapper.toResponseDTO(saved);
        } catch (DuplicateKeyException e) {
            throw duplicateCode(dto.getCode());
        }
    }

    public MotifResponseDTO update(String id, MotifRequestDTO dto) {
        Motif motif = getOrThrow(id);
        checkClientExists(dto.getClientId());

        // only check the code if it changed
        if (!motif.getCode().equals(dto.getCode())
                && motifRepository.existsByCode(dto.getCode())) {
            throw duplicateCode(dto.getCode());
        }

        motif.setCode(dto.getCode());
        motif.setNom(dto.getNom());
        motif.setDescription(dto.getDescription());
        motif.setFichierPath(dto.getFichierPath());
        motif.setPreviewPath(dto.getPreviewPath());
        motif.setLargeurCm(dto.getLargeurCm());
        motif.setHauteurCm(dto.getHauteurCm());
        motif.setClientId(dto.getClientId());

        try {
            return MotifMapper.toResponseDTO(motifRepository.save(motif));
        } catch (DuplicateKeyException e) {
            throw duplicateCode(dto.getCode());
        }
    }

    public void delete(String id) {
        Motif motif = getOrThrow(id);

        // MongoDB has no foreign keys: check references manually
        if (ordreProductionRepository.existsByMotifId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer le motif " + motif.getCode()
                            + " : des ordres de production l'utilisent");
        }
        motifRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Motif getOrThrow(String id) {
        return motifRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Motif introuvable : " + id));
    }

    /** clientId is optional, but if given it must point to an existing client */
    private void checkClientExists(String clientId) {
        if (clientId != null && !clientRepository.existsById(clientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Client introuvable : " + clientId);
        }
    }

    private ResponseStatusException duplicateCode(String code) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Un motif avec le code " + code + " existe déjà");
    }
}