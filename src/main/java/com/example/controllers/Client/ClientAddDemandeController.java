package com.example.controllers.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.entities.Article;
import com.example.entities.Client;
import com.example.entities.Detail;
import com.example.entities.Dette;
import com.example.enums.Statut;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.services.ArticleServiceImpl;
import com.example.services.ClientServiceImpl;
import com.example.services.DetailServiceImpl;
import com.example.services.DetteServiceImpl;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.TableView;

public class ClientAddDemandeController {
    @FXML
    private ComboBox<Article> cbArticle;

    @FXML
    private TextField quantityField;

    @FXML
    private TableView<Detail> articleTable;

    @FXML
    private TableView<Dette> detteTable;

    @FXML
    private TableColumn<Detail, String> articleNameColumn;

    @FXML
    private TableColumn<Detail, Integer> quantityColumn;

    @FXML
    private TableColumn<Detail, Double> totalColumn;

    @FXML
    private Text totalMontantDette;

    private DoubleProperty totalMontant = new SimpleDoubleProperty(0.0);

    private ClientMainController mainController;
    private ArticleServiceImpl articleService = new ArticleServiceImpl(new ArticleRepositoryJpa());
    private final DetteServiceImpl DetteService = new DetteServiceImpl(new DetteRepositoryJpa());
    private final DetailServiceImpl detailServiceImpl = new DetailServiceImpl(new DetailRepositoryJpa());
    private ClientServiceImpl ClientService = new ClientServiceImpl(new ClientRepositoryJpa());
    private ObservableList<Detail> detailsList = FXCollections.observableArrayList();
    private Integer clientId;
    private Client client;

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setClientById(Integer clientId) {
        this.client = ClientService.getById(clientId);
    }

    @FXML
    public void initialize() {
        totalMontantDette.textProperty().bind(totalMontant.asString("%.2f")); // Display with 2 decimal places
        cbArticle.setItems(FXCollections.observableArrayList());
        cbArticle.setButtonCell(new ListCell<Article>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getLibelle() : null);
            }
        });
        cbArticle.setCellFactory(param -> new ListCell<Article>() {
            @Override
            protected void updateItem(Article item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null) ? item.getLibelle() : null);
            }
        });
        loadArticleDisponible();
        articleTable.setItems(detailsList);
        setupTableColumns();
    }

    private void setupTableColumns() {
        articleNameColumn.setCellValueFactory(cellData -> {
            Article article = articleService.getById(cellData.getValue().getArticle().getId());
            return new ReadOnlyStringWrapper(article.getLibelle());
        });
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("qteDette"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
    }

    @FXML
    public void handleSaveDette() {
        if (detailsList.isEmpty()) {
            return;
        }
        Dette dette = new Dette();
        dette.setStatus(Statut.ENCOURS);
        dette.setClient(client);
        dette.setMontant(0.0);
        dette.setMontantVerser(0.0);
        dette.setMontantRestant(0.0);
        DetteService.create(dette);

        double totalMontant = 0.0;
        for (Detail detail : detailsList) {
            detail.setDette(dette);
            detailServiceImpl.create(detail);
            Article article = articleService.getById(detail.getArticle().getId());
            if (article != null) {
                Integer newStock = article.getQteStock() - detail.getQteDette();
                article.setQteStock(newStock);
                articleService.update(article); // Save updated stock
            }
            totalMontant += detail.getMontant();
        }
        dette.setMontant(totalMontant);
        dette.setMontantRestant(totalMontant);
        DetteService.update(dette);
        detailsList.clear();
        articleTable.setItems(detailsList); // Refresh the table
        redirectToDemande(); // Redirect to the demand list
    }

    @FXML
    public void handleAddArticle() {
        Article selectedArticle = cbArticle.getValue();
        if (selectedArticle == null || quantityField.getText().isEmpty()) {
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (NumberFormatException e) {
            return; // Exit if input is not a valid number
        }

        // Check if the article already exists in the detailsList
        for (Detail detail : detailsList) {
            if (detail.getArticle().getId().equals(selectedArticle.getId())) {
                // Article already exists, update quantity and montant
                int newQuantity = detail.getQteDette() + quantity;
                detail.setQteDette(newQuantity);
                detail.setMontant(newQuantity * selectedArticle.getPrix());
                recalculateTotalMontant(); // Update total montant
                articleTable.refresh(); // Refresh the table view
                clearFields(); // Reset input fields
                return; // Exit method
            }
        }

        // Article is not in the list, create a new Detail and add it
        Detail detail = new Detail();
        detail.setArticle(selectedArticle);
        detail.setQteDette(quantity);
        detail.setMontant(quantity * selectedArticle.getPrix());
        detailsList.add(detail); // Add to the observable list
        recalculateTotalMontant(); // Update total montant
        articleTable.setItems(detailsList); // Update the table view
        clearFields(); // Reset input fields
    }

    // Method to recalculate the total montant
    private void recalculateTotalMontant() {
        double total = detailsList.stream()
                .mapToDouble(Detail::getMontant)
                .sum();
        totalMontant.set(total); // Update the property
    }

    public void setMainController(ClientMainController mainController) {
        this.mainController = mainController;
    }

    public void handleCancel() {
        clearFields();
        detailsList.clear();
        articleTable.setItems(detailsList);
        redirectToDemande();
    }

    public void loadArticleDisponible() {
        ObservableList<Article> listArticle = FXCollections
                .observableArrayList(articleService.findAllArticleDisponible());
        cbArticle.setItems(listArticle);
    }

    private void clearFields() {
        cbArticle.setValue(null);
        quantityField.clear();

    }

    @FXML
    public void redirectToDemande() {
        try {
            mainController.initializeClientDemande();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
