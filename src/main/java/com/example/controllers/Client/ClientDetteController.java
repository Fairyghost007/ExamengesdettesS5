package com.example.controllers.Client;

import com.example.entities.Dette;
import com.example.repositories.jpa.DetteRepositoryJpa;
import com.example.services.DetteServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientDetteController {
    @FXML
    private Label nDettes;

    @FXML
    private Label totalDette;

    @FXML
    private Label nDemandes;

    @FXML
    private TableView<Dette> table;

    @FXML
    private TableColumn<Dette, Integer> cId;

    @FXML
    private TableColumn<Dette, Double> cMontant;

    @FXML
    private TableColumn<Dette, Double> cMontantVerser;

    private DetteServiceImpl detteService = new DetteServiceImpl(new DetteRepositoryJpa());

    @FXML
    public void initialize() {
        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        cMontantVerser.setCellValueFactory(new PropertyValueFactory<>("montantVerser"));
    }

    public void loadDetteData(Integer clientId) {
        ObservableList<Dette> listDette = FXCollections.observableArrayList(detteService.findAllDetteByClientId(clientId));
        table.setItems(listDette);
        updateMetrics(clientId);
    }

    private void updateMetrics(Integer clientId) {
        ObservableList<Dette> dettes=FXCollections.observableArrayList(detteService.findAllDetteByClientId(clientId));
        ObservableList <Dette> demandes=FXCollections.observableArrayList(detteService.findAllDetteAnulerOuEnCourById(clientId));
        double totalMontant = detteService.sumMontantByClientId(clientId);

        nDettes.setText(String.valueOf(dettes.size()));
        totalDette.setText(String.format("%.2f", totalMontant));
        nDemandes.setText(String.valueOf(demandes.size()));
    }
}
