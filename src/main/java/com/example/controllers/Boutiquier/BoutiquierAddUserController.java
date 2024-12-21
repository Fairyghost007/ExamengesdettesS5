package com.example.controllers.Boutiquier;

import java.io.IOException;
import java.util.regex.Pattern;

import com.example.entities.User;
import com.example.enums.Role;
import com.example.repositories.jpa.UserRepositoryJpa;
import com.example.services.UserServiceImpl;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BoutiquierAddUserController {
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private Text messageLabelEmail;

    @FXML
    private Text messageLabelLogin;

    @FXML
    private Text messageLabelPassword;

    @FXML
    private Text messageLabelRole;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    private BoutiquierMainController mainController;
    private UserServiceImpl userService = new UserServiceImpl(new UserRepositoryJpa());
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@gmail\\.com$");

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("ADMIN", "BOUTIQUIER"));
    }

    @FXML
    private void handleSaveUser() {
        String email = emailField.getText().trim();
        String login = loginField.getText().trim();
        String password = passwordField.getText().trim();
        String roleSelection = roleComboBox.getValue();

        if (email.isEmpty()) {
            messageLabelEmail.setText("Email should not be empty.");
            return;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            messageLabelEmail.setText("Invalid email format. It should be a valid Gmail address.");
            return;
        }

        if (login.isEmpty()) {
            messageLabelLogin.setText("Login should not be empty.");
            return;
        }

        if (password.isEmpty()) {
            messageLabelPassword.setText("Password should not be empty.");
            return;
        }

        if (roleSelection == null || roleSelection.isEmpty()) {
            messageLabelRole.setText("Role must be selected.");
            return;
        }

        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);

        try {
            Role role = Role.valueOf(roleSelection);
            user.setRole(role);
        } catch (IllegalArgumentException e) {
            messageLabelRole.setText("Invalid role selected.");
            return;
        }

        user.setStatut(true);

        userService.create(user);

        clearFields();
        redirectToUser();
    }

    public void setMainController(BoutiquierMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void redirectToUser() {
        try {
            mainController.initializeUSer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        emailField.clear();
        loginField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().clearSelection();

        messageLabelEmail.setText("");
        messageLabelLogin.setText("");
        messageLabelPassword.setText("");
        messageLabelRole.setText("");
    }



    @FXML
    private void handleCancel() {
        clearFields();
        redirectToUser();
    }
}
