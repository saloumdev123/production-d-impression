package com.SALOUM.Gestion.de.production.d.impression.controller;

import com.SALOUM.Gestion.de.production.d.impression.dtos.TextileRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.TextileResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.services.TextileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/textiles")
@RequiredArgsConstructor
public class TextileController {

    private final TextileService textileService;

    @GetMapping
    public List<TextileResponseDTO> findAll() {
        return textileService.findAll();
    }

    @GetMapping("/{id}")
    public TextileResponseDTO findById(@PathVariable String id) {
        return textileService.findById(id);
    }

    @PostMapping
    public ResponseEntity<TextileResponseDTO> create(@Valid @RequestBody TextileRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(textileService.create(dto));
    }

    @PutMapping("/{id}")
    public TextileResponseDTO update(@PathVariable String id,
                                     @Valid @RequestBody TextileRequestDTO dto) {
        return textileService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        textileService.delete(id);
    }
}