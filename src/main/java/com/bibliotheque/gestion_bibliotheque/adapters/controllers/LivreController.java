package com.bibliotheque.gestion_bibliotheque.adapters.controllers;

import com.bibliotheque.gestion_bibliotheque.application.dto.LivreDto;
import com.bibliotheque.gestion_bibliotheque.application.mapper.LivreMapper;
import com.bibliotheque.gestion_bibliotheque.application.usecase.GestionLivreUseCase;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Livre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livres")
@Tag(name = "Livres", description = "Gestion des livres de la bibliothèque")
public class LivreController {

    private final GestionLivreUseCase gestionLivreUseCase;

    public LivreController(GestionLivreUseCase gestionLivreUseCase) {
        this.gestionLivreUseCase = gestionLivreUseCase;
    }

    @PostMapping
    @Operation(summary = "Ajouter un livre", description = "Ajoute un nouveau livre au catalogue")
    public ResponseEntity<LivreDto> ajouterLivre(@RequestBody LivreDto livreDto) {
        Livre livre = LivreMapper.toEntity(livreDto);
        Livre livreAjoute = gestionLivreUseCase.ajouterLivre(livre);
        return new ResponseEntity<>(LivreMapper.toDto(livreAjoute), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtenir tous les livres", description = "Récupère la liste de tous les livres")
    public ResponseEntity<List<LivreDto>> obtenirTousLesLivres() {
        List<Livre> livres = gestionLivreUseCase.obtenirTousLesLivres();
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un livre par ID", description = "Récupère un livre spécifique par son identifiant")
    public ResponseEntity<LivreDto> obtenirLivreParId(@PathVariable Long id) {
        return gestionLivreUseCase.trouverLivreParId(id)
                .map(livre -> ResponseEntity.ok(LivreMapper.toDto(livre)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Obtenir les livres disponibles", description = "Récupère tous les livres disponibles à l'emprunt")
    public ResponseEntity<List<LivreDto>> obtenirLivresDisponibles() {
        List<Livre> livres = gestionLivreUseCase.obtenirLivresDisponibles();
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    @GetMapping("/recherche/titre")
    @Operation(summary = "Rechercher par titre", description = "Recherche des livres par titre")
    public ResponseEntity<List<LivreDto>> rechercherParTitre(@RequestParam String titre) {
        List<Livre> livres = gestionLivreUseCase.rechercherParTitre(titre);
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    @GetMapping("/recherche/auteur")
    @Operation(summary = "Rechercher par auteur", description = "Recherche des livres par auteur")
    public ResponseEntity<List<LivreDto>> rechercherParAuteur(@RequestParam String auteur) {
        List<Livre> livres = gestionLivreUseCase.rechercherParAuteur(auteur);
        return ResponseEntity.ok(LivreMapper.toDtoList(livres));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un livre", description = "Modifie les informations d'un livre existant")
    public ResponseEntity<LivreDto> modifierLivre(@PathVariable Long id, @RequestBody LivreDto livreDto) {
        Livre livreModifie = LivreMapper.toEntity(livreDto);
        Livre livre = gestionLivreUseCase.modifierLivre(id, livreModifie);
        return ResponseEntity.ok(LivreMapper.toDto(livre));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un livre", description = "Supprime un livre du catalogue")
    public ResponseEntity<Void> supprimerLivre(@PathVariable Long id) {
        gestionLivreUseCase.supprimerLivre(id);
        return ResponseEntity.noContent().build();
    }

}