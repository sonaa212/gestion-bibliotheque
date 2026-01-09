package com.bibliotheque.gestion_bibliotheque.application.dto;

import java.time.LocalDate;

public class MembreDto {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String typeMembre;
    private Integer quotaEmprunt;
    private Integer scoreFiabilite;
    private LocalDate dateInscription;

    // Constructeur vide
    public MembreDto() {}

    // Constructeur complet
    public MembreDto(Long id, String nom, String prenom, String email,
                     String typeMembre, Integer quotaEmprunt,
                     Integer scoreFiabilite, LocalDate dateInscription) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.typeMembre = typeMembre;
        this.quotaEmprunt = quotaEmprunt;
        this.scoreFiabilite = scoreFiabilite;
        this.dateInscription = dateInscription;
    }

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