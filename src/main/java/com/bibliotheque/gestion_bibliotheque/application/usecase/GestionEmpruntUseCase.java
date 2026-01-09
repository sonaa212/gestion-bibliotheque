package com.bibliotheque.gestion_bibliotheque.domain.service;


import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import com.bibliotheque.gestion_bibliotheque.domain.repository.EmpruntRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreService livreService;
    private final MembreService membreService;

    public EmpruntService(EmpruntRepository empruntRepository,
                          LivreService livreService,
                          MembreService membreService) {
        this.empruntRepository = empruntRepository;
        this.livreService = livreService;
        this.membreService = membreService;
    }

    // === USE CASE: Emprunter un livre ===
    public Emprunt emprunterLivre(Long livreId, Long membreId) {
        // 1. Vérifier que le livre existe et est disponible
        if (!livreService.estDisponible(livreId)) {
            throw new IllegalStateException("Le livre n'est pas disponible");
        }

        // 2. Vérifier que le membre peut emprunter
        int empruntsEnCours = empruntRepository.countByMembreIdAndStatut(membreId, "EN_COURS");
        if (!membreService.peutEmprunter(membreId, empruntsEnCours)) {
            throw new IllegalStateException("Le membre a atteint son quota d'emprunts");
        }

        // 3. Créer l'emprunt (durée: 14 jours)
        LocalDate dateEmprunt = LocalDate.now();
        LocalDate dateRetourPrevue = dateEmprunt.plusDays(14);

        Emprunt emprunt = new Emprunt(null, livreId, membreId, dateEmprunt, dateRetourPrevue);

        // 4. Décrémenter le nombre d'exemplaires disponibles
        livreService.emprunterExemplaire(livreId);

        // 5. Sauvegarder l'emprunt
        return empruntRepository.save(emprunt);
    }

    // === USE CASE: Retourner un livre ===
    public Emprunt retournerLivre(Long empruntId) {
        // 1. Récupérer l'emprunt
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt non trouvé"));

        if (!"EN_COURS".equals(emprunt.getStatut())) {
            throw new IllegalStateException("Cet emprunt n'est pas en cours");
        }

        // 2. Marquer comme retourné et calculer pénalités
        emprunt.retourner();

        // 3. Incrémenter le nombre d'exemplaires disponibles
        livreService.retournerExemplaire(emprunt.getLivreId());

        // 4. Ajuster le score du membre
        if (emprunt.estEnRetard()) {
            membreService.ajusterScore(emprunt.getMembreId(), -10); // Retard: -10 points
        } else {
            membreService.ajusterScore(emprunt.getMembreId(), 5);   // À temps: +5 points
        }

        // 5. Sauvegarder l'emprunt
        return empruntRepository.save(emprunt);
    }

    // === USE CASE: Obtenir les emprunts d'un membre ===
    public List<Emprunt> obtenirEmpruntsParMembre(Long membreId) {
        return empruntRepository.findByMembreId(membreId);
    }

    // === USE CASE: Obtenir les emprunts en cours ===
    public List<Emprunt> obtenirEmpruntsEnCours() {
        return empruntRepository.findByStatut("EN_COURS");
    }

    // === USE CASE: Obtenir les emprunts en retard ===
    public List<Emprunt> obtenirEmpruntsEnRetard() {
        return empruntRepository.findByStatutAndDateRetourPrevueBefore(
                "EN_COURS", LocalDate.now()
        );
    }

    // === USE CASE: Obtenir un emprunt par ID ===
    public Optional<Emprunt> trouverEmpruntParId(Long id) {
        return empruntRepository.findById(id);
    }

    // === USE CASE: Obtenir tous les emprunts ===
    public List<Emprunt> obtenirTousLesEmprunts() {
        return empruntRepository.findAll();
    }
}