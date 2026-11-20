package com.SALOUM.Gestion.de.production.d.impression.controller;

import com.SALOUM.Gestion.de.production.d.impression.dtos.ChangementStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.OrdreProductionRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.OrdreProductionResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.enums.StatutOP;
import com.SALOUM.Gestion.de.production.d.impression.services.OrdreProductionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordres-production")
@RequiredArgsConstructor
public class OrdreProductionController {

    private final OrdreProductionService ordreService;

    @GetMapping
    public List<OrdreProductionResponseDTO> findAll(
            @RequestParam(required = false) StatutOP statut,
            @RequestParam(required = false) String clientId,
            @RequestParam(required = false) String operateurId) {
        return ordreService.findAll(statut, clientId, operateurId);
    }

    @GetMapping("/{id}")
    public OrdreProductionResponseDTO findById(@PathVariable String id) {
        return ordreService.findById(id);
    }

    @PostMapping
    public ResponseEntity<OrdreProductionResponseDTO> create(
            @Valid @RequestBody OrdreProductionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ordreService.create(dto));
    }

    @PutMapping("/{id}")
    public OrdreProductionResponseDTO update(@PathVariable String id,
                                             @Valid @RequestBody OrdreProductionRequestDTO dto) {
        return ordreService.update(id, dto);
    }

    @PatchMapping("/{id}/statut")
    public OrdreProductionResponseDTO changerStatut(
            @PathVariable String id,
            @Valid @RequestBody ChangementStatutRequestDTO dto) {
        return ordreService.changerStatut(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        ordreService.delete(id);
    }
}