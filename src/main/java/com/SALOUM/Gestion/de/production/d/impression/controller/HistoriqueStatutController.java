package com.SALOUM.Gestion.de.production.d.impression.controller;

import com.SALOUM.Gestion.de.production.d.impression.dtos.HistoriqueStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.HistoriqueStatutResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.services.HistoriqueStatutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historiques-statut")
@RequiredArgsConstructor
public class HistoriqueStatutController {

    private final HistoriqueStatutService historiqueService;

    @GetMapping("/{id}")
    public HistoriqueStatutResponseDTO findById(@PathVariable String id) {
        return historiqueService.findById(id);
    }

    @GetMapping("/ordre/{ordreProductionId}")
    public List<HistoriqueStatutResponseDTO> findByOrdreProduction(
            @PathVariable String ordreProductionId) {
        return historiqueService.findByOrdreProduction(ordreProductionId);
    }

    @PostMapping
    public ResponseEntity<HistoriqueStatutResponseDTO> create(
            @Valid @RequestBody HistoriqueStatutRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historiqueService.create(dto));
    }
}