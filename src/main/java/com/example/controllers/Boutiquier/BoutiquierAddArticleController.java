
package com.example.controllers.Boutiquier;

import java.io.IOException;

import com.example.entities.Article;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.services.ArticleServiceImpl; // Import your service

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import javafx.scene.layout.Region;

public class BoutiquierAddArticleController {

    @FXML
    private TextField libelleField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField quantiteField;
    private BoutiquierMainController mainController;

    private ArticleServiceImpl articleService = new ArticleServiceImpl(new ArticleRepositoryJpa());

    @FXML
    private void handleSaveArticle() {
        String libelle = libelleField.getText();
        String prixText = prixField.getText();
        String quantiteText = quantiteField.getText();

        if (libelle.isEmpty() || prixText.isEmpty() || quantiteText.isEmpty()) {
            // showAlert("Input Error", "Please fill in all fields.");
            return;
        }

        try {
            double prix = Double.parseDouble(prixText);
            Integer quantite = Integer.parseInt(quantiteText);

            Article article = new Article();
            article.setLibelle(libelle);
            article.setPrix(prix);
            article.setQteStock(quantite);

            articleService.create(article);
            
            clearFields();
            redirectToArticle();

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid number for price and quantity.");
        }
    }

    @FXML
    private void handleCancel() {
        clearFields();
        redirectToArticle();
    }

    private void clearFields() {
        libelleField.clear();
        prixField.clear();
        quantiteField.clear();
    }
     public void setMainController(BoutiquierMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void redirectToArticle() {
        try {
            mainController.initializeArticle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

}
