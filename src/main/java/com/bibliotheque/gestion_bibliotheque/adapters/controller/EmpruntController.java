package com.bibliotheque.gestion_bibliotheque.adapters.controller;

import com.bibliotheque.gestion_bibliotheque.application.dto.EmpruntDto;
import com.bibliotheque.gestion_bibliotheque.application.mapper.EmpruntMapper;
import com.bibliotheque.gestion_bibliotheque.application.service.EmpruntService;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
@Tag(name = "Gestion des Emprunts", description = "APIs pour gérer les emprunts de livres")
public class EmpruntController {

    private final EmpruntService empruntService;

    public EmpruntController(EmpruntService empruntService) {
        this.empruntService = empruntService;
    }

    // === EMPRUNTER UN LIVRE ===
    @PostMapping
    @Operation(summary = "Emprunter un livre", description = "Crée un nouvel emprunt pour un membre et un livre")
    public ResponseEntity<EmpruntDto> emprunterLivre(
            @RequestParam Long livreId,
            @RequestParam Long membreId) {
        try {
            Emprunt emprunt = empruntService.emprunterLivre(livreId, membreId);
            return ResponseEntity.status(HttpStatus.CREATED).body(EmpruntMapper.toDto(emprunt));
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Retourne 400 Bad Request si le livre n'est pas disponible ou le membre ne peut pas emprunter
            return ResponseEntity.badRequest().build();
        }
    }

    // === RETOURNER UN LIVRE ===
    @PutMapping("/{empruntId}/retour")
    @Operation(summary = "Retourner un livre", description = "Enregistre le retour d'un livre emprunté")
    public ResponseEntity<EmpruntDto> retournerLivre(@PathVariable Long empruntId) {
        try {
            Emprunt emprunt = empruntService.retournerLivre(empruntId);
            return ResponseEntity.ok(EmpruntMapper.toDto(emprunt));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // === OBTENIR LES EMPRUNTS D'UN MEMBRE ===
    @GetMapping("/membre/{membreId}")
    @Operation(summary = "Obtenir les emprunts d'un membre", description = "Récupère l'historique des emprunts d'un membre")
    public ResponseEntity<List<EmpruntDto>> obtenirEmpruntsParMembre(@PathVariable Long membreId) {
        List<Emprunt> emprunts = empruntService.obtenirEmpruntsParMembre(membreId);
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }

    // === OBTENIR TOUS LES EMPRUNTS EN COURS ===
    @GetMapping("/en-cours")
    @Operation(summary = "Obtenir les emprunts en cours", description = "Récupère tous les emprunts actuellement en cours")
    public ResponseEntity<List<EmpruntDto>> obtenirEmpruntsEnCours() {
        List<Emprunt> emprunts = empruntService.obtenirEmpruntsEnCours();
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }

    // === OBTENIR LES EMPRUNTS EN RETARD ===
    @GetMapping("/en-retard")
    @Operation(summary = "Obtenir les emprunts en retard", description = "Récupère tous les emprunts qui sont en retard")
    public ResponseEntity<List<EmpruntDto>> obtenirEmpruntsEnRetard() {
        List<Emprunt> emprunts = empruntService.obtenirEmpruntsEnRetard();
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }

    // === OBTENIR UN EMPRUNT PAR ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un emprunt par ID", description = "Récupère les détails d'un emprunt spécifique")
    public ResponseEntity<EmpruntDto> obtenirEmpruntParId(@PathVariable Long id) {
        return empruntService.trouverEmpruntParId(id)
                .map(emprunt -> ResponseEntity.ok(EmpruntMapper.toDto(emprunt)))
                .orElse(ResponseEntity.notFound().build());
    }
}
