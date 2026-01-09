package com.bibliotheque.gestion_bibliotheque.domain.repository;


import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpruntRepository {

    // Créer ou mettre à jour un emprunt
    Emprunt save(Emprunt emprunt);

    // Trouver un emprunt par ID
    Optional<Emprunt> findById(Long id);

    // Trouver tous les emprunts
    List<Emprunt> findAll();

    // Trouver les emprunts d'un membre
    List<Emprunt> findByMembreId(Long membreId);

    // Trouver les emprunts d'un livre
    List<Emprunt> findByLivreId(Long livreId);

    // Trouver les emprunts en cours d'un membre
    List<Emprunt> findByMembreIdAndStatut(Long membreId, String statut);

    // Trouver les emprunts en cours
    List<Emprunt> findByStatut(String statut);

    // Trouver les emprunts en retard
    List<Emprunt> findByStatutAndDateRetourPrevueBefore(String statut, LocalDate date);

    // Compter les emprunts en cours d'un membre
    int countByMembreIdAndStatut(Long membreId, String statut);

    // Supprimer un emprunt
    void deleteById(Long id);

    // Vérifier si un emprunt existe
    boolean existsById(Long id);
}