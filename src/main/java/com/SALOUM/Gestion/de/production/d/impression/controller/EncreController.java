package com.SALOUM.Gestion.de.production.d.impression.controller;

import com.SALOUM.Gestion.de.production.d.impression.dtos.EncreRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.EncreResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.services.EncreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encres")
@RequiredArgsConstructor
public class EncreController {

    private final EncreService encreService;

    @GetMapping
    public List<EncreResponseDTO> findAll() {
        return encreService.findAll();
    }

    @GetMapping("/{id}")
    public EncreResponseDTO findById(@PathVariable String id) {
        return encreService.findById(id);
    }

    @PostMapping
    public ResponseEntity<EncreResponseDTO> create(@Valid @RequestBody EncreRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(encreService.create(dto));
    }

    @PutMapping("/{id}")
    public EncreResponseDTO update(@PathVariable String id,
                                   @Valid @RequestBody EncreRequestDTO dto) {
        return encreService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        encreService.delete(id);
    }
}