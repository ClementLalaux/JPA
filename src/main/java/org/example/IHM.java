package org.example;

import org.example.entity.Listing;
import org.example.entity.ListingInfo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
            }
        }while (!choix.equals("0"));
        emf.close();
    }
    private void menu() {
        System.out.println("1 - Ajouter TODO ");
        System.out.println("2 - Lister TODOS ");
        System.out.println("3 - Modifier TODO ");
        System.out.println("4 - Supprimer TODO ");
        System.out.println("0 - EXIT");
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


        listing = new Listing(titre);
        listingInfo = new ListingInfo(description,datefinal,priorité);
        listing.setListingInfo(listingInfo);

        try {
            em.persist(listing);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException(e);
        }

        em.close();

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

}
