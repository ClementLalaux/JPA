package org.example;

import org.example.entity.Categorie;
import org.example.entity.Listing;
import org.example.entity.ListingInfo;
import org.example.entity.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class IHM {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("listing");
    Scanner scanner;
    String choix;

    public IHM() {
        scanner = new Scanner(System.in);
    }

    public void start() {
        do {
            menu();
            choix = scanner.nextLine();
            switch (choix) {
                case "1":
                    createTodo();
                    break;
                case "2":
                    listTodos();
                    break;
                case "3":
                    updateTodo();
                    break;
                case "4":
                    removeTodo();
                    break;
                case "5":
                    createUser();
                    break;
                case "6":
                    listTodosByUser();
                    break;
                case "7":
                    removeUser();
                    break;
                case "8":
                    createCategorie();
                    break;
                case "9":
                    removeCategorie();
                    break;
                case "10":
                    listTodosByCategorie();
                    break;
            }
        }while (!choix.equals("0"));
        emf.close();
    }
    private void menu() {
        System.out.println("1 - Ajouter TODO ");
        System.out.println("2 - Lister TODOS ");
        System.out.println("3 - Modifier TODO ");
        System.out.println("4 - Supprimer TODO ");
        System.out.println("5 - Ajouter un utilisateur");
        System.out.println("6 - Lister TODOS par utilisateur");
        System.out.println("7 - Supprimer utilisateur");
        System.out.println("8 - Ajouter une catégorie");
        System.out.println("9 - Supprimer une catégorie");
        System.out.println("10 - Afficher les taches d'un catégorie");
        System.out.println("11 - Afficher les taches d'un catégorie");
        System.out.println("0 - EXIT");
    }

    private void createUser(){
        Utilisateur utilisateur = null;
        System.out.println("Merci de saisir un nom d'utilisateur");
        String nom = scanner.nextLine();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        utilisateur = new Utilisateur(nom);
        try {
            em.persist(utilisateur);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }

        em.close();
    }

    private void createTodo(){
        Listing listing = null;
        ListingInfo listingInfo = null;
        System.out.print("Merci de saisir le titre de la TODO : ");
        String titre = scanner.nextLine();
        System.out.print("Merci de saisir la description : ");
        String description = scanner.nextLine();
        System.out.print("Merci de saisir la date d'échéance format (2023-05-16) : ");
        String dateEcheance = scanner.nextLine();
        LocalDate datefinal = LocalDate.parse(dateEcheance, DateTimeFormatter.ISO_DATE);


        System.out.print("Merci de saisir la priorité de 1 à 255 : ");
        int priorité = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Utilisateur utilisateur = findUser();

        listing = new Listing(titre);
        listingInfo = new ListingInfo(description,datefinal,priorité);
        listing.setListingInfo(listingInfo);
        listing.setUtilisateur(utilisateur);

        try {
            em.persist(listing);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }

        em.close();

    }

    private void createCategorie(){
        Categorie categorie = null;
        System.out.print("Merci de saisir le titre de la catégorie : ");
        String titre = scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        categorie = new Categorie(titre);

        try {
            em.persist(categorie);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }

        em.close();

    }

    private void removeCategorie(){
        System.out.print("Merci de saisir l'id de la categorie a supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Categorie categorie = em.find(Categorie.class,id);

        try {
            em.remove(categorie);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        em.close();

    }

    private Utilisateur findUser(){
        Utilisateur utilisateur = null;
        EntityManager em = emf.createEntityManager();

        System.out.println("Entrez l'id de l'utilisateur");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Query query = em.createQuery("select p from Utilisateur p where p.id = :id");
            query.setParameter("id",id);
            utilisateur = (Utilisateur) query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        em.close();
        return utilisateur;

    }

    private void listTodos(){
        List<Listing> listings = null;
        EntityManager em = emf.createEntityManager();

        listings = em.createQuery("select p from Listing p",Listing.class).getResultList();

        for (Listing l : listings){
            System.out.println(l.getTitre());
            System.out.println(l.isStatut());
        }

        em.close();

    }

    private void listTodosByUser(){
        Utilisateur utilisateur = findUser();
        int id = utilisateur.getId();

        List<Listing> listings = null;
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("select p from Listing p WHERE p.utilisateur.id = :idUser",Listing.class);
        query.setParameter("idUser",id);

        listings = query.getResultList();

        for (Listing l : listings){
            System.out.println(l.getUtilisateur().getNomUtilisateur());
            System.out.println(l.getTitre());
        }

        em.close();
    }

    private void listTodosByCategorie(){
        System.out.print("Merci de saisir l'id de la categorie a afficher : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        List<Listing> listings = null;
        Categorie categorie = null;
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("select c from Categorie c WHERE id = :idCategorie",Listing.class);
        query.setParameter("idCategorie",id);
        categorie = (Categorie) query.getSingleResult();

        listings = categorie.getListings();

        for (Listing l : listings){
            System.out.println(l.getUtilisateur().getNomUtilisateur());
            System.out.println(l.getTitre());
        }

        em.close();
    }

    private void addTodoInCategorie(){
        Listing listing = null;
        Categorie categorie = null;

        System.out.print("Merci de saisir l'id du TODO a mettre dans une catégorie : ");
        int idTodo = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();

        try {
            Query query = em.createQuery("select p from Listing p where p.id = :id");
            query.setParameter("id",idTodo);
            listing = (Listing) query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("Entrez l'id de la catégorie");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Query query = em.createQuery("select p from Categorie p where p.id = :id");
            query.setParameter("id",idTodo);
            categorie = (Categorie) query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        listing.addCategorie(categorie);

        try {
            em.persist(listing);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }

        em.close();

    }

    private void removeTodo(){
        System.out.print("Merci de saisir l'id du todo : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Listing listing = em.find(Listing.class,id);

        try {
            em.remove(listing);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        em.close();

    }

    private void updateTodo(){
        System.out.print("Merci de saisir l'id de la TODO : ");
        int id = scanner.nextInt();
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Listing listing = em.find(Listing.class,id);
        listing.setStatut(!listing.isStatut());

        try {
            em.persist(listing);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }

        em.close();

    }

    private void removeUser(){
        System.out.print("Merci de saisir l'id de l'utilisateur : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Utilisateur utilisateur = em.find(Utilisateur.class,id);

        try {
            em.remove(utilisateur);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        em.close();

    }

}
