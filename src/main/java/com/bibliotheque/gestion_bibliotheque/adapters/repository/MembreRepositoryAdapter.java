package com.bibliotheque.gestion_bibliotheque.adapters.repository;

import com.bibliotheque.gestion_bibliotheque.domain.entities.Membre;
import com.bibliotheque.gestion_bibliotheque.domain.repository.MembreRepository;
import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity.MembreEntity;
import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.repository.JpaMembreRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MembreRepositoryAdapter implements MembreRepository {

    private final JpaMembreRepository jpaRepository;

    public MembreRepositoryAdapter(JpaMembreRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Membre save(Membre membre) {
        MembreEntity entity = toEntity(membre);
        MembreEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Membre> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Membre> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<Membre> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Membre> findByTypeMembre(String typeMembre) {
        return jpaRepository.findByTypeMembre(typeMembre).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Membre> findByNomContaining(String nom) {
        return jpaRepository.findByNomContaining(nom).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    // ========== MAPPERS PRIVÉS ==========

    private MembreEntity toEntity(Membre membre) {
        MembreEntity entity = new MembreEntity();
        entity.setId(membre.getId());
        entity.setNom(membre.getNom());
        entity.setPrenom(membre.getPrenom());
        entity.setEmail(membre.getEmail());
        entity.setTypeMembre(membre.getTypeMembre());
        entity.setQuotaEmprunt(membre.getQuotaEmprunt());
        entity.setScoreFiabilite(membre.getScoreFiabilite());
        entity.setDateInscription(membre.getDateInscription());
        return entity;
    }

    private Membre toDomain(MembreEntity entity) {
        Membre membre = new Membre();
        membre.setId(entity.getId());
        membre.setNom(entity.getNom());
        membre.setPrenom(entity.getPrenom());
        membre.setEmail(entity.getEmail());
        membre.setTypeMembre(entity.getTypeMembre());
        membre.setQuotaEmprunt(entity.getQuotaEmprunt());
        membre.setScoreFiabilite(entity.getScoreFiabilite());
        membre.setDateInscription(entity.getDateInscription());
        return membre;
    }
}