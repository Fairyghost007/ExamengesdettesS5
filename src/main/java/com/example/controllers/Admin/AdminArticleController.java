package com.example.controllers.Admin;
import java.io.IOException;

import com.example.controllers.Admin.AdminMainController;
import com.example.entities.Article;
import com.example.entities.Detail;
import com.example.entities.Dette;
import com.example.entities.Paiement;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.services.ArticleServiceImpl;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminArticleController {
    @FXML
    private TableColumn<Article, String> cLibelle;

    @FXML
    private TableColumn<Article, Double> cPrix;

    @FXML
    private TableColumn<Article, Integer> cQuantite;

    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private TableColumn<Article, Void> cActions;

    @FXML
    private TableView<Article> table;

    private ArticleServiceImpl articleService = new ArticleServiceImpl(new ArticleRepositoryJpa());
    private AdminMainController mainController;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupActionColumn();
        loadArticleDisponible();
        toggleBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loadArticleNonDisponible();
            } else {
                loadArticleDisponible();
            }
        });
    }

    private void setupTableColumns() {
        cLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        cPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        cQuantite.setCellValueFactory(new PropertyValueFactory<>("qteStock"));

    }

    private void setupActionColumn() {
        cActions.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Article, Void> call(TableColumn<Article, Void> param) {
                return new TableCell<>() {
                    private final Button btnMAJ = new Button("MaJ");

                    {
                        btnMAJ.getStyleClass().add("AV-button");
                        btnMAJ.setOnAction(event -> {
                            Article article = getTableView().getItems().get(getIndex());

                            Stage stage = new Stage();
                            stage.setTitle("Quantite");

                            TextField newQteStockField = new TextField();
                            newQteStockField.setPromptText("New quantite");

                            Button cancelButton = new Button("Annuler");
                            Button validateButton = new Button("Valider");

                            cancelButton.setOnAction(event1 -> {
                                stage.close();
                            });
                            validateButton.setOnAction(event1 -> {
                                String newQuantity = newQteStockField.getText();
                                article.setQteStock(Integer.parseInt(newQuantity));
                                articleService.update(article);
                                stage.close();
                                loadArticleDisponible();
                            });

                            // Set the stage content
                            BorderPane borderPane = new BorderPane();
                            borderPane.setCenter(new VBox(10, new Label("New quantite:"), newQteStockField));
                            borderPane.setBottom(new HBox(10, cancelButton, validateButton));
                            Scene scene = new Scene(borderPane, 300, 150);
                            stage.setScene(scene);

                            // Show the stage
                            stage.show();
                            loadArticleDisponible();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(5, btnMAJ));
                        }
                    }
                };
            }
        });
    }

    public void loadArticleDisponible() {
        ObservableList<Article> listArticle = FXCollections
                .observableArrayList(articleService.findAllArticleDisponible());
        table.setItems(listArticle);
    }

    public void loadArticleNonDisponible() {
        ObservableList<Article> listArticle = FXCollections
                .observableArrayList(articleService.findAllArticleNonDisponible());
        table.setItems(listArticle);
    }

    public void setMainController(AdminMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void redirectToAddArticle() {
        try {
            mainController.initializeAddArticle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
