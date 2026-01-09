package com.bibliotheque.gestion_bibliotheque.adapters.controllers;


import com.bibliotheque.gestion_bibliotheque.application.dto.MembreDto;
import com.bibliotheque.gestion_bibliotheque.application.mapper.MembreMapper;
import com.bibliotheque.gestion_bibliotheque.application.usecase.GestionMembreUseCase;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Membre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
@Tag(name = "Membres", description = "Gestion des membres de la bibliothèque")
public class MembreController {

    private final GestionMembreUseCase gestionMembreUseCase;

    public MembreController(GestionMembreUseCase gestionMembreUseCase) {
        this.gestionMembreUseCase = gestionMembreUseCase;
    }

    @PostMapping
    @Operation(summary = "Inscrire un membre", description = "Inscrit un nouveau membre à la bibliothèque")
    public ResponseEntity<MembreDto> inscrireMembre(@RequestBody MembreDto membreDto) {
        Membre membre = MembreMapper.toEntity(membreDto);
        Membre membreInscrit = gestionMembreUseCase.inscrireMembre(membre);
        return new ResponseEntity<>(MembreMapper.toDto(membreInscrit), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtenir tous les membres", description = "Récupère la liste de tous les membres")
    public ResponseEntity<List<MembreDto>> obtenirTousLesMembres() {
        List<Membre> membres = gestionMembreUseCase.obtenirTousLesMembres();
        return ResponseEntity.ok(MembreMapper.toDtoList(membres));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un membre par ID", description = "Récupère un membre spécifique par son identifiant")
    public ResponseEntity<MembreDto> obtenirMembreParId(@PathVariable Long id) {
        return gestionMembreUseCase.trouverMembreParId(id)
                .map(membre -> ResponseEntity.ok(MembreMapper.toDto(membre)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/recherche/nom")
    @Operation(summary = "Rechercher par nom", description = "Recherche des membres par nom")
    public ResponseEntity<List<MembreDto>> rechercherParNom(@RequestParam String nom) {
        List<Membre> membres = gestionMembreUseCase.rechercherParNom(nom);
        return ResponseEntity.ok(MembreMapper.toDtoList(membres));
    }

    @GetMapping("/type/{typeMembre}")
    @Operation(summary = "Filtrer par type", description = "Filtre les membres par type (ETUDIANT, ENSEIGNANT, PERSONNEL)")
    public ResponseEntity<List<MembreDto>> obtenirMembresParType(@PathVariable String typeMembre) {
        List<Membre> membres = gestionMembreUseCase.obtenirMembresParType(typeMembre);
        return ResponseEntity.ok(MembreMapper.toDtoList(membres));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un membre", description = "Modifie les informations d'un membre existant")
    public ResponseEntity<MembreDto> modifierMembre(@PathVariable Long id, @RequestBody MembreDto membreDto) {
        Membre membreModifie = MembreMapper.toEntity(membreDto);
        Membre membre = gestionMembreUseCase.modifierMembre(id, membreModifie);
        return ResponseEntity.ok(MembreMapper.toDto(membre));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un membre", description = "Supprime un membre de la bibliothèque")
    public ResponseEntity<Void> supprimerMembre(@PathVariable Long id) {
        gestionMembreUseCase.supprimerMembre(id);
        return ResponseEntity.noContent().build();
    }
}
