package org.example.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String nomUtilisateur;

    @OneToMany(mappedBy = "utilisateur",cascade = CascadeType.ALL)
    private List<Listing> listeListing;

    public Utilisateur() {
    }

    public Utilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public Utilisateur(int id, String nomUtilisateur) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public List<Listing> getListeListing() {
        return listeListing;
    }

    public void setListeListing(List<Listing> listeListing) {
        this.listeListing = listeListing;
    }
}
