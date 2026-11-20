package com.SALOUM.Gestion.de.production.d.impression.services;

import com.SALOUM.Gestion.de.production.d.impression.dtos.ChangementMotDePasseRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.UtilisateurRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.UtilisateurResponseDTO;
import com.SALOUM.Gestion.de.production.d.impression.dtos.UtilisateurUpdateRequestDTO;
import com.SALOUM.Gestion.de.production.d.impression.entities.Utilisateur;
import com.SALOUM.Gestion.de.production.d.impression.mappers.UtilisateurMapper;
import com.SALOUM.Gestion.de.production.d.impression.repos.HistoriqueStatutRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.OrdreProductionRepository;
import com.SALOUM.Gestion.de.production.d.impression.repos.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final OrdreProductionRepository ordreProductionRepository;
    private final HistoriqueStatutRepository historiqueRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UtilisateurResponseDTO> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurMapper::toResponseDTO)
                .toList();
    }

    public UtilisateurResponseDTO findById(String id) {
        return UtilisateurMapper.toResponseDTO(getOrThrow(id));
    }

    public UtilisateurResponseDTO create(UtilisateurRequestDTO dto) {
        if (utilisateurRepository.existsByUsername(dto.getUsername())) {
            throw duplicateUsername(dto.getUsername());
        }

        Utilisateur utilisateur = UtilisateurMapper.toEntity(dto);
        // the mapper put the RAW password in passwordHash: replace it with the BCrypt hash
        utilisateur.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        try {
            return UtilisateurMapper.toResponseDTO(utilisateurRepository.save(utilisateur));
        } catch (DuplicateKeyException e) {
            throw duplicateUsername(dto.getUsername());
        }
    }

    public UtilisateurResponseDTO update(String id, UtilisateurUpdateRequestDTO dto) {
        Utilisateur utilisateur = getOrThrow(id);

        if (!utilisateur.getUsername().equals(dto.getUsername())
                && utilisateurRepository.existsByUsername(dto.getUsername())) {
            throw duplicateUsername(dto.getUsername());
        }

        utilisateur.setUsername(dto.getUsername());
        utilisateur.setNomComplet(dto.getNomComplet());
        utilisateur.setRole(dto.getRole());
        if (dto.getActif() != null) {
            utilisateur.setActif(dto.getActif());
        }
        // the password is never touched here

        try {
            return UtilisateurMapper.toResponseDTO(utilisateurRepository.save(utilisateur));
        } catch (DuplicateKeyException e) {
            throw duplicateUsername(dto.getUsername());
        }
    }

    public void changerMotDePasse(String id, ChangementMotDePasseRequestDTO dto) {
        Utilisateur utilisateur = getOrThrow(id);
        utilisateur.setPasswordHash(passwordEncoder.encode(dto.getNouveauMotDePasse()));
        utilisateurRepository.save(utilisateur);
    }

    public void delete(String id) {
        Utilisateur utilisateur = getOrThrow(id);

        // MongoDB has no foreign keys: check references manually
        if (ordreProductionRepository.existsByOperateurId(id)
                || historiqueRepository.existsByUtilisateurId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Impossible de supprimer l'utilisateur " + utilisateur.getUsername()
                            + " : il est référencé par des ordres ou un historique. "
                            + "Désactivez-le plutôt (actif = false).");
        }
        utilisateurRepository.deleteById(id);
    }

    // ---------- helpers ----------

    private Utilisateur getOrThrow(String id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Utilisateur introuvable : " + id));
    }

    private ResponseStatusException duplicateUsername(String username) {
        return new ResponseStatusException(HttpStatus.CONFLICT,
                "Le nom d'utilisateur " + username + " existe déjà");
    }
}