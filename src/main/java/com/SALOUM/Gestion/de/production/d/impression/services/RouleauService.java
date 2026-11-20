package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.GenerationRouleauxRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.OrdreProduction;
import com.SALOUM.Gestion.de.production.d.impression.entities.Rouleau;
import com.SALOUM.Gestion.de.production.d.impression.mappers.RouleauMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.OrdreProductionRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.RouleauRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouleauService {

    private final RouleauRepository rouleauRepository;
    private final OrdreProductionRepository ordreRepository;

    // ---------- lecture ----------

    public List<RouleauResponseDTO> findAll(String ordreProductionId) {
        List<Rouleau> rouleaux = ordreProductionId != null
                ? rouleauRepository.findByOrdreProductionIdOrderByNumeroSequenceAsc(ordreProductionId)
                : rouleauRepository.findAll();
        return rouleaux.stream().map(RouleauMapper::toResponseDTO).toList();
    }

    public RouleauResponseDTO findById(String id) {
        return RouleauMapper.toResponseDTO(getOrThrow(id));
    }

    /** Used when a roll's QR code is scanned */
    public RouleauResponseDTO findByCodeQr(String codeQr) {
        return RouleauMapper.toResponseDTO(
                rouleauRepository.findByCodeQr(codeQr)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Rouleau introuvable pour le code QR : " + codeQr)));
    }

    // ---------- création manuelle ----------

    public RouleauResponseDTO create(RouleauRequestDTO dto) {
        checkOrdreExists(dto.getOrdreProductionId());
        checkUnique(dto.getCodeQr(), dto.getOrdreProductionId(), dto.getNumeroSequence());

        try {
            return RouleauMapper.toResponseDTO(rouleauRepository.save(RouleauMapper.toEntity(dto)));
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Code QR ou numéro de séquence déjà utilisé");
        }
    }

    // ---------- génération automatique ----------

    /**
     * Creates the rolls of an order (quantiteRouleaux of them).
     * The order's length is split evenly; the last roll takes the remainder
     * so the total always equals the order's length.
     */
    public List<RouleauResponseDTO> generer(String ordreProductionId, GenerationRouleauxRequestDTO dto) {
        OrdreProduction ordre = ordreRepository.findById(ordreProductionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ordre de production introuvable : " + ordreProductionId));

        if (rouleauRepository.countByOrdreProductionId(ordreProductionId) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Des rouleaux existent déjà pour l'ordre " + ordre.getNumero());
        }

        int quantite = ordre.getQuantiteRouleaux() != null ? ordre.getQuantiteRouleaux() : 1;
        BigDecimal part = ordre.getLongueurM()
                .divide(BigDecimal.valueOf(quantite), 2, RoundingMode.DOWN);
        BigDecimal reste = ordre.getLongueurM().subtract(part.multiply(BigDecimal.valueOf(quantite)));

        String prefixe = "R-" + ordre.getNumero().replaceFirst("^OP-", "OP");  // OP-2025-0001 -> R-OP2025-0001

        List<Rouleau> aCreer = new ArrayList<>();
        for (int i = 1; i <= quantite; i++) {
            BigDecimal metrage = (i == quantite) ? part.add(reste) : part;
            aCreer.add(Rouleau.builder()
                    .codeQr(prefixe + "-" + String.format("%02d", i))   // R-OP2025-0001-01
                    .ordreProductionId(ordreProductionId)
                    .numeroSequence(i)
                    .metrage(metrage)
                    .statut(dto.getStatut())
                    .build());
        }

        try {
            return rouleauRepository.saveAll(aCreer).stream()
                    .map(RouleauMapper::toResponseDTO)
                    .toList();
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Un code QR généré existe déjà : vérifiez les rouleaux de l'ordre " + ordre.getNumero());
        }
    }

    // ---------- modification ----------

    public RouleauResponseDTO update(String id, RouleauRequestDTO dto) {
        Rouleau rouleau = getOrThrow(id);
        checkOrdreExists(dto.getOrdreProductionId());

        boolean codeChange = !rouleau.getCodeQr().equals(dto.getCodeQr());
        boolean seqChange = !rouleau.getOrdreProductionId().equals(dto.getOrdreProductionId())
                || !rouleau.getNumeroSequence().equals(dto.getNumeroSequence());

        if (codeChange && rouleauRepository.existsByCodeQr(dto.getCodeQr())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le code QR " + dto.getCodeQr() + " existe déjà");
        }
        if (seqChange && rouleauRepository.existsByOrdreProductionIdAndNumeroSequence(
                dto.getOrdreProductionId(), dto.getNumeroSequence())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le numéro de séquence " + dto.getNumeroSequence() + " existe déjà pour cet ordre");
        }

        rouleau.setCodeQr(dto.getCodeQr());
        rouleau.setOrdreProductionId(dto.getOrdreProductionId());
        rouleau.setNumeroSequence(dto.getNumeroSequence());
        rouleau.setMetrage(dto.getMetrage());
        rouleau.setStatut(dto.getStatut());
        rouleau.setQrCodePath(dto.getQrCodePath());

        try {
            return RouleauMapper.toResponseDTO(rouleauRepository.save(rouleau));
        } catch (DuplicateKeyException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Code QR ou numéro de séquence déjà utilisé");
        }
    }

    /** Lightweight status change (what a scanner or the workshop screen will call) */
    public RouleauResponseDTO changerStatut(String id, RouleauStatutRequestDTO dto) {
        Rouleau rouleau = getOrThrow(id);
        rouleau.setStatut(dto.getStatut());
        return RouleauMapper.toResponseDTO(rouleauRepository.save(rouleau));
    }

    // ---------- suppression ----------

    public void delete(String id) {
        getOrThrow(id);
        rouleauRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Rouleau getOrThrow(String id) {
        return rouleauRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rouleau introuvable : " + id));
    }

    private void checkOrdreExists(String ordreProductionId) {
        if (!ordreRepository.existsById(ordreProductionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ordre de production introuvable : " + ordreProductionId);
        }
    }

    private void checkUnique(String codeQr, String ordreId, Integer sequence) {
        if (rouleauRepository.existsByCodeQr(codeQr)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le code QR " + codeQr + " existe déjà");
        }
        if (rouleauRepository.existsByOrdreProductionIdAndNumeroSequence(ordreId, sequence)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Le numéro de séquence " + sequence + " existe déjà pour cet ordre");
        }
    }
}