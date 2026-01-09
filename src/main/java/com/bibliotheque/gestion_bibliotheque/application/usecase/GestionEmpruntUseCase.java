package com.bibliotheque.gestion_bibliotheque.application.usecase;

import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import com.bibliotheque.gestion_bibliotheque.domain.repository.EmpruntRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GestionEmpruntUseCase {

    private final EmpruntRepository empruntRepository;
    private final GestionLivreUseCase gestionLivreUseCase;
    private final GestionMembreUseCase gestionMembreUseCase;

    public GestionEmpruntUseCase(EmpruntRepository empruntRepository,
                                 GestionLivreUseCase gestionLivreUseCase,
                                 GestionMembreUseCase gestionMembreUseCase) {
        this.empruntRepository = empruntRepository;
        this.gestionLivreUseCase = gestionLivreUseCase;
        this.gestionMembreUseCase = gestionMembreUseCase;
    }

    // === USE CASE: Emprunter un livre ===
    public Emprunt emprunterLivre(Long livreId, Long membreId) {
        // 1. Vérifier que le livre existe et est disponible
        if (!gestionLivreUseCase.estDisponible(livreId)) {
            throw new IllegalStateException("Le livre n'est pas disponible");
        }

        // 2. Vérifier que le membre peut emprunter
        int empruntsEnCours = empruntRepository.countByMembreIdAndStatut(membreId, "EN_COURS");
        if (!gestionMembreUseCase.peutEmprunter(membreId, empruntsEnCours)) {
            throw new IllegalStateException("Le membre a atteint son quota d'emprunts");
        }

        // 3. Créer l'emprunt (durée: 14 jours)
        LocalDate dateEmprunt = LocalDate.now();
        LocalDate dateRetourPrevue = dateEmprunt.plusDays(14);

        Emprunt emprunt = new Emprunt(null, livreId, membreId, dateEmprunt, dateRetourPrevue);

        // 4. Décrémenter le nombre d'exemplaires disponibles
        gestionLivreUseCase.emprunterExemplaire(livreId);

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
        gestionLivreUseCase.retournerExemplaire(emprunt.getLivreId());

        // 4. Ajuster le score du membre
        if (emprunt.estEnRetard()) {
            gestionMembreUseCase.ajusterScore(emprunt.getMembreId(), -10); // Retard: -10 points
        } else {
            gestionMembreUseCase.ajusterScore(emprunt.getMembreId(), 5);   // À temps: +5 points
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