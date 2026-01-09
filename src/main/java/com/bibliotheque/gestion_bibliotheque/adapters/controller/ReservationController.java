package com.bibliotheque.gestion_bibliotheque.adapters.controller;

import com.bibliotheque.gestion_bibliotheque.application.dto.ReservationDto;
import com.bibliotheque.gestion_bibliotheque.application.mapper.ReservationMapper;
import com.bibliotheque.gestion_bibliotheque.application.service.ReservationService;
import com.bibliotheque.gestion_bibliotheque.domain.entities.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Gestion des Réservations", description = "APIs pour gérer les réservations de livres")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // === RÉSERVER UN LIVRE ===
    @PostMapping
    @Operation(summary = "Réserver un livre", description = "Crée une nouvelle réservation pour un livre non disponible")
    public ResponseEntity<ReservationDto> reserverLivre(
            @RequestParam Long livreId,
            @RequestParam Long membreId) {
        try {
            Reservation reservation = reservationService.reserverLivre(livreId, membreId);
            return ResponseEntity.status(HttpStatus.CREATED).body(ReservationMapper.toDto(reservation));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // === ANNULER UNE RÉSERVATION ===
    @DeleteMapping("/{reservationId}/annuler")
    @Operation(summary = "Annuler une réservation", description = "Annule une réservation existante")
    public ResponseEntity<ReservationDto> annulerReservation(@PathVariable Long reservationId) {
        try {
            Reservation reservation = reservationService.annulerReservation(reservationId);
            return ResponseEntity.ok(ReservationMapper.toDto(reservation));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // === OBTENIR LES RÉSERVATIONS D'UN MEMBRE ===
    @GetMapping("/membre/{membreId}")
    @Operation(summary = "Obtenir les réservations d'un membre", description = "Récupère toutes les réservations d'un membre")
    public ResponseEntity<List<ReservationDto>> obtenirReservationsParMembre(@PathVariable Long membreId) {
        List<Reservation> reservations = reservationService.obtenirReservationsParMembre(membreId);
        return ResponseEntity.ok(ReservationMapper.toDtoList(reservations));
    }

    // === OBTENIR LES RÉSERVATIONS D'UN LIVRE ===
    @GetMapping("/livre/{livreId}")
    @Operation(summary = "Obtenir les réservations d'un livre", description = "Récupère toutes les réservations pour un livre")
    public ResponseEntity<List<ReservationDto>> obtenirReservationsParLivre(@PathVariable Long livreId) {
        List<Reservation> reservations = reservationService.obtenirReservationsParLivre(livreId);
        return ResponseEntity.ok(ReservationMapper.toDtoList(reservations));
    }

    // === OBTENIR UNE RÉSERVATION PAR ID ===
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une réservation par ID", description = "Récupère les détails d'une réservation spécifique")
    public ResponseEntity<ReservationDto> obtenirReservationParId(@PathVariable Long id) {
        return reservationService.trouverReservationParId(id)
                .map(reservation -> ResponseEntity.ok(ReservationMapper.toDto(reservation)))
                .orElse(ResponseEntity.notFound().build());
    }
}
