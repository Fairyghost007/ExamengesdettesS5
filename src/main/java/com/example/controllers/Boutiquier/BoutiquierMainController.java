package com.example.controllers.Boutiquier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class BoutiquierMainController implements Initializable {
      @FXML
    private AnchorPane contentPane;
    
    private int boutiquierId; 

    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private BorderPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierMenu.fxml"));
            VBox menuPane = menuLoader.load(); 
            BoutiquierMenuController menuController = menuLoader.getController();
            menuController.setMainController(this);
            mainPane.setLeft(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading menu", "Could not load the menu. Please try again.");
        }
    }

    public void setBoutiquierId(int boutiquierId) {
        this.boutiquierId = boutiquierId;
        try {
            initializeArticle();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error loading client debts", "Could not load client debt data.");
        }
    }


    public void initializeArticle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierArticle.fxml"));
        AnchorPane BoutiquierArticlePane = fxmlLoader.load();
        BoutiquierArticleController boutiquierArticleController = fxmlLoader.getController();
        boutiquierArticleController.setMainController(this); 
        boutiquierArticleController.loadArticleDisponible(); 
        contentPane.getChildren().setAll(BoutiquierArticlePane); 
    }

    public void initializeArticleNonDisponible() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierArticleNonDisponible.fxml"));
        AnchorPane BoutiquierArticlePane = fxmlLoader.load();
        BoutiquierArticleController boutiquierArticleController = fxmlLoader.getController();
        boutiquierArticleController.setMainController(this); 
        boutiquierArticleController.loadArticleNonDisponible();
        contentPane.getChildren().setAll(BoutiquierArticlePane);
    }

    public void initializeClient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierClient.fxml"));
        AnchorPane BoutiquierClientPane = fxmlLoader.load();
        BoutiquierClientController boutiquierClientController = fxmlLoader.getController();
        boutiquierClientController.setMainController(this); 
        boutiquierClientController.loadClientData(); 
        contentPane.getChildren().setAll(BoutiquierClientPane); 
    }

    public void initializeUSer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierUser.fxml"));
        AnchorPane BoutiquierUserPane = fxmlLoader.load();
        BoutiquierUserController boutiquierUserController = fxmlLoader.getController();
        boutiquierUserController.setMainController(this);
        boutiquierUserController.loadUserData(); 
        contentPane.getChildren().setAll(BoutiquierUserPane); 
    }

    public void initializeDette() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierDette.fxml"));
        AnchorPane BoutiquierDettePane = fxmlLoader.load();
        BoutiquierDetteController boutiquierDetteController = fxmlLoader.getController();
        boutiquierDetteController.loadDetteDataNonSolder(); 
        contentPane.getChildren().setAll(BoutiquierDettePane); 
    }

    public void initializeDemande() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierDemande.fxml"));
        AnchorPane BoutiquierDemandePane = fxmlLoader.load();
        BoutiquierDemandeController boutiquierDemandeController = fxmlLoader.getController();
        boutiquierDemandeController.loadDetteDataEnCour(); 
        contentPane.getChildren().setAll(BoutiquierDemandePane); 
    }

    public void initializeAddArticle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierAddArticle.fxml"));
        AnchorPane addArticlePane = fxmlLoader.load();
        BoutiquierAddArticleController addArticleController = fxmlLoader.getController();
        addArticleController.setMainController(this); 
        contentPane.getChildren().setAll(addArticlePane);
    }

    public void initializeAddClient() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierAddClient.fxml"));
        AnchorPane addClientPane = fxmlLoader.load();
        BoutiquierAddClientController addClientController = fxmlLoader.getController();
        addClientController.setMainController(this); 
        contentPane.getChildren().setAll(addClientPane);
    }
    public void initializeAddUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierAddUser.fxml"));
        AnchorPane addUserPane = fxmlLoader.load();
        BoutiquierAddUserController addUserController = fxmlLoader.getController();
        addUserController.setMainController(this); 
        contentPane.getChildren().setAll(addUserPane);
    }
    


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
