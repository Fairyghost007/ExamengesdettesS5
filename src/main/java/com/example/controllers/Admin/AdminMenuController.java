package com.example.controllers.Admin;
import java.io.IOException;

import com.example.controllers.Admin.AdminMainController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class AdminMenuController {
    @FXML
    public Button articleBtn;
    @FXML
    public Button userBtn;
    @FXML
    public Button logoutBtn;
    
    private AdminMainController mainController;
    public void setMainController(AdminMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void showArticle() throws IOException {
        System.out.println("Client Dette button clicked!");
        if (mainController != null) {
            mainController.initializeArticle();
        }
    }

    @FXML
    public void showUser() throws IOException {
        System.out.println("Client Demande button clicked!");
        if (mainController != null) {
            mainController.initializeUSer();
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
