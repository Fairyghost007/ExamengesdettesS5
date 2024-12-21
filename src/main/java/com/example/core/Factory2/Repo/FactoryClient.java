package com.example.core.Factory2.Repo;

import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.UserRepositoryJpa;
import com.example.entities.Client;
import com.example.services.ClientServiceImpl;
import com.example.views.ClientViewImpl;
import com.example.views.UserViewImpl;

public class FactoryClient {

    private ClientRepositoryJpa clientRepositoryJpa;
    private ClientServiceImpl clientService;
    // private ClientRepositoryBD clientRepositoryBD;
    // private UserRepositoryBD userRepositoryBD;

    public FactoryClient() {
        // Initialize the user repository first
        // userRepositoryBD = new UserRepositoryBD();

        // Initialize the client repository with dependencies
        // clientRepositoryBD = new ClientRepositoryBD(userRepositoryBD);
        clientRepositoryJpa = new ClientRepositoryJpa();

        // Initialize the service with the JPA repository
        // clientService = new ClientServiceImpl(clientRepositoryJpa);
    }

    public ClientRepositoryJpa getClientRepositoryJpa() {
        return clientRepositoryJpa;
    }

    public ClientServiceImpl getClientService() {
        return clientService;
    }

    // public ClientRepositoryBD getClientRepositoryBD() {
    //     return clientRepositoryBD;
    // }

    // public UserRepositoryBD getUserRepositoryBD() {
    //     return userRepositoryBD;
    // }
}
