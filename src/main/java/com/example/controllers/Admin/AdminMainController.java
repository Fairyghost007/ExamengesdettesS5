package com.example.controllers.Admin;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.example.controllers.Admin.AdminMenuController;
import com.example.controllers.Admin.AdminAddArticleController;
import com.example.controllers.Admin.AdminAddUserController;
import com.example.controllers.Admin.AdminArticleController;
import com.example.controllers.Admin.AdminUserController;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AdminMainController implements Initializable {
    @FXML
    private AnchorPane contentPane;
    
    private int adminId; 

    @FXML
    private JFXToggleButton toggleBtn;

    @FXML
    private BorderPane mainPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Admin/AdminMenu.fxml"));
            VBox menuPane = menuLoader.load(); 
            AdminMenuController menuController = menuLoader.getController();
            menuController.setMainController(this);
            mainPane.setLeft(menuPane);
        } catch (IOException e) {
            e.printStackTrace();
            // showAlert("Error loading menu", "Could not load the menu. Please try again.");
        }
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
        try {
            initializeArticle();
        } catch (IOException e) {
            e.printStackTrace();
            // showAlert("Error loading client debts", "Could not load client debt data.");
        }
    }

    public void initializeArticle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Admin/AdminArticle.fxml"));
        AnchorPane adminArticlePane = fxmlLoader.load();
        AdminArticleController adminArticleController = fxmlLoader.getController();
        adminArticleController.setMainController(this); 
        adminArticleController.loadArticleDisponible(); 
        contentPane.getChildren().setAll(adminArticlePane); 
    }
    public void initializeAddArticle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Admin/AdminAddArticle.fxml"));
        AnchorPane addArticlePane = fxmlLoader.load();
        AdminAddArticleController addArticleController = fxmlLoader.getController();
        addArticleController.setMainController(this); 
        contentPane.getChildren().setAll(addArticlePane);
    }

    public void initializeUSer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Admin/AdminUser.fxml"));
        AnchorPane AdminUserPane = fxmlLoader.load();
        AdminUserController adminUserController = fxmlLoader.getController();
        adminUserController.setMainController(this);
        adminUserController.loadUserData(); 
        contentPane.getChildren().setAll(AdminUserPane); 
    }
    public void initializeAddUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Admin/AdminAddUser.fxml"));
        AnchorPane addUserPane = fxmlLoader.load();
        AdminAddUserController addUserController = fxmlLoader.getController();
        addUserController.setMainController(this); 
        contentPane.getChildren().setAll(addUserPane);
    }
    
}
