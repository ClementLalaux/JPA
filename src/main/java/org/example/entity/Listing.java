package org.example.entity;

import javax.persistence.*;

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

        @Override
        public String toString() {
            return "Listing{" +
                    "id=" + id +
                    ", titre='" + titre + '\'' +
                    '}';
        }
    }


