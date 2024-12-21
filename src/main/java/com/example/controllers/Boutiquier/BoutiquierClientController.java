package com.example.controllers.Boutiquier;

import java.io.IOException;

import com.example.entities.Client;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.services.ClientServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
public class BoutiquierClientController {
    @FXML
    private TableColumn<Client, String> cSurname;

    @FXML
    private TableColumn<Client, String> cAddress;

    @FXML
    private TableColumn<Client, String> cPhone;

    @FXML
    private TableView<Client> table;

    @FXML
    private TextField surnameSearchField;

    @FXML
    private Button searchBtn;


    private ClientServiceImpl ClientService = new ClientServiceImpl(new ClientRepositoryJpa());
    private BoutiquierMainController mainController;


    @FXML
    public void initialize() {
        cSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        cAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        cPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        loadClientData();
        searchBtn.setOnAction(event -> {
            searchClient();
        });
    }

    public void loadClientData() {
        ObservableList<Client> listClients = FXCollections.observableArrayList(ClientService.findAll());
        table.setItems(listClients);
    }

    public void searchClient() {
        String surname = surnameSearchField.getText();
        ObservableList<Client> listClients = FXCollections.observableArrayList(ClientService.search(surname));
        table.setItems(listClients);
    }

    public void setMainController(BoutiquierMainController mainController) {
        this.mainController = mainController;
    }


    @FXML
    public void redirectToAddClient() {
        try {
            mainController.initializeAddClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
