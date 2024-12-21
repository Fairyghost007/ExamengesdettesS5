package com.example.controllers;

import com.example.entities.User;
import com.example.entities.Client;
import com.example.controllers.Admin.AdminMainController;
import com.example.controllers.Boutiquier.BoutiquierMainController;
import com.example.controllers.Client.ClientDetteController;
import com.example.core.factory1.RepositoryFactory;
import com.example.services.ClientServiceImpl;
import com.example.services.UserServiceImpl;
import com.example.controllers.Client.ClientMainController;
import com.example.enums.Role;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class ConnexionController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text messageLabel;

    UserServiceImpl userServiceImpl = new UserServiceImpl(RepositoryFactory.createUserRepositoryJpa());
    ClientServiceImpl clientServiceImpl = new ClientServiceImpl(RepositoryFactory.createClientRepositoryJpa());

    @FXML
    public void handleLogin() throws IOException {
        User user;
        do {
            String login = loginField.getText();
            user = userServiceImpl.findUserByLogin(login);

            if (user == null) {
                messageLabel.setText("Invalid login or password. Please try again.");
                return;
            }
        } while (user == null);

        Stage stage = (Stage) loginButton.getScene().getWindow();
        FXMLLoader fxmlLoader;
        Scene mainScene;

        if (user.getRole() == Role.CLIENT) {
            messageLabel.setText("Welcome, " + user.getLogin() + ", " + user.getRole() + " !");
            fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Client/ClientMain.fxml"));
            mainScene = new Scene(fxmlLoader.load());
            ClientMainController mainController = fxmlLoader.getController();
            Client client = clientServiceImpl.findClientByUserId(user.getId());
            mainController.setClientId(client.getId());
            mainController.initializeClientDette();
        } else if (user.getRole() == Role.BOUTIQUIER) {
            messageLabel.setText("Welcome, " + user.getLogin() + ", " + user.getRole() + " !");
            fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Boutiquier/BoutiquierMain.fxml"));
            mainScene = new Scene(fxmlLoader.load());
            BoutiquierMainController mainController = fxmlLoader.getController();
            mainController.setBoutiquierId(user.getId());
            mainController.initializeArticle();
        }else if(user.getRole() == Role.ADMIN){
            messageLabel.setText("Welcome, " + user.getLogin() + ", " + user.getRole() + " !");
            fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Fxml/Admin/AdminMain.fxml"));
            mainScene = new Scene(fxmlLoader.load());
            AdminMainController mainController = fxmlLoader.getController();
            mainController.setAdminId(user.getId());
            mainController.initializeArticle();
            

        } else {
            return;
        }

        stage.setScene(mainScene);
        stage.show();
    }

}
