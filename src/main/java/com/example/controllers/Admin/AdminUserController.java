package com.example.controllers.Admin;
import java.io.IOException;

import com.example.controllers.Admin.AdminMainController;
import com.example.entities.Article;
import com.example.entities.Client;
import com.example.entities.Detail;
import com.example.entities.Dette;
import com.example.entities.Paiement;
import com.example.entities.User;
import com.example.repositories.jpa.UserRepositoryJpa;
import com.example.services.UserServiceImpl;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminUserController {
    @FXML
    private TableColumn<User, Void> cActions;
    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TableColumn<User, String> cEmail;

    @FXML
    private TableColumn<User, String> cRole;

    @FXML
    private TableColumn<User, String> cStatut;

    @FXML
    private TableView<User> table;

    private UserServiceImpl UserService = new UserServiceImpl(new UserRepositoryJpa());
    private AdminMainController mainController;

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("ALL", "ADMIN", "BOUTIQUIER"));
        roleComboBox.onActionProperty().set(event -> {
            String selectedRole = roleComboBox.getValue();
            if (selectedRole.equals("ALL")) {
                loadUserData();
            } else if (selectedRole.equals("ADMIN")) {
                loadUserDataAdmin();
            } else if (selectedRole.equals("BOUTIQUIER")) {
                loadUserDataBoutiquier();
            } else {
                loadUserData();
            }
        });
        setupTableColumns();
        setupActionColumn();
        loadUserData();
    }

    private void setupTableColumns() {
        cEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        cRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        cStatut.setCellValueFactory(cellData -> {
            Boolean statut = cellData.getValue().isStatut();
            return new ReadOnlyStringWrapper(statut ? "Activer" : "Desactiver");
        });
    }

    private void setupActionColumn() {
        cActions.setCellFactory(new Callback<>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<>() {
                    private final Button btnAction = new Button();

                    {
                        btnAction.getStyleClass().add("AV-button");
                        btnAction.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (user.isStatut()) {
                                user.setStatut(false);
                            } else {
                                user.setStatut(true);
                            }
                            UserService.update(user);
                            loadUserData();
                            redirectToUser();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Retrieve the User object from the current row
                            User user = getTableView().getItems().get(getIndex());

                            // Set button text based on the User's status
                            btnAction.setText(user.isStatut() ? "Desactiver" : "Activer");

                            // Add the button to the cell
                            setGraphic(btnAction);
                        }
                    }

                };
            }
        });
    }

    public void loadUserData() {
        ObservableList<User> listUsers = FXCollections.observableArrayList(UserService.findAll());
        table.setItems(listUsers);
    }

    public void loadUserDataAdmin() {
        ObservableList<User> listUsers = FXCollections.observableArrayList(UserService.findAllUserActiveAdmin());
        table.setItems(listUsers);
    }

    public void loadUserDataBoutiquier() {
        ObservableList<User> listUsers = FXCollections.observableArrayList(UserService.findAllUserActiveBoutiquier());
        table.setItems(listUsers);
    }

    public void setMainController(AdminMainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void redirectToAddUser() {
        try {
            System.out.println("redirectToAddUsertrfewsewrftgr8fe9w08rfg9fe0dw8");
            mainController.initializeAddUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void redirectToUser(){
        try {
            System.out.println("redirectToAddUsertrfewsewrftgr8fe9w08rfg9fe0dw8");
            mainController.initializeUSer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
