package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.EncreRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.EncreResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Encre;
import com.SALOUM.Gestion.de.production.d.impression.mappers.EncreMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.EncreRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.OrdreProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EncreService {

    private final EncreRepository encreRepository;
    private final OrdreProductionRepository ordreProductionRepository;

    public List<EncreResponseDTO> findAll() {
        return encreRepository.findAll().stream()
                .map(EncreMapper::toResponseDTO)
                .toList();
    }

    public EncreResponseDTO findById(String id) {
        return EncreMapper.toResponseDTO(getOrThrow(id));
    }

    public EncreResponseDTO create(EncreRequestDTO dto) {
        Encre saved = encreRepository.save(EncreMapper.toEntity(dto));
        return EncreMapper.toResponseDTO(saved);
    }

    public EncreResponseDTO update(String id, EncreRequestDTO dto) {
        Encre encre = getOrThrow(id);

        encre.setType(dto.getType());
        encre.setCouleur(dto.getCouleur());
        encre.setPrixLitre(dto.getPrixLitre());
        encre.setMlParM2(dto.getMlParM2());

        return EncreMapper.toResponseDTO(encreRepository.save(encre));
    }

    public void delete(String id) {
        getOrThrow(id);

        // MongoDB has no foreign keys: check references manually
        if (ordreProductionRepository.existsByEncreId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer cette encre : des ordres de production l'utilisent");
        }
        encreRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Encre getOrThrow(String id) {
        return encreRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Encre introuvable : " + id));
    }
}