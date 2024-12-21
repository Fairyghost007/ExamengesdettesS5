package com.example.controllers.Boutiquier;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import com.example.entities.Dette;
import com.example.entities.Paiement;
import com.example.enums.Statut;
import com.example.entities.Dette;
import com.example.entities.Article;
import com.example.entities.Detail;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.PaiementRepositoryJpa;
import com.example.services.DetteServiceImpl;
import com.example.services.DetailServiceImpl;
import com.example.services.ArticleServiceImpl;
import com.example.services.PaiementServiceImpl;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BoutiquierDetteController {
    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private TableColumn<Dette, String> cDate;

    @FXML
    private TableColumn<Dette, Double> cMontant;

    @FXML
    private TableColumn<Dette, Double> cMontantVerser;
    @FXML
    private TableColumn<Dette, Double> cMontantRestant;

    @FXML
    private TableColumn<Dette, Void> cActions;

    @FXML
    private TableView<Dette> table;

    private final DetteServiceImpl DetteService = new DetteServiceImpl(new DetteRepositoryJpa());
    private final DetailServiceImpl DetailService = new DetailServiceImpl(new DetailRepositoryJpa());
    private final ArticleServiceImpl ArticleService = new ArticleServiceImpl(new ArticleRepositoryJpa());
    private final PaiementServiceImpl PaiementService = new PaiementServiceImpl(new PaiementRepositoryJpa());

    @FXML
    public void initialize() {
        setupTableColumns();
        setupActionColumn();
        loadDetteDataNonSolder();
        toggleBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loadDetteDataSolder();
            } else {
                loadDetteDataNonSolder();
            }
        });

    }

    private void setupTableColumns() {
        cDate.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyStringWrapper(cellData.getValue().getCreatedAt().format(formatter));
        });
        cMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        cMontantVerser.setCellValueFactory(new PropertyValueFactory<>("montantVerser"));
        cMontantRestant.setCellValueFactory(new PropertyValueFactory<>("montantRestant"));
    }

    private void setupActionColumn() {
        cActions.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Dette, Void> call(TableColumn<Dette, Void> param) {
                return new TableCell<>() {
                    private final Button btnDetail = new Button("Detail");
                    private final Button btnPaiement = new Button("Paiement");

                    {
                        btnDetail.getStyleClass().add("AV-button");
                        btnPaiement.getStyleClass().add("AV-button");
                        btnDetail.setOnAction(event -> {
                            Dette dette = getTableView().getItems().get(getIndex());
                            ObservableList<Detail> listDetails = FXCollections
                                    .observableArrayList(DetailService.findDetailsOfDetteById(dette.getId()));
                            Stage stage = new Stage();
                            stage.setTitle("Articles de la dette");
                            TableView<Detail> articleTable = new TableView<>();
                            TableColumn<Detail, String> libelleColumn = new TableColumn<>("Libellé");
                            libelleColumn.setCellValueFactory(cellData -> {
                                Article article = ArticleService.getById(cellData.getValue().getArticle().getId());
                                return new ReadOnlyStringWrapper(article.getLibelle());
                            });
                            TableColumn<Detail, Integer> qteColumn = new TableColumn<>("Quantité");
                            qteColumn.setCellValueFactory(new PropertyValueFactory<>("qteDette"));
                            TableColumn<Detail, Double> montantColumn = new TableColumn<>("Montant");
                            montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));
                            articleTable.getColumns().addAll(libelleColumn, qteColumn, montantColumn);
                            articleTable.setItems(listDetails);
                            stage.setScene(new Scene(articleTable));
                            stage.show();
                            loadDetteDataNonSolder();
                        });
                        btnPaiement.setOnAction(event -> {
                            Dette dette = getTableView().getItems().get(getIndex());

                            // Create the stage
                            Stage stage = new Stage();
                            stage.setTitle("Paiement");

                            // Create the input field for the payment amount
                            TextField paymentAmountField = new TextField();
                            paymentAmountField.setPromptText("Montant du paiement");

                            // Create the buttons
                            Button cancelButton = new Button("Annuler");
                            Button validateButton = new Button("Valider");

                            // Set the button actions
                            cancelButton.setOnAction(event1 -> {
                                stage.close();
                            });
                            validateButton.setOnAction(event1 -> {
                                Double paymentAmount = Double.parseDouble(paymentAmountField.getText());
                                dette.setMontantVerser(dette.getMontantVerser() + paymentAmount);
                                dette.setMontantRestant(dette.getMontantRestant() - paymentAmount);
                                DetteService.update(dette);
                                Paiement paiement =new Paiement();
                                paiement.setMontant(paymentAmount);
                                paiement.setDette(dette);
                                PaiementService.create(paiement);
                                stage.close();
                                loadDetteDataNonSolder();
                            });

                            // Set the stage content
                            BorderPane borderPane = new BorderPane();
                            borderPane.setCenter(new VBox(10, new Label("Montant du paiement:"), paymentAmountField));
                            borderPane.setBottom(new HBox(10, cancelButton, validateButton));
                            Scene scene = new Scene(borderPane, 300, 150);
                            stage.setScene(scene);

                            // Show the stage
                            stage.show();
                            loadDetteDataNonSolder();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(5, btnDetail, btnPaiement));
                        }
                    }
                };
            }
        });
    }

    public void loadDetteDataSolder() {
        ObservableList<Dette> listDettes = FXCollections.observableArrayList(DetteService.findAllDetteSolder());
        table.setItems(listDettes);
    }
    public void loadDetteDataNonSolder() {
        ObservableList<Dette> listDettes = FXCollections.observableArrayList(DetteService.findAllDetteNonSolder());
        table.setItems(listDettes);
    }
}
