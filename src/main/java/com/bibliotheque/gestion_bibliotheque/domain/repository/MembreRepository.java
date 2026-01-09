package com.bibliotheque.gestion_bibliotheque.domain.repository;


import com.bibliotheque.gestion_bibliotheque.domain.entities.Membre;
import java.util.List;
import java.util.Optional;

public interface MembreRepository {

    // Créer ou mettre à jour un membre
    Membre save(Membre membre);

    // Trouver un membre par ID
    Optional<Membre> findById(Long id);

    // Trouver un membre par email
    Optional<Membre> findByEmail(String email);

    // Trouver tous les membres
    List<Membre> findAll();

    // Trouver par type de membre
    List<Membre> findByTypeMembre(String typeMembre);

    // Trouver par nom
    List<Membre> findByNomContaining(String nom);

    // Supprimer un membre
    void deleteById(Long id);

    // Vérifier si un membre existe
    boolean existsById(Long id);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);
}