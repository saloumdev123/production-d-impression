package com.SALOUM.Gestion.de.production.d.impression.controller;

import com.SALOUM.Gestion.de.production.d.impression.dtos.GenerationRouleauxRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.RouleauStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.services.RouleauService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rouleaux")
@RequiredArgsConstructor
public class RouleauController {

    private final RouleauService rouleauService;

    @GetMapping
    public List<RouleauResponseDTO> findAll(
            @RequestParam(required = false) String ordreProductionId) {
        return rouleauService.findAll(ordreProductionId);
    }

    @GetMapping("/{id}")
    public RouleauResponseDTO findById(@PathVariable String id) {
        return rouleauService.findById(id);
    }

    @GetMapping("/qr/{codeQr}")
    public RouleauResponseDTO findByCodeQr(@PathVariable String codeQr) {
        return rouleauService.findByCodeQr(codeQr);
    }

    @PostMapping
    public ResponseEntity<RouleauResponseDTO> create(@Valid @RequestBody RouleauRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rouleauService.create(dto));
    }

    @PostMapping("/generer/{ordreProductionId}")
    public ResponseEntity<List<RouleauResponseDTO>> generer(
            @PathVariable String ordreProductionId,
            @Valid @RequestBody GenerationRouleauxRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rouleauService.generer(ordreProductionId, dto));
    }

    @PutMapping("/{id}")
    public RouleauResponseDTO update(@PathVariable String id,
                                     @Valid @RequestBody RouleauRequestDTO dto) {
        return rouleauService.update(id, dto);
    }

    @PatchMapping("/{id}/statut")
    public RouleauResponseDTO changerStatut(@PathVariable String id,
                                            @Valid @RequestBody RouleauStatutRequestDTO dto) {
        return rouleauService.changerStatut(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        rouleauService.delete(id);
    }
}