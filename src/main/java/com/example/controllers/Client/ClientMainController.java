package com.example.controllers.Client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.entities.Client;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.services.ClientServiceImpl;

public class ClientMainController implements Initializable {

    @FXML
    private AnchorPane contentPane;

    private Integer clientId; 

    private Client client;

    @FXML
    private BorderPane mainPane;
    private ClientServiceImpl ClientService = new ClientServiceImpl(new ClientRepositoryJpa());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Client/ClientMenu.fxml"));
            VBox menuPane = menuLoader.load(); 
            ClientMenuController menuController = menuLoader.getController();
            menuController.setMainController(this);
            mainPane.setLeft(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading menu", "Could not load the menu. Please try again.");
        }
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
        try {
            initializeClientDette();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading client debts", "Could not load client debt data.");
        }
    }

    public void setClientById( Integer clientId) {
        this.client = ClientService.getById(clientId);
    }

    public void initializeClientDette() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Client/ClientDette.fxml"));
        AnchorPane clientDettePane = fxmlLoader.load();
        ClientDetteController clientDetteController = fxmlLoader.getController();
        clientDetteController.loadDetteData(clientId); 
        contentPane.getChildren().setAll(clientDettePane); 
    }

    public void initializeClientDemande() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Client/ClientDemande.fxml"));
        AnchorPane clientDemandePane = fxmlLoader.load();
        ClientDemandeController clientDemandeController = fxmlLoader.getController();
        clientDemandeController.setMainController(this);
        clientDemandeController.setClientId(clientId); 
        clientDemandeController.loadDemandeDataEnCours(clientId); 
        contentPane.getChildren().setAll(clientDemandePane); 
    }

    public void initializeAddDemande() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Client/ClientAddDemande.fxml"));
        AnchorPane addDemandePane = fxmlLoader.load();
        ClientAddDemandeController addDemandeController = fxmlLoader.getController();
        addDemandeController.setMainController(this); 
        addDemandeController.setClientId(clientId); 
        addDemandeController.setClientById(clientId);
        contentPane.getChildren().setAll(addDemandePane);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
