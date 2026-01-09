package com.bibliotheque.gestion_bibliotheque.domain.repository;


import com.bibliotheque.gestion_bibliotheque.domain.entities.Livre;

import java.util.List;
import java.util.Optional;

public interface LivreRepository {

    // Créer ou mettre à jour un livre
    Livre save(Livre livre);

    // Trouver un livre par ID
    Optional<Livre> findById(Long id);

    // Trouver un livre par ISBN
    Optional<Livre> findByIsbn(String isbn);

    // Trouver tous les livres
    List<Livre> findAll();

    // Rechercher par titre
    List<Livre> findByTitreContaining(String titre);

    // Rechercher par auteur
    List<Livre> findByAuteurContaining(String auteur);

    // Rechercher par catégorie
    List<Livre> findByCategorie(String categorie);

    // Trouver les livres disponibles
    List<Livre> findByNombreDisponiblesGreaterThan(int quantite);

    // Supprimer un livre
    void deleteById(Long id);

    // Vérifier si un livre existe
    boolean existsById(Long id);
}