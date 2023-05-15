package org.example.entity;

import javax.persistence.*;

    @Entity
    @Table(name = "listing")
    public  class Listing {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String titre;

        public Listing() {
        }

        public Listing(String titre) {
            this.titre = titre;
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

        @Override
        public String toString() {
            return "Listing{" +
                    "id=" + id +
                    ", titre='" + titre + '\'' +
                    '}';
        }
    }


