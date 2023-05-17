package org.example.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nomCategorie;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "categorie_listing",joinColumns = @JoinColumn(name = "categorie_id"),
    inverseJoinColumns = @JoinColumn(name = "listing_id"))
    private List<Listing> listings = new ArrayList<>();

    public Categorie() {
    }

    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }


}
