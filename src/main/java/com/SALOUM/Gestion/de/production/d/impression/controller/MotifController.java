package com.SALOUM.Gestion.de.production.d.impression.controller;

import com.SALOUM.Gestion.de.production.d.impression.dtos.MotifRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.MotifResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.services.MotifService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/motifs")
@RequiredArgsConstructor
public class MotifController {

    private final MotifService motifService;

    @GetMapping
    public List<MotifResponseDTO> findAll(@RequestParam(required = false) String clientId) {
        return clientId != null ? motifService.findByClient(clientId) : motifService.findAll();
    }

    @GetMapping("/{id}")
    public MotifResponseDTO findById(@PathVariable String id) {
        return motifService.findById(id);
    }

    @PostMapping
    public ResponseEntity<MotifResponseDTO> create(@Valid @RequestBody MotifRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motifService.create(dto));
    }

    @PutMapping("/{id}")
    public MotifResponseDTO update(@PathVariable String id,
                                   @Valid @RequestBody MotifRequestDTO dto) {
        return motifService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        motifService.delete(id);
    }
}