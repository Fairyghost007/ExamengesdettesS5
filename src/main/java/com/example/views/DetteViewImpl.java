package com.example.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.example.enums.Statut;
import com.example.core.config.service.Service;
import com.example.core.config.view.impl.ViewImpl;
import com.example.entities.Article;
import com.example.entities.Detail;
import com.example.entities.Dette;
import com.example.entities.Paiement;
import com.example.entities.User;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.repositories.jpa.PaiementRepositoryJpa;
import com.example.services.ArticleServiceImpl;
import com.example.services.ClientServiceImpl;
import com.example.services.DetailServiceImpl;
import com.example.services.DetteServiceImpl;
import com.example.services.PaiementServiceImpl;
import com.example.entities.Client;

public class DetteViewImpl extends ViewImpl {

    private Service<Client> clientServiceImpl;
    private Service<Dette> detteServiceImpl;
    private Service<Detail> detailServiceImpl;
    private Service<Paiement> paiementServiceImpl;
    private ArticleServiceImpl articleServiceImpl;

    public DetteViewImpl(Service<Dette> detteServiceImpl, Service<Detail> detailServiceImpl, 
                         Service<Paiement> paiementServiceImpl, Service<Client> clientServiceImpl, 
                         Service<Article> articleServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.detteServiceImpl = detteServiceImpl;
        this.detailServiceImpl = detailServiceImpl;
        this.paiementServiceImpl = paiementServiceImpl;
        this.articleServiceImpl = (ArticleServiceImpl) articleServiceImpl; // Ensure correct casting for ArticleServiceImpl
    }

    @Override
    public Dette saisie() {
        ClientServiceImpl clientServiceImpl = new ClientServiceImpl(new ClientRepositoryJpa());
        DetteServiceImpl   detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());
        Scanner scanner = new Scanner(System.in);
        Dette dette = new Dette();
    
        // Fetch clients and select one
        List<Client> clients = clientServiceImpl.findAll();
        displayClients(clients);
        // System.out.print("Choose a client by entering the ID: ");
        Integer clientId ;
        Client client;
        do {
            System.out.print("Choose a client by entering the ID: ");
            clientId = scanner.nextInt();
            client = clientServiceImpl.getById(clientId);
            if (client == null) {
                System.out.println("Client not found! Please try again.");
            }
        } while (client == null);
    
    
        // Set the client in the dette
        dette.setClient(client);
        
        System.out.println("Client selected: " + client.getSurname());
        dette.setStatus(Statut.ENCOURS);
    
        // Save the Dette entity first
        detteServiceImpl.create(dette);
    
        // Fetch articles and select them
        List<Detail> details = selectAndCreateDetails(dette); // Select articles and create details
    
        if (details.isEmpty()) {
            System.out.println("No details selected. Dette creation cancelled.");
            return null;  // No details, no dette
        }
    
        // Calculate total amount and set it in Dette
        double totalMontant = 0.0;
        for (Detail detail : details) {
            totalMontant += detail.getMontant();  // Accumulate total amount
        }
    
        // Set montant, montantVerser, and montantRestant
        dette.setMontant(totalMontant);
        dette.setMontantVerser(0.0);  // Initial amount paid is 0
        dette.setMontantRestant(totalMontant);  // Initial remaining amount is total
    
        // Update the Dette in the database with calculated amounts
        detteServiceImpl.update(dette);
    
        return dette;  // Return the created dette
    }
    
    private List<Detail> selectAndCreateDetails(Dette dette) {
        ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl(new ArticleRepositoryJpa());
        Scanner scanner = new Scanner(System.in);
        List<Detail> details = new ArrayList<>();
    
        // Display available articles
        List<Article> articles = articleServiceImpl.findAll();
        System.out.println("List of Articles:");
        for (Article article : articles) {
            System.out.println("ID: " + article.getId() + ", Name: " + article.getLibelle() + 
                               ", Price: " + article.getPrix() + ", Stock: " + article.getQteStock());
        }
    
        Integer articleId;
        do {
            System.out.print("Enter Article ID to select (or 0 to finish): ");
            articleId = scanner.nextInt();
    
            if (articleId != 0) {
                // Retrieve the article based on ID
                Article article = articleServiceImpl.getById(articleId);
    
                // Check if the article exists
                if (article == null) {
                    System.out.println("Article does not exist.");
                } else if (article.getQteStock() == 0) {
                    // Check if the article is out of stock
                    System.out.println("Article is out of stock.");
                } else {
                    // Ask for the desired quantity of the selected article
                    System.out.print("Enter the quantity you want to select for " + article.getLibelle() + ": ");
                    Integer selectedQuantity = scanner.nextInt();
    
                    // Validate the selected quantity
                    if (selectedQuantity > article.getQteStock()) {
                        System.out.println("Insufficient stock. Available quantity: " + article.getQteStock());
                    } else if (selectedQuantity <= 0) {
                        System.out.println("Please enter a valid quantity.");
                    } else {
                        // Create a Detail object for the selected article
                        Detail detail = new Detail();
                        detail.setArticle(article);
                        detail.setQteDette(selectedQuantity); // Set qteDette
                        detail.setMontant(selectedQuantity * article.getPrix()); // Calculate montant
                        detail.setDette(dette); // Associate detail with the saved dette
                        details.add(detail);  // Add detail to the list
    
                        // Update the stock and persist the changes
                        // article.setQteStock(article.getQteStock() - selectedQuantity); // Subtract selected quantity from stock
                        // articleServiceImpl.update(article); // Save the updated article
                        System.out.println("Selected " + selectedQuantity + " of " + article.getLibelle());
                    }
                }
            }
        } while (articleId != 0);  // Continue until the user enters 0 to finish
    
        // Persist details if any were selected
        if (!details.isEmpty()) {
            DetailServiceImpl detailServiceImpl = new DetailServiceImpl(new DetailRepositoryJpa());
            for (Detail detail : details) {
                detailServiceImpl.create(detail); // Persist each detail
            }
        }
    
        return details;  // Return the list of selected details
    }
    

    private void displayClients(List<Client> clients) {
        System.out.println("List of Clients:");
        for (Client client : clients) {
            System.out.println("ID: " + client.getId() + ", Name: " + client.getSurname());
        }
    }


    public void displayArticleOfADette() {
        // First, fetch all Dette entities
        DetteServiceImpl detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());
        List<Dette> dettes =detteServiceImpl.findAll();
    
        // Check if there are any dettes to display
        if (dettes.isEmpty()) {
            System.out.println("No debts available.");
            return;
        }
    
        // Display all dettes
        System.out.println("List of Dettes:");
        for (Dette dette : dettes) {
            System.out.println("ID: " + dette.getId() + ", Client: " + dette.getClient().getSurname() +
                               ", Montant Restant: " + dette.getMontantRestant());
        }
    
        Integer chosenDetteId;
        Scanner scanner = new Scanner(System.in);
    
        do {
            // Prompt the user to choose a Dette by its ID
            System.out.print("Enter the ID of the Dette you want to see articles for: ");
            chosenDetteId = scanner.nextInt();
    
            // Check if the entered ID exists directly in the loop
            if (detteServiceImpl.getById(chosenDetteId) == null) {
                System.out.println("Invalid ID. Please try again.");
            }
        } while (detteServiceImpl.getById(chosenDetteId) == null); // Keep prompting until a valid ID is entered
    
        // Fetch details related to the chosen Dette ID
        DetailServiceImpl detailServiceImpl = new DetailServiceImpl(new DetailRepositoryJpa());
        List<Detail> details = detailServiceImpl.findDetailsOfDetteById(chosenDetteId);
    
        // Check if there are any associated articles
        if (details.isEmpty()) {
            System.out.println("No articles associated with this debt.");
            return;
        }
    
        // Display associated articles
        System.out.println("Articles associated with Dette ID: " + chosenDetteId);
        for (Detail detail : details) {
            Article article = detail.getArticle();
            System.out.println("ID: " + article.getId() +
                               ", Article: " + article.getLibelle() +
                               ", Quantity: " + detail.getQteDette() +
                               ", Price per unit: " + article.getPrix() +
                               ", Total: " + detail.getMontant());
        }
    }


    public void displayPaiementsOfADette() {
    // First, fetch all Dette entities
    DetteServiceImpl detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());
    List<Dette> dettes = detteServiceImpl.findAll();

    // Check if there are any dettes to display
    if (dettes.isEmpty()) {
        System.out.println("No debts available.");
        return;
    }

    // Display all dettes
    System.out.println("List of Dettes:");
    for (Dette dette : dettes) {
        System.out.println("ID: " + dette.getId() + ", Client: " + dette.getClient().getSurname() +
                           ", Montant Restant: " + dette.getMontantRestant());
    }

    Integer chosenDetteId;
    Scanner scanner = new Scanner(System.in);

    do {
        // Prompt the user to choose a Dette by its ID
        System.out.print("Enter the ID of the Dette you want to see payments for: ");
        chosenDetteId = scanner.nextInt();

        // Check if the entered ID exists directly in the loop
        if (detteServiceImpl.getById(chosenDetteId) == null) {
            System.out.println("Invalid ID. Please try again.");
        }
    } while (detteServiceImpl.getById(chosenDetteId) == null); // Keep prompting until a valid ID is entered

    // Fetch payments related to the chosen Dette ID
    PaiementServiceImpl paiementServiceImpl = new PaiementServiceImpl(new PaiementRepositoryJpa());
    List<Paiement> paiements = paiementServiceImpl.findPaiementsOfDetteById(chosenDetteId);

    // Check if there are any associated payments
    if (paiements.isEmpty()) {
        System.out.println("No payments associated with this debt.");
        return;
    }

    // Display associated payments
    System.out.println("Payments associated with Dette ID: " + chosenDetteId);
    for (Paiement paiement : paiements) {
        System.out.println("ID: " + paiement.getId() +
                           ", Amount Paid: " + paiement.getMontant());
    }
}


public void deleteAllDetteSolder() {
    DetteServiceImpl detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());

    // Fetch all dettes that have montantRestant === 0
    List<Dette> dettesSolder = detteServiceImpl.findAllDetteSolder();
    System.out.println("List of Dettes solder:");
    for (Dette dette : dettesSolder) {
        System.out.println("ID: " + dette.getId() + ", Client: " + dette.getClient().getSurname() +
                           ", Montant Restant: " + dette.getMontantRestant());
    }

    if (dettesSolder.isEmpty()) {
        System.out.println("No dettes to delete. All dettes have remaining balances.");
        return;
    }
    
    // Loop through the list and delete each dette
    for (Dette dette : dettesSolder) {
        detteServiceImpl.delete(dette.getId()); // Assuming you have a delete method that deletes by ID
    }
}

public void displayAllDetteEnCoursOuAnuler(){
    DetteServiceImpl detteServiceImpl = new DetteServiceImpl(new DetteRepositoryJpa());
    DetailServiceImpl detailService = new DetailServiceImpl(new DetailRepositoryJpa());
    ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl(new ArticleRepositoryJpa());
    List<Dette> dettes = detteServiceImpl.findAllDetteAnulerOuEnCour();
    System.out.println("List of Dettes solder:");
    for (Dette dette : dettes) {
        System.out.println("ID: " + dette.getId() + ", Client: " + dette.getClient().getSurname() +
                           ", Montant Restant: " + dette.getMontantRestant()+ ", Statut:"+ dette.getStatus());
    }

    if (dettes.isEmpty()) {
        System.out.println("No dettes to delete. All dettes have remaining balances.");
        return;
    }

    Integer chosenDette;
    Scanner scanner = new Scanner(System.in);

    // Prompt the user to choose an article by ID
    do {
        System.out.print("Enter the ID of the Dette you want to look for: ");
        chosenDette = scanner.nextInt();

        // Check if the entered ID exists directly in the loop
        if (detteServiceImpl.getById(chosenDette) == null) {
            System.out.println("Invalid ID. Please try again.");
        }
    } while (detteServiceImpl.getById(chosenDette) == null); 
    List<Detail> details = detailService.findAllDetailsByDetteId(chosenDette);
    for (Detail detail : details) {
        Article article= articleServiceImpl.getById(detail.getArticle().getId());
        System.out.println( ", Article: " + article.getLibelle() +
                           ", Quantite Demander: " + detail.getQteDette()+ ", Qantite en stock:"+article.getQteStock());
        
    }
    
    Dette dette = detteServiceImpl.getById(chosenDette);

    System.out.println("Choose an option:");
    System.out.println("1. Valider");
    System.out.println("2. Annuler");
    int choice;
    choice = scanner.nextInt();
    if (choice == 1) {
        // Decrease stock
        // do {
            
            
            dette.setStatus(Statut.VALIDER);
            for (Detail detail : details) {
                Article article= articleServiceImpl.getById(detail.getArticle().getId());
                int newQuantity = article.getQteStock() - detail.getQteDette();
                if (newQuantity < 0) {
                    System.out.println("Error: Stock cannot be negative. Please enter a valid quantity.");
                    newQuantity = article.getQteStock();
                    break;
                }
                article.setQteStock(newQuantity);
                articleServiceImpl.update(article);
            }
    }else if (choice == 2) {
        // Increase stock
        dette.setStatus(Statut.ANNULER);
    } else {
        System.out.println("Invalid choice. No changes made.");
        return;
    }
    // Update the stock quantity in the database
    detteServiceImpl.update(dette);

    System.out.println("Dette updated successfully.");

}


    


    

    
}
