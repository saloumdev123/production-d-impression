package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.HistoriqueStatutRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.HistoriqueStatutResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.HistoriqueStatut;
import com.SALOUM.Gestion.de.production.d.impression.entities.OrdreProduction;
import com.SALOUM.Gestion.de.production.d.impression.mappers.HistoriqueStatutMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.HistoriqueStatutRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.OrdreProductionRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoriqueStatutService {

    private final HistoriqueStatutRepository historiqueRepository;
    private final OrdreProductionRepository ordreProductionRepository;
    private final UtilisateurRepository utilisateurRepository;

    public HistoriqueStatutResponseDTO findById(String id) {
        return HistoriqueStatutMapper.toResponseDTO(
                historiqueRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Historique introuvable : " + id)));
    }

    public List<HistoriqueStatutResponseDTO> findByOrdreProduction(String ordreProductionId) {
        if (!ordreProductionRepository.existsById(ordreProductionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ordre de production introuvable : " + ordreProductionId);
        }
        return historiqueRepository
                .findByOrdreProductionIdOrderByDateChangementAsc(ordreProductionId)
                .stream()
                .map(HistoriqueStatutMapper::toResponseDTO)
                .toList();
    }

    public HistoriqueStatutResponseDTO create(HistoriqueStatutRequestDTO dto) {
        // MongoDB has no foreign keys: verify the references manually
        OrdreProduction ordre = ordreProductionRepository.findById(dto.getOrdreProductionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ordre de production introuvable : " + dto.getOrdreProductionId()));

        if (dto.getUtilisateurId() != null
                && !utilisateurRepository.existsById(dto.getUtilisateurId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Utilisateur introuvable : " + dto.getUtilisateurId());
        }

        HistoriqueStatut historique = HistoriqueStatutMapper.toEntity(dto);

        // if the caller did not give the previous status, take the order's current one
        if (historique.getAncienStatut() == null) {
            historique.setAncienStatut(ordre.getStatut());
        }

        return HistoriqueStatutMapper.toResponseDTO(historiqueRepository.save(historique));
    }
}