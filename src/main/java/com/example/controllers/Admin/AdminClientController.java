package com.example.controllers.Admin;
import java.io.IOException;

import com.example.controllers.Admin.AdminMainController;
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
public class AdminClientController {
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
    private AdminMainController mainController;

    


}
