package com.bibliotheque.gestion_bibliotheque.adapters.controller;

import com.bibliotheque.gestion_bibliotheque.application.dto.LivreDto;
import com.bibliotheque.gestion_bibliotheque.application.mapper.LivreMapper;
import com.bibliotheque.gestion_bibliotheque.application.service.LivreService;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Livre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
@Tag(name = "Gestion des Livres", description = "APIs pour gérer le catalogue de livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    // === AJOUTER UN LIVRE ===
    @PostMapping
    @Operation(summary = "Ajouter un nouveau livre", description = "Ajoute un livre au catalogue de la bibliothèque")
    public ResponseEntity<LivreDto> ajouterLivre(@RequestBody LivreDto livreDto) {
        try {
            Livre livre = LivreMapper.toEntity(livreDto);
            Livre livreAjoute = livreService.ajouterLivre(livre);
            return ResponseEntity.status(HttpStatus.CREATED).body(LivreMapper.toDto(livreAjoute));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // === OBTENIR TOUS LES LIVRES ===
    @GetMapping
    @Operation(summary = "Obtenir tous les livres", description = "Récupère la liste complète des livres")
    public ResponseEntity<List<LivreDto>> obtenirTousLesLivres() {
        List<Livre> livres = livreService.obtenirTousLesLivres();
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    // === OBTENIR UN LIVRE PAR ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un livre par ID", description = "Récupère les détails d'un livre spécifique")
    public ResponseEntity<LivreDto> obtenirLivreParId(@PathVariable Long id) {
        return livreService.trouverLivreParId(id)
                .map(livre -> ResponseEntity.ok(LivreMapper.toDto(livre)))
                .orElse(ResponseEntity.notFound().build());
    }

    // === RECHERCHER PAR TITRE ===
    @GetMapping("/recherche/titre")
    @Operation(summary = "Rechercher par titre", description = "Recherche des livres par titre (partiel)")
    public ResponseEntity<List<LivreDto>> rechercherParTitre(@RequestParam String titre) {
        List<Livre> livres = livreService.rechercherParTitre(titre);
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    // === RECHERCHER PAR AUTEUR ===
    @GetMapping("/recherche/auteur")
    @Operation(summary = "Rechercher par auteur", description = "Recherche des livres par auteur (partiel)")
    public ResponseEntity<List<LivreDto>> rechercherParAuteur(@RequestParam String auteur) {
        List<Livre> livres = livreService.rechercherParAuteur(auteur);
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    // === RECHERCHER PAR CATÉGORIE ===
    @GetMapping("/recherche/categorie")
    @Operation(summary = "Rechercher par catégorie", description = "Filtre les livres par catégorie")
    public ResponseEntity<List<LivreDto>> rechercherParCategorie(@RequestParam String categorie) {
        List<Livre> livres = livreService.rechercherParCategorie(categorie);
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    // === OBTENIR LES LIVRES DISPONIBLES ===
    @GetMapping("/disponibles")
    @Operation(summary = "Obtenir les livres disponibles", description = "Récupère tous les livres qui ont au moins un exemplaire disponible")
    public ResponseEntity<List<LivreDto>> obtenirLivresDisponibles() {
        List<Livre> livres = livreService.obtenirLivresDisponibles();
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    // === MODIFIER UN LIVRE ===
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un livre", description = "Met à jour les informations d'un livre existant")
    public ResponseEntity<LivreDto> modifierLivre(@PathVariable Long id, @RequestBody LivreDto livreDto) {
        try {
            Livre livre = LivreMapper.toEntity(livreDto);
            Livre livreModifie = livreService.modifierLivre(id, livre);
            return ResponseEntity.ok(LivreMapper.toDto(livreModifie));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === SUPPRIMER UN LIVRE ===
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre", description = "Supprime un livre du catalogue")
    public ResponseEntity<Void> supprimerLivre(@PathVariable Long id) {
        try {
            livreService.supprimerLivre(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
