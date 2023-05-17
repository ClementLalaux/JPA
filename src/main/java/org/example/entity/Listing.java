package org.example.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "listing")
public  class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;

    private boolean statut = false;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "listing_info_id",referencedColumnName = "id")
    private ListingInfo listingInfo;

    @ManyToOne
    @JoinColumn(name="utilisateur_id")
    private Utilisateur utilisateur;

    @ManyToMany(mappedBy = "listings")
    private List<Categorie> categories = new ArrayList<>();


    public Listing() {
    }

    public Listing(String titre) {
        this.titre = titre;
    }

    public Listing(String titre, boolean statut) {
        this.titre = titre;
        this.statut = statut;
    }

    public Listing(String titre, boolean statut, ListingInfo listingInfo) {
        this.titre = titre;
        this.statut = statut;
        this.listingInfo = listingInfo;
    }

    public Listing(String titre, boolean statut, ListingInfo listingInfo, Utilisateur utilisateur) {
        this.titre = titre;
        this.statut = statut;
        this.listingInfo = listingInfo;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public ListingInfo getListingInfo() {
        return listingInfo;
    }

    public void setListingInfo(ListingInfo listingInfo) {
        this.listingInfo = listingInfo;
    }


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> posts) {
        this.categories = posts;
    }

    public void addCategorie(Categorie categorie){
        categories.add(categorie);
        categorie.getListings().add(this);
    }

    @Override
    public String toString() {
        return "Listing{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", statut=" + statut +
                ", listingInfo=" + listingInfo +
                ", utilisateur=" + utilisateur +
                '}';
    }
}