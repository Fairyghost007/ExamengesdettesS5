package com.example.controllers.Boutiquier;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.regex.Pattern;

import com.example.entities.Client;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.services.ClientServiceImpl;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BoutiquierAddClientController {

    @FXML
    private TextField surnameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private JFXToggleButton addUserToggle;

    @FXML
    private VBox userFieldsBox;

    @FXML
    private Text messageLabelSurname;

    @FXML
    private Text messageLabelPhone;

    @FXML
    private Text messageLabelAddress;

    @FXML
    private Text messageLabelEmail;

    @FXML
    private Text messageLabelLogin;

    @FXML
    private Text messageLabelPassword;
    private BoutiquierMainController mainController;
    private ClientServiceImpl clientService = new ClientServiceImpl(new ClientRepositoryJpa());
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(77|78|76|70|75)[0-9]{7}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@gmail\\.com$");

    @FXML
    private void initialize() {
        userFieldsBox.setVisible(false);
        userFieldsBox.managedProperty().bind(userFieldsBox.visibleProperty());

        addUserToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            userFieldsBox.setVisible(newValue);
        });
    }

    @FXML
    private void handleSaveClient() {
        String surname = surnameField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();

        if (surname.isEmpty()) {
            messageLabelSurname.setText("Surname should not be empty;");
            return;
        }

        if (phone.isEmpty()) {
            messageLabelPhone.setText("Phone should not be empty;");
            return;
        }

        if (!PHONE_PATTERN.matcher(phone).matches()) {
            messageLabelPhone
                    .setText("Invalid phone format. It should start with 77, 78, 76, 70, or 75 followed by 7 digits.");
            return;
        }

        if (address.isEmpty()) {
            messageLabelAddress.setText("Address should not be empty;");
            return;
        }

        Client client = new Client();
        client.setSurname(surname);
        client.setPhone(phone);
        client.setAddress(address);

        if (addUserToggle.isSelected()) {
            String email = emailField.getText().trim();
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty()) {
                messageLabelEmail.setText("Email should not be empty;");
                return;
            }

            if (!EMAIL_PATTERN.matcher(email).matches()) {
                messageLabelEmail.setText("Invalid email format. It should be a valid Gmail address.");
                return;
            }

            if (login.isEmpty()) {
                messageLabelLogin.setText("Login should not be empty;");
                return;
            }

            if (password.isEmpty()) {
                messageLabelPassword.setText("Password should not be empty;");
                return;
            }

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setRole(Role.CLIENT);
            newUser.setStatut(true);
            client.setUser(newUser);
        }

        clientService.create(client);
        clearFields();
        redirectToClient();
    }

    @FXML
    private void handleCancel() {
        clearFields();
        redirectToClient();
    }

    @FXML
    private void handleToggleUserFields() {
        // Toggle the visibility of the user fields
        if (addUserToggle.isSelected()) {
            userFieldsBox.setVisible(true);
        } else {
            userFieldsBox.setVisible(false);
        }
    }

    public void setMainController(BoutiquierMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void redirectToClient() {
        try {
            mainController.initializeClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        surnameField.clear();
        phoneField.clear();
        addressField.clear();
        emailField.clear();
        loginField.clear();
        passwordField.clear();
        addUserToggle.setSelected(false);

        messageLabelSurname.setText("");
        messageLabelPhone.setText("");
        messageLabelAddress.setText("");
        messageLabelEmail.setText("");
        messageLabelLogin.setText("");
        messageLabelPassword.setText("");
    }

}
