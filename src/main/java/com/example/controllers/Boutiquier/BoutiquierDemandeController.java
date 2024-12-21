// package com.example.controllers.Boutiquier;

// import java.time.format.DateTimeFormatter;

// import com.example.entities.Dette;
// import com.example.repositories.jpa.DetteRepositoryJpa;
// import com.example.services.DetteServiceImpl;

// import javafx.beans.property.ReadOnlyStringWrapper;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.fxml.FXML;
// import javafx.scene.control.TableColumn;
// import javafx.scene.control.TableView;
// import javafx.scene.control.cell.PropertyValueFactory;

// public class BoutiquierDemandeController {
//     @FXML
//     private TableColumn<Dette, String> cDate;

//     @FXML
//     private TableColumn<Dette, Double> cMontant;

//     @FXML
//     private TableColumn<Dette, String> cStatus;

//     @FXML
//     private TableView<Dette> table;

//     private DetteServiceImpl DetteService = new DetteServiceImpl(new DetteRepositoryJpa());

//     @FXML
//     public void initialize() {
//         cDate.setCellValueFactory(cellData -> {
//             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//             return new ReadOnlyStringWrapper(cellData.getValue().getCreatedAt().format(formatter));
//         });
//         cMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
//         cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
//     }

//     public void loadDetteData() {
//         ObservableList<Dette> listDettes = FXCollections.observableArrayList(DetteService.findAllDetteAnulerOuEnCour());
//         table.setItems(listDettes);
//     }
    
// }

package com.example.controllers.Boutiquier;

import java.time.format.DateTimeFormatter;

import com.example.entities.Article;
import com.example.entities.Detail;
import com.example.entities.Dette;
import com.example.enums.Statut;
import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.services.ArticleServiceImpl;
import com.example.services.DetailServiceImpl;
import com.example.services.DetteServiceImpl;
import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class BoutiquierDemandeController {
    @FXML
    private TableColumn<Dette, String> cDate;

    @FXML
    private TableColumn<Dette, Double> cMontant;

    @FXML
    private TableColumn<Dette, String> cStatus;

    @FXML
    private TableColumn<Dette, Void> cActions;

    @FXML
    private TableView<Dette> table;

    @FXML
    private JFXToggleButton toggleBtn;


    private final DetteServiceImpl detteService = new DetteServiceImpl(new DetteRepositoryJpa());
    private final DetailServiceImpl DetailService = new DetailServiceImpl(new DetailRepositoryJpa());
    private final ArticleServiceImpl ArticleService = new ArticleServiceImpl(new ArticleRepositoryJpa());



    @FXML
    public void initialize() {
        setupTableColumns();
        setupActionColumn();
        loadDetteDataEnCour();
        toggleBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loadDetteDataAnnuler();
            } else {
                loadDetteDataEnCour();
            }
        });
    }

    private void setupTableColumns() {
        cDate.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new ReadOnlyStringWrapper(cellData.getValue().getCreatedAt().format(formatter));
        });
        cMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void setupActionColumn() {
        cActions.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Dette, Void> call(TableColumn<Dette, Void> param) {
                return new TableCell<>() {
                    private final Button btnAnnuler = new Button("Annuler");
                    private final Button btnValider = new Button("Valider");
                    private final Button btnDetail = new Button("Details");

                    {
                        btnAnnuler.getStyleClass().add("AV-button");
                        btnValider.getStyleClass().add("AV-button");
                        btnDetail.getStyleClass().add("AV-button");
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
                            TableColumn<Detail, Integer> qteRestantColumn = new TableColumn<>("QteRestant");
                            qteRestantColumn.setCellValueFactory(cellData -> {
                                Article article = ArticleService.getById(cellData.getValue().getArticle().getId());
                                return new SimpleIntegerProperty(article.getQteStock()).asObject();
                            });
                            articleTable.getColumns().addAll(libelleColumn, qteColumn, montantColumn, qteRestantColumn);
                            articleTable.setItems(listDetails);
                            stage.setScene(new Scene(articleTable));
                            stage.show();
                            loadDetteDataEnCour();
                        });
                        btnAnnuler.setOnAction(event -> {
                            Dette dette = getTableView().getItems().get(getIndex());
                            updateDetteStatus(dette.getId(), Statut.ANNULER);
                        });

                        btnValider.setOnAction(event -> {
                            Dette dette = getTableView().getItems().get(getIndex());
                            updateDetteStatus(dette.getId(), Statut.VALIDER);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(20, btnAnnuler, btnValider,btnDetail));
                        }
                    }
                };
            }
        });
    }

    private void updateDetteStatus(Integer detteId, Statut newStatus) {
        Dette dette = detteService.getById(detteId);
        if (dette != null) {
            if (dette.getStatus() == newStatus) {
                System.out.println("Status already " + newStatus + " for Dette ID: " + detteId);
                return;
            }
            dette.setStatus(newStatus); 
            detteService.update(dette); 
            loadDetteDataEnCour();
        }
    }
    

    public void loadDetteDataEnCour() {
        ObservableList<Dette> listDettes = FXCollections.observableArrayList(detteService.findAllDetteEnCour());
        table.setItems(listDettes);
    }
    public void loadDetteDataAnnuler() {
        ObservableList<Dette> listDettes = FXCollections.observableArrayList(detteService.findAllDetteAnuler());
        table.setItems(listDettes);
    }
}

