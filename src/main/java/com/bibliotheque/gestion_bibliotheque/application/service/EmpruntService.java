package com.bibliotheque.gestion_bibliotheque.application.service;


import com.bibliotheque.gestion_bibliotheque.adapters.messaging.event.EmpruntCreeEvent;
import com.bibliotheque.gestion_bibliotheque.adapters.messaging.producer.EmpruntEventProducer;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Livre;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Membre;
import com.bibliotheque.gestion_bibliotheque.domain.repository.EmpruntRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmpruntService {

    private final EmpruntRepository empruntRepository;
    private final LivreService livreService;
    private final MembreService membreService;
    private final ReservationService reservationService;
    private final EmpruntEventProducer empruntEventProducer; // Peut être null si Kafka est désactivé

    public EmpruntService(EmpruntRepository empruntRepository,
                          LivreService livreService,
                          MembreService membreService,
                          ReservationService reservationService,
                          @Autowired(required = false) EmpruntEventProducer empruntEventProducer) {
        this.empruntRepository = empruntRepository;
        this.livreService = livreService;
        this.membreService = membreService;
        this.reservationService = reservationService;
        this.empruntEventProducer = empruntEventProducer; // Sera null si Kafka est désactivé
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
        Emprunt empruntSauvegarde = empruntRepository.save(emprunt);

        // 6. PUBLIER UN ÉVÉNEMENT KAFKA (Architecture Événementielle - EDA)
        // NOTE: Si Kafka est désactivé, empruntEventProducer sera null
        if (empruntEventProducer != null) {
            try {
                Livre livre = livreService.trouverLivreParId(livreId).orElse(null);
                Membre membre = membreService.trouverMembreParId(membreId).orElse(null);

                if (livre != null && membre != null) {
                    EmpruntCreeEvent event = new EmpruntCreeEvent(
                            empruntSauvegarde.getId(),
                            livreId,
                            livre.getTitre(),
                            membreId,
                            membre.getNom() + " " + membre.getPrenom(),
                            LocalDateTime.now(),
                            dateRetourPrevue.atStartOfDay()
                    );
                    empruntEventProducer.publierEmpruntCree(event);
                }
            } catch (Exception e) {
                // Log l'erreur mais ne pas bloquer la transaction
                System.err.println("Erreur lors de la publication de l'événement Kafka: " + e.getMessage());
            }
        }

        return empruntSauvegarde;
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

        // 5. Notifier la prochaine réservation s'il y en a une
        reservationService.notifierProchaineReservation(emprunt.getLivreId());

        // 6. Sauvegarder l'emprunt
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
}
