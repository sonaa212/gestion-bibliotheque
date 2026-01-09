package com.bibliotheque.gestion_bibliotheque.adapters.repository;

import com.bibliotheque.gestion_bibliotheque.domain.entities.Livre;
import com.bibliotheque.gestion_bibliotheque.domain.repository.LivreRepository;
import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity.LivreEntity;
import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.repository.JpaLivreRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LivreRepositoryAdapter implements LivreRepository {

    private final JpaLivreRepository jpaRepository;

    public LivreRepositoryAdapter(JpaLivreRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Livre save(Livre livre) {
        LivreEntity entity = toEntity(livre);
        LivreEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Livre> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Livre> findByIsbn(String isbn) {
        return jpaRepository.findByIsbn(isbn).map(this::toDomain);
    }

    @Override
    public List<Livre> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livre> findByTitreContaining(String titre) {
        return jpaRepository.findByTitreContaining(titre).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livre> findByAuteurContaining(String auteur) {
        return jpaRepository.findByAuteurContaining(auteur).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livre> findByCategorie(String categorie) {
        return jpaRepository.findByCategorie(categorie).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Livre> findByNombreDisponiblesGreaterThan(int quantite) {
        return jpaRepository.findByNombreDisponiblesGreaterThan(quantite).stream()
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

    // ========== MAPPERS PRIVÉS ==========

    private LivreEntity toEntity(Livre livre) {
        LivreEntity entity = new LivreEntity();
        entity.setId(livre.getId());
        entity.setTitre(livre.getTitre());
        entity.setAuteur(livre.getAuteur());
        entity.setIsbn(livre.getIsbn());
        entity.setEditeur(livre.getEditeur());
        entity.setAnneePublication(livre.getAnneePublication());
        entity.setCategorie(livre.getCategorie());
        entity.setNombreExemplaires(livre.getNombreExemplaires());
        entity.setNombreDisponibles(livre.getNombreDisponibles());
        entity.setEtatPhysique(livre.getEtatPhysique());
        return entity;
    }

    private Livre toDomain(LivreEntity entity) {
        Livre livre = new Livre();
        livre.setId(entity.getId());
        livre.setTitre(entity.getTitre());
        livre.setAuteur(entity.getAuteur());
        livre.setIsbn(entity.getIsbn());
        livre.setEditeur(entity.getEditeur());
        livre.setAnneePublication(entity.getAnneePublication());
        livre.setCategorie(entity.getCategorie());
        livre.setNombreExemplaires(entity.getNombreExemplaires());
        livre.setNombreDisponibles(entity.getNombreDisponibles());
        livre.setEtatPhysique(entity.getEtatPhysique());
        return livre;
    }
}