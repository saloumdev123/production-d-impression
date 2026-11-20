package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.ClientRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.ClientResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Client;
import com.SALOUM.Gestion.de.production.d.impression.mappers.ClientMapper;
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
public class ClientService {

    private final ClientRepository clientRepository;
    private final OrdreProductionRepository ordreProductionRepository;
    private final MotifRepository motifRepository;

    public List<ClientResponseDTO> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::toResponseDTO)
                .toList();
    }

    public ClientResponseDTO findById(String id) {
        return ClientMapper.toResponseDTO(getOrThrow(id));
    }

    public ClientResponseDTO create(ClientRequestDTO dto) {
        if (clientRepository.existsByCode(dto.getCode())) {
            throw duplicateCode(dto.getCode());
        }
        try {
            Client saved = clientRepository.save(ClientMapper.toEntity(dto));
            return ClientMapper.toResponseDTO(saved);
        } catch (DuplicateKeyException e) {
            // two requests with the same code at the same time: the unique index catches it
            throw duplicateCode(dto.getCode());
        }
    }

    public ClientResponseDTO update(String id, ClientRequestDTO dto) {
        Client client = getOrThrow(id);

        // only check the code if it changed
        if (!client.getCode().equals(dto.getCode())
                && clientRepository.existsByCode(dto.getCode())) {
            throw duplicateCode(dto.getCode());
        }

        client.setCode(dto.getCode());
        client.setNom(dto.getNom());
        client.setContact(dto.getContact());
        client.setTelephone(dto.getTelephone());
        client.setEmail(dto.getEmail());
        client.setAdresse(dto.getAdresse());
        if (dto.getActif() != null) {
            client.setActif(dto.getActif());
        }

        try {
            return ClientMapper.toResponseDTO(clientRepository.save(client));
        } catch (DuplicateKeyException e) {
            throw duplicateCode(dto.getCode());
        }
    }

    public void delete(String id) {
        Client client = getOrThrow(id);

        // MongoDB has no foreign keys: check references manually
        if (ordreProductionRepository.existsByClientId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer le client " + client.getCode()
                            + " : des ordres de production y sont rattachés");
        }
        if (motifRepository.existsByClientId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer le client " + client.getCode()
                            + " : des motifs y sont rattachés");
        }
        clientRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Client getOrThrow(String id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Client introuvable : " + id));
    }

    private ResponseStatusException duplicateCode(String code) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Un client avec le code " + code + " existe déjà");
    }
}