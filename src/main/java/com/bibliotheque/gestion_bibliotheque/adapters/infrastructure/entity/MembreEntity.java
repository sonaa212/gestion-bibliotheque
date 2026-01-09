package com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "membres")
public class MembreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "type_membre", nullable = false)
    private String typeMembre;

    @Column(name = "quota_emprunt")
    private Integer quotaEmprunt;

    @Column(name = "score_fiabilite")
    private Integer scoreFiabilite;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    // Constructeur vide
    public MembreEntity() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTypeMembre() { return typeMembre; }
    public void setTypeMembre(String typeMembre) { this.typeMembre = typeMembre; }

    public Integer getQuotaEmprunt() { return quotaEmprunt; }
    public void setQuotaEmprunt(Integer quotaEmprunt) {
        this.quotaEmprunt = quotaEmprunt;
    }

    public Integer getScoreFiabilite() { return scoreFiabilite; }
    public void setScoreFiabilite(Integer scoreFiabilite) {
        this.scoreFiabilite = scoreFiabilite;
    }

    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }
}