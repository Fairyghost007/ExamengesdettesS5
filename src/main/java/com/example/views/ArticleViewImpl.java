package com.example.views;
import java.util.Scanner;
import java.util.List;
import com.example.core.config.service.Service;
import com.example.core.config.view.impl.ViewImpl;
import com.example.core.factory1.RepositoryFactory;
import com.example.entities.Article;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.services.ArticleServiceImpl;


public class ArticleViewImpl extends ViewImpl<Article>{
    private Service<Article> articleServiceImpl;
    public  ArticleViewImpl(Service<Article> articleServiceImpl) {
        this.articleServiceImpl = articleServiceImpl;
    }

    @Override
    public Article saisie() {
        ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl(RepositoryFactory.createArticleRepositoryJpa());
        Article article = new Article();
        String libelle;
    
        do {
            System.out.println("Enter libelle: ");
            libelle = scanner.nextLine();
    
            // Check if the libelle is unique
            if (articleServiceImpl.findByLibelle(libelle) != null) {
                System.out.println("An article with this libelle already exists. Please enter a different one.");
            }
    
        } while (articleServiceImpl.findByLibelle(libelle) != null);
    
        article.setLibelle(libelle);
        
        System.out.println("Enter prix: ");
        article.setPrix(scanner.nextDouble());
        scanner.nextLine(); // Consume the leftover newline
    
        System.out.println("Enter qteStock: ");
        article.setQteStock(scanner.nextInt());
        scanner.nextLine(); // Consume the leftover newline
    
        return article;
    }


    public void updateArticleStock() {
    ArticleServiceImpl articleServiceImpl = new ArticleServiceImpl(new ArticleRepositoryJpa());

    // Fetch all articles
    List<Article> articles = articleServiceImpl.findAll(); // Assuming you have a method to fetch all articles

    // Check if there are any articles to display
    if (articles.isEmpty()) {
        System.out.println("No articles available.");
        return;
    }

    // Display all articles
    System.out.println("List of Articles:");
    for (Article article : articles) {
        System.out.println("ID: " + article.getId() + ", Libelle: " + article.getLibelle() +
                           ", Prix: " + article.getPrix() + ", Qte Stock: " + article.getQteStock());
    }

    Integer chosenArticleId;
    Scanner scanner = new Scanner(System.in);

    // Prompt the user to choose an article by ID
    do {
        System.out.print("Enter the ID of the article you want to update the stock for: ");
        chosenArticleId = scanner.nextInt();

        // Check if the entered ID exists directly in the loop
        if (articleServiceImpl.getById(chosenArticleId) == null) {
            System.out.println("Invalid ID. Please try again.");
        }
    } while (articleServiceImpl.getById(chosenArticleId) == null); // Keep prompting until a valid ID is entered

    // Ask the user if they want to increase or decrease the stock
    System.out.println("Choose an option:");
    System.out.println("1. Decrease stock");
    System.out.println("2. Increase stock");
    int choice;
    choice = scanner.nextInt();

    // Get the quantity to adjust
    System.out.print("Enter the quantity: ");
    int quantityChange = scanner.nextInt();

    // Fetch the current article
    Article articleToUpdate = articleServiceImpl.getById(chosenArticleId);
    int newQuantity;

    // Update the stock quantity based on user choice
    if (choice == 1) {
        // Decrease stock
        do {
            newQuantity = articleToUpdate.getQteStock() - quantityChange;
    
            if (newQuantity < 0) {
                System.out.println("Error: Stock cannot be negative. Please enter a valid quantity.");
            }
        } while (newQuantity < 0); // Repeat until the quantity is valid
    }else if (choice == 2) {
        // Increase stock
        newQuantity = articleToUpdate.getQteStock() + quantityChange;
    } else {
        System.out.println("Invalid choice. No changes made.");
        return;
    }

    articleToUpdate.setQteStock(newQuantity);
    // Update the stock quantity in the database
    articleServiceImpl.update(articleToUpdate);


    // Confirm the update
    System.out.println("Stock updated successfully. New quantity: " + newQuantity);
}

    


    
}
