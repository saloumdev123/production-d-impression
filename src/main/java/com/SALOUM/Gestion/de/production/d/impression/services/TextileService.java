package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.TextileRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.TextileResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Textile;
import com.SALOUM.Gestion.de.production.d.impression.mappers.TextileMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.OrdreProductionRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.TextileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TextileService {

    private final TextileRepository textileRepository;
    private final OrdreProductionRepository ordreProductionRepository;

    public List<TextileResponseDTO> findAll() {
        return textileRepository.findAll().stream()
                .map(TextileMapper::toResponseDTO)
                .toList();
    }

    public TextileResponseDTO findById(String id) {
        return TextileMapper.toResponseDTO(getOrThrow(id));
    }

    public TextileResponseDTO create(TextileRequestDTO dto) {
        if (textileRepository.existsByCode(dto.getCode())) {
            throw duplicateCode(dto.getCode());
        }
        try {
            Textile saved = textileRepository.save(TextileMapper.toEntity(dto));
            return TextileMapper.toResponseDTO(saved);
        } catch (DuplicateKeyException e) {
            throw duplicateCode(dto.getCode());
        }
    }

    public TextileResponseDTO update(String id, TextileRequestDTO dto) {
        Textile textile = getOrThrow(id);

        // only check the code if it changed
        if (!textile.getCode().equals(dto.getCode())
                && textileRepository.existsByCode(dto.getCode())) {
            throw duplicateCode(dto.getCode());
        }

        textile.setCode(dto.getCode());
        textile.setNom(dto.getNom());
        textile.setType(dto.getType());
        textile.setComposition(dto.getComposition());
        textile.setLargeurCm(dto.getLargeurCm());
        textile.setGrammageGm2(dto.getGrammageGm2());
        textile.setPrixMetre(dto.getPrixMetre());

        try {
            return TextileMapper.toResponseDTO(textileRepository.save(textile));
        } catch (DuplicateKeyException e) {
            throw duplicateCode(dto.getCode());
        }
    }

    public void delete(String id) {
        Textile textile = getOrThrow(id);

        // MongoDB has no foreign keys: check references manually
        if (ordreProductionRepository.existsByTextileId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer le textile " + textile.getCode()
                            + " : des ordres de production l'utilisent");
        }
        textileRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Textile getOrThrow(String id) {
        return textileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Textile introuvable : " + id));
    }

    private ResponseStatusException duplicateCode(String code) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Un textile avec le code " + code + " existe déjà");
    }
}