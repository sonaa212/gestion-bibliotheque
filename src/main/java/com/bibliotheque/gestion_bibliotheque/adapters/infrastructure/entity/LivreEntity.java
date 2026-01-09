package com.bibliotheque.gestion_bibliotheque.adapters.infrastructure.entity;

import javax.persistence.*;

@Entity
@Table(name = "livres")
public class LivreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false)
    private String auteur;

    @Column(unique = true, nullable = false)
    private String isbn;

    private String editeur;

    @Column(name = "annee_publication")
    private Integer anneePublication;

    private String categorie;

    @Column(name = "nombre_exemplaires", nullable = false)
    private Integer nombreExemplaires;

    @Column(name = "nombre_disponibles", nullable = false)
    private Integer nombreDisponibles;

    @Column(name = "etat_physique")
    private String etatPhysique;

    // Constructeur vide (requis par JPA)
    public LivreEntity() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getEditeur() { return editeur; }
    public void setEditeur(String editeur) { this.editeur = editeur; }

    public Integer getAnneePublication() { return anneePublication; }
    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Integer getNombreExemplaires() { return nombreExemplaires; }
    public void setNombreExemplaires(Integer nombreExemplaires) {
        this.nombreExemplaires = nombreExemplaires;
    }

    public Integer getNombreDisponibles() { return nombreDisponibles; }
    public void setNombreDisponibles(Integer nombreDisponibles) {
        this.nombreDisponibles = nombreDisponibles;
    }

    public String getEtatPhysique() { return etatPhysique; }
    public void setEtatPhysique(String etatPhysique) {
        this.etatPhysique = etatPhysique;
    }
}