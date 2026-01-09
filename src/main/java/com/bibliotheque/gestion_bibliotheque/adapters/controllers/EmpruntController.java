package com.bibliotheque.gestion_bibliotheque.adapters.controllers;


import com.bibliotheque.gestion_bibliotheque.application.dto.EmpruntDto;
import com.bibliotheque.gestion_bibliotheque.application.mapper.EmpruntMapper;
import com.bibliotheque.gestion_bibliotheque.application.usecase.GestionEmpruntUseCase;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Emprunt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emprunts")
@Tag(name = "Emprunts", description = "Gestion des emprunts de livres")
public class EmpruntController {

    private final GestionEmpruntUseCase gestionEmpruntUseCase;

    public EmpruntController(GestionEmpruntUseCase gestionEmpruntUseCase) {
        this.gestionEmpruntUseCase = gestionEmpruntUseCase;
    }

    @PostMapping
    @Operation(summary = "Emprunter un livre", description = "Crée un nouvel emprunt de livre")
    public ResponseEntity<EmpruntDto> emprunterLivre(@RequestParam Long livreId, @RequestParam Long membreId) {
        Emprunt emprunt = gestionEmpruntUseCase.emprunterLivre(livreId, membreId);
        return new ResponseEntity<>(EmpruntMapper.toDto(emprunt), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/retour")
    @Operation(summary = "Retourner un livre", description = "Traite le retour d'un livre emprunté")
    public ResponseEntity<EmpruntDto> retournerLivre(@PathVariable Long id) {
        Emprunt emprunt = gestionEmpruntUseCase.retournerLivre(id);
        return ResponseEntity.ok(EmpruntMapper.toDto(emprunt));
    }

    @GetMapping
    @Operation(summary = "Obtenir tous les emprunts", description = "Récupère la liste de tous les emprunts")
    public ResponseEntity<List<EmpruntDto>> obtenirTousLesEmprunts() {
        List<Emprunt> emprunts = gestionEmpruntUseCase.obtenirTousLesEmprunts();
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un emprunt par ID", description = "Récupère un emprunt spécifique par son identifiant")
    public ResponseEntity<EmpruntDto> obtenirEmpruntParId(@PathVariable Long id) {
        return gestionEmpruntUseCase.trouverEmpruntParId(id)
                .map(emprunt -> ResponseEntity.ok(EmpruntMapper.toDto(emprunt)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/membre/{membreId}")
    @Operation(summary = "Obtenir les emprunts d'un membre", description = "Récupère tous les emprunts d'un membre spécifique")
    public ResponseEntity<List<EmpruntDto>> obtenirEmpruntsParMembre(@PathVariable Long membreId) {
        List<Emprunt> emprunts = gestionEmpruntUseCase.obtenirEmpruntsParMembre(membreId);
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }

    @GetMapping("/en-cours")
    @Operation(summary = "Obtenir les emprunts en cours", description = "Récupère tous les emprunts actuellement en cours")
    public ResponseEntity<List<EmpruntDto>> obtenirEmpruntsEnCours() {
        List<Emprunt> emprunts = gestionEmpruntUseCase.obtenirEmpruntsEnCours();
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }

    @GetMapping("/en-retard")
    @Operation(summary = "Obtenir les emprunts en retard", description = "Récupère tous les emprunts en retard")
    public ResponseEntity<List<EmpruntDto>> obtenirEmpruntsEnRetard() {
        List<Emprunt> emprunts = gestionEmpruntUseCase.obtenirEmpruntsEnRetard();
        return ResponseEntity.ok(EmpruntMapper.toDtoList(emprunts));
    }


}
