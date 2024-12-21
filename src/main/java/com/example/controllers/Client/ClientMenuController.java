package com.example.controllers.Client;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class ClientMenuController {
    @FXML
    public Button clientDetteBtn;

    @FXML
    public Button clientDemandeBtn;

    @FXML
    public Button logoutBtn;

    private ClientMainController mainController; 

    public void setMainController(ClientMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void showClientDette() throws IOException {
        System.out.println("Client Dette button clicked!");
        if (mainController != null) {
            mainController.initializeClientDette();
        }
    }

    @FXML
public void showClientDemande() throws IOException {
    System.out.println("Client Demande button clicked!");
    if (mainController != null) {
        mainController.initializeClientDemande();
    } else {
        System.out.println("Main controller is null!");
    }

}

@FXML
    public void logout() throws IOException {
        System.out.println("Logout button clicked!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Connexion.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}

