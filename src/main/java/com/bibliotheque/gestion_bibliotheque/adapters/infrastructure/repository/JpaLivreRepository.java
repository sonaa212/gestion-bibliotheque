package com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.repository;

import com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity.LivreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaLivreRepository extends JpaRepository<LivreEntity, Long> {

    Optional<LivreEntity> findByIsbn(String isbn);

    List<LivreEntity> findByTitreContaining(String titre);

    List<LivreEntity> findByAuteurContaining(String auteur);

    List<LivreEntity> findByCategorie(String categorie);

    List<LivreEntity> findByNombreDisponiblesGreaterThan(int quantite);
}