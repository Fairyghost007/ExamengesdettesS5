package com.example.controllers.Client;

import java.io.IOException;

import com.example.controllers.Boutiquier.BoutiquierMainController;
import com.example.entities.Dette;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.services.DetteServiceImpl;
import com.jfoenix.controls.JFXToggleButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientDemandeController {

    @FXML
    private TableView<Dette> table;

    @FXML
    private TableColumn<Dette, Integer> cId;

    @FXML
    private TableColumn<Dette, Double> cMontant;

    @FXML
    private TableColumn<Dette, Double> cMontantRestant;

    @FXML
    private TableColumn<Dette, String> cStatut;

    @FXML
    private JFXToggleButton toggleBtn;

    private DetteServiceImpl detteService = new DetteServiceImpl(new DetteRepositoryJpa());
    private ClientMainController mainController;
    private Integer clientId;

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        toggleBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                loadDemandeDataAnuler(clientId);
            } else {
                loadDemandeDataEnCours(clientId);
            }
        });
    }

    private void setupTableColumns() {
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        cMontantRestant.setCellValueFactory(new PropertyValueFactory<>("montantRestant"));
        cStatut.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    public void loadDemandeDataEnCours(Integer clientId) {
        ObservableList<Dette> listDette = FXCollections
                .observableArrayList(detteService.findAllDetteEncoursById(clientId));
        table.setItems(listDette);
    }

    public void loadDemandeDataAnuler(Integer clientId) {
        ObservableList<Dette> listDette = FXCollections
                .observableArrayList(detteService.findAllDetteAnulerById(clientId));
        table.setItems(listDette);
    }

    public void setMainController(ClientMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void redirectToAddDemande() {
        try {
            mainController.initializeAddDemande();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
